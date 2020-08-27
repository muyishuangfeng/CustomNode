package com.yk.silence.customnode.widget.activity

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.databinding.ActivityMySelfInfoBinding
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.impl.OnCameraClickListener
import com.yk.silence.customnode.impl.OnOssResultListener
import com.yk.silence.customnode.model.UserModel
import com.yk.silence.customnode.ui.dialog.ChooseDialogHelper
import com.yk.silence.customnode.util.CameraUtil
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.util.glide.GlideUtils
import com.yk.silence.customnode.util.oss.OSSHelper
import com.yk.silence.customnode.viewmodel.mine.MineViewModel
import com.yk.silence.toolbar.CustomTitleBar

class MySelfInfoActivity : BaseVMActivity<MineViewModel, ActivityMySelfInfoBinding>() {

    private lateinit var mUser: UserModel
    private var mAvatar = ""

    override fun getLayoutID() = R.layout.activity_my_self_info

    override fun viewModelClass() = MineViewModel::class.java

    override fun initBinding(binding: ActivityMySelfInfoBinding) {
        super.initBinding(binding)
        binding.titleMyself.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {
                ActivityManager.finish(MySelfInfoActivity::class.java)
            }

            override fun onRightClick() {

            }
        })
        binding.imgMyselfAvatar.setOnClickListener {
            ChooseDialogHelper.showCamera(
                this@MySelfInfoActivity,
                object : OnCameraClickListener {
                    override fun onCameraClick(position: Int) {
                        if (position == 1) {
                            //跳转到相册
                            CameraUtil.openAlbum(
                                this@MySelfInfoActivity,
                                REQUEST_CODE_OPEN_PHOTO_ALBUM
                            )
                        } else {
                            //跳转到相机
                            CameraUtil.openAlbum(
                                this@MySelfInfoActivity,
                                REQUEST_CODE_TAKE_PHOTO
                            )
                        }
                    }
                })
        }
        binding.btnMyselfSubmit.setOnClickListener { submit() }
    }

    override fun initData() {
        super.initData()
        mViewModel.getData()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mUserModel.observe(this@MySelfInfoActivity, Observer {
                mBinding.user = it
                if (it != null) {
                    mUser = it
                    GlideUtils.loadPathWithOutCache(
                        this@MySelfInfoActivity,
                        it.user_avatar,
                        mBinding.imgMyselfAvatar
                    )
                }

            })
            mUpdateState.observe(this@MySelfInfoActivity, Observer {
                var result = "更新失败"
                result = if (it) {
                    "更新成功"
                } else {
                    "更新失败"
                }
                ToastUtil.getInstance().shortToast(this@MySelfInfoActivity, result)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CameraUtil.onActivityResult(
            this,
            requestCode,
            resultCode,
            data,
            object : CameraUtil.OnCameraResultListener {
                override fun onCameraResult(uri: Uri) {
                    GlideUtils.loadPathWithOutCache(
                        this@MySelfInfoActivity,
                        uri.toString(),
                        mBinding.imgMyselfAvatar
                    )
                    uploadFile(uri.toString())
                }

                override fun onAlbumResult(path: String) {
                    GlideUtils.loadPathWithOutCache(
                        this@MySelfInfoActivity,
                        path,
                        mBinding.imgMyselfAvatar
                    )
                    uploadFile(path)
                }
            })
    }


    /**
     * 上传文件
     */
    private fun uploadFile(path: String) {
        CThreadPoolExecutor.runInBackground(Runnable {
            OSSHelper.updateFile(
                this@MySelfInfoActivity,
                "1" + USER_AVATAR,
                path,
                object : OnOssResultListener {
                    override fun onResultSuccess() {
                        mAvatar = BASE_OSS_URL + "1" + USER_AVATAR
                        Log.e("TAG", mAvatar.toString())
                    }

                    override fun onResultFailed() {
                        Log.e("TAG", "onResultFailed")
                    }

                })
        })
    }

    /**
     * 提交
     */
    private fun submit() {
        if (mBinding.edtNickName.text.toString().isEmpty()) {
            return
        }
        if (mBinding.edtPass.text.toString().isEmpty()) {
            return
        }
        if (mAvatar.isEmpty()) {
            return
        }
        mViewModel.updateUser(
            mBinding.edtNickName.text.toString(),
            mBinding.edtPass.text.toString(),
            mAvatar
        )
    }
}