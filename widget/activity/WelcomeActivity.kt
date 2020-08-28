package com.yk.silence.customnode.widget.activity

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseVMActivity<BaseViewModel, ActivityWelcomeBinding>() {

    override fun getLayoutID() = R.layout.activity_welcome

    override fun viewModelClass() = BaseViewModel::class.java


    override fun initData() {
        super.initData()
        checkLoginState {
            window.decorView.postDelayed({
                ActivityManager.start(MainActivity::class.java)
                ActivityManager.finish(WelcomeActivity::class.java)
            }, 1000)
        }
    }
}