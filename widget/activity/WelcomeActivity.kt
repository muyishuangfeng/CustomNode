package com.yk.silence.customnode.widget.activity

import android.Manifest
import android.annotation.SuppressLint
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.ActivityWelcomeBinding
import com.yk.silent.permission.HiPermission
import com.yk.silent.permission.impl.PermissionCallback
import com.yk.silent.permission.model.PermissionItem

class WelcomeActivity : BaseVMActivity<BaseViewModel, ActivityWelcomeBinding>() {

    override fun getLayoutID() = R.layout.activity_welcome

    override fun viewModelClass() = BaseViewModel::class.java


    override fun initData() {
        super.initData()
        initPermission()
    }

    /**
     * 获取权限
     */
    @SuppressLint("CheckResult")
    private fun initPermission() {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(
            PermissionItem(
                Manifest.permission.RECORD_AUDIO,
                resources.getString(R.string.text_record_audio), R.drawable.permission_ic_camera
            )
        )
        permissionItems.add(
            PermissionItem(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                resources.getString(R.string.text_write_storage), R.drawable.permission_ic_storage
            )
        )
        permissionItems.add(
            PermissionItem(
                Manifest.permission.CAMERA,
                resources.getString(R.string.text_camera), R.drawable.permission_ic_camera
            )
        )
        permissionItems.add(
            PermissionItem(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                resources.getString(R.string.text_read_storage), R.drawable.permission_ic_phone
            )
        )

        HiPermission.create(this)
            .title(resources.getString(R.string.permission_get))
            .msg(resources.getString(R.string.permission_desc))
            .permissions(permissionItems)
            .checkMutiPermission(object : PermissionCallback {
                override fun onClose() {
                }

                override fun onDeny(permission: String?, position: Int) {
                }

                override fun onFinish() {
                    checkLoginState {
                        window.decorView.postDelayed({
                            ActivityManager.start(MainActivity::class.java)
                            ActivityManager.finish(WelcomeActivity::class.java)
                        }, 1000)
                    }
                }

                override fun onGuarantee(permission: String?, position: Int) {

                }
            })
    }
}