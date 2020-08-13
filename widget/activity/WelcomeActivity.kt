package com.yk.silence.customnode.widget.activity

import android.text.TextUtils
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.LOGIN_STATUS
import com.yk.silence.customnode.databinding.ActivityWelcomeBinding
import com.yk.silence.customnode.util.JPushLoginUtil
import com.yk.silence.customnode.util.SPUTil
import java.util.concurrent.TimeUnit

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    override fun getLayoutID() = R.layout.activity_welcome

    override fun initBinding(binding: ActivityWelcomeBinding) {
        super.initBinding(binding)
        if (TextUtils.isEmpty(SPUTil.getString(this, LOGIN_STATUS))) {
            JPushLoginUtil.welcome()
        } else {
            window.decorView.postDelayed({
                ActivityManager.start(MainActivity::class.java)
                ActivityManager.finish(WelcomeActivity::class.java)
            }, 1000)
        }


    }
}