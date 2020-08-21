package com.yk.silence.customnode.widget.activity

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.sdk.android.oss.common.utils.OSSUtils
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.databinding.ActivityAddFriendBinding
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.impl.OnCameraClickListener
import com.yk.silence.customnode.impl.OnOssResultListener
import com.yk.silence.customnode.ui.ChooseDialogHelper
import com.yk.silence.customnode.util.CameraUtil
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.util.glide.GlideUtils
import com.yk.silence.customnode.util.oss.OSSHelper
import com.yk.silence.customnode.viewmodel.friend.FriendViewModel
import com.yk.silence.toolbar.CustomTitleBar

class AddFriendActivity : BaseVMActivity<FriendViewModel, ActivityAddFriendBinding>() {

    private var mAvatar: String? = null
    private var mAvatarUrl: String? = null

    override fun getLayoutID() = R.layout.activity_add_friend

    override fun viewModelClass() = FriendViewModel::class.java

    override fun initBinding(binding: ActivityAddFriendBinding) {
        super.initBinding(binding)
        mBinding.titleAddFriend.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {
                ActivityManager.finish(AddFriendActivity::class.java)
            }

            override fun onRightClick() {

            }
        })
        mBinding.imgAddPhoto.setOnClickListener {
            ChooseDialogHelper.showCamera(
                this@AddFriendActivity,
                object : OnCameraClickListener {
                    override fun onCameraClick(position: Int) {
                        if (position == 1) {
                            //跳转到相册
                            CameraUtil.openAlbum(
                                this@AddFriendActivity,
                                REQUEST_CODE_OPEN_PHOTO_ALBUM
                            )
                        } else {
                            //跳转到相机
                            CameraUtil.openAlbum(
                                this@AddFriendActivity,
                                REQUEST_CODE_TAKE_PHOTO
                            )
                        }
                    }
                })
        }
        mBinding.btnAddSubmit.setOnClickListener { submit() }
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
                    mAvatarUrl = uri.toString()
                    GlideUtils.loadPathWithCircle(
                        this@AddFriendActivity,
                        uri.toString(),
                        mBinding.imgAddPhoto
                    )
                    uploadFile(uri.toString())
                }

                override fun onAlbumResult(path: String) {
                    mAvatarUrl = path
                    GlideUtils.loadPathWithCircle(
                        this@AddFriendActivity,
                        path,
                        mBinding.imgAddPhoto
                    )
                    uploadFile(path)
                }
            })
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mFriendState.observe(this@AddFriendActivity, Observer {
                Log.e("TAG", "添加成功")
                ActivityManager.finish(AddFriendActivity::class.java)
            })
        }
    }

    /**
     * 上传文件
     */
    private fun uploadFile(path: String) {
        CThreadPoolExecutor.runInBackground(Runnable {
            OSSHelper.updateFile(
                this@AddFriendActivity,
                mBinding.edtAddId.toString() + USER_AVATAR,
                path,
                object : OnOssResultListener {
                    override fun onResultSuccess() {
                        mAvatar = BASE_OSS_URL + mBinding.edtAddId.toString() + USER_AVATAR
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
        if (mBinding.edtAddId.toString().isEmpty()) {
            ToastUtil.getInstance().shortToast(this, "请输入ID")
            return
        }
        if (mBinding.edtAddName.toString().isEmpty()) {
            ToastUtil.getInstance().shortToast(this, "请输入昵称")
            return
        }
        if (mAvatarUrl == null) {
            ToastUtil.getInstance().shortToast(this, "请上传图片")
            return
        }
        val model = FriendModel()
        model.user_name = mBinding.edtAddName.text.toString()
        model.user_id = mBinding.edtAddId.text.toString()
        model.user_avatar = mAvatarUrl!!
        model.friend_time = System.currentTimeMillis().toString()
        mViewModel.addFriend(model)

    }

}