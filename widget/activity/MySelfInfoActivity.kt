package com.yk.silence.customnode.widget.activity

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.databinding.ActivityMySelfInfoBinding
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.impl.OnCameraClickListener
import com.yk.silence.customnode.impl.OnCameraResultListener
import com.yk.silence.customnode.impl.OnOssResultListener
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.model.UserModel
import com.yk.silence.customnode.ui.dialog.ChooseDialogHelper
import com.yk.silence.customnode.util.CameraUtil
import com.yk.silence.customnode.util.EventUtil
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
                                this@MySelfInfoActivity
                            )
                        } else {
                            //跳转到相机
                            CameraUtil.openCamera(
                                this@MySelfInfoActivity
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
                    if (it.user_avatar != null) {
                        mAvatar = it.user_avatar!!

                    }

                }

            })
            mUpdateState.observe(this@MySelfInfoActivity, Observer {
                if (it) {
                    ToastUtil.getInstance().shortToast(this@MySelfInfoActivity, "更新成功")
                    EventUtil.send(EventModel(MSG_CODE_UPDATE_INFO))
                    ActivityManager.finish(MySelfInfoActivity::class.java)
                } else {
                    ToastUtil.getInstance().shortToast(this@MySelfInfoActivity, "更新失败")
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CameraUtil.onActivityResult(requestCode, resultCode, data, object : OnCameraResultListener {
            override fun onCameraResult(path: String?) {
                if (path != null) {
                    GlideUtils.loadPathWithOutCache(
                        this@MySelfInfoActivity,
                        path,
                        mBinding.imgMyselfAvatar
                    )
                    uploadFile(path)
                }

            }

            override fun onAlbumResult(path: String?) {
                if (path != null) {
                    GlideUtils.loadPathWithOutCache(
                        this@MySelfInfoActivity,
                        path,
                        mBinding.imgMyselfAvatar
                    )
                    uploadFile(path)
                }
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
                mUser.id.toString() + USER_AVATAR,
                path,
                object : OnOssResultListener {
                    override fun onResultSuccess() {
                        mAvatar = BASE_OSS_URL + mUser.id.toString() + USER_AVATAR
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