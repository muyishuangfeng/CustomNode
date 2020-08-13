package com.yk.silence.customnode.widget.activity

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.ActivityLoginBinding
import com.yk.silence.customnode.util.JPushLoginUtil
import com.yk.silence.customnode.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVMActivity<LoginViewModel, ActivityLoginBinding>() {


    override fun getLayoutID() = R.layout.activity_login

    override fun viewModelClass() = LoginViewModel::class.java

    override fun initBinding(binding: ActivityLoginBinding) {
        super.initBinding(binding)
        binding.imgClose.setOnClickListener {
            ActivityManager.finish(LoginActivity::class.java)
        }

        binding.txtRegister.setOnClickListener {
            ActivityManager.start(RegisterActivity::class.java)
        }

        binding.edtPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                btn_login.performClick()
                true
            } else {
                false
            }
        }

        binding.btnLogin.setOnClickListener {
            binding.txtAccount.error = ""
            binding.txtPassword.error = ""
            val account = binding.edtAccount.text.toString()
            val password = binding.edtPassword.text.toString()
            when {
                account.isEmpty() -> binding.txtAccount.error =
                    getString(R.string.account_can_not_be_empty)
                password.isEmpty() -> binding.txtPassword.error =
                    getString(R.string.password_can_not_be_empty)
                else -> mViewModel.login(account, password)
            }
        }
    }


    override fun observer() {
        super.observer()
        mViewModel.run {
            mSubmitting.observe(this@LoginActivity, Observer {
                if (it) {
                    JPushLoginUtil.login(1)
                    showProgressDialog(R.string.text_logining)
                } else hideProgressDialog()
            })
            mLoginResult.observe(this@LoginActivity, Observer {
                if (it) {
                    ActivityManager.finish(LoginActivity::class.java)
                }
            })
        }
    }
}