package com.yk.silence.customnode.widget.activity

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.ActivityRegisterBinding
import com.yk.silence.customnode.util.JPushLoginUtil
import com.yk.silence.customnode.viewmodel.register.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseVMActivity<RegisterViewModel, ActivityRegisterBinding>() {


    override fun getLayoutID() = R.layout.activity_register

    override fun viewModelClass() = RegisterViewModel::class.java

    override fun initBinding(binding: ActivityRegisterBinding) {
        super.initBinding(binding)
        binding.imgRegisterClose.setOnClickListener {
            ActivityManager.finish(RegisterActivity::class.java)
        }

        binding.edtConfirmPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                btn_register.performClick()
                true
            } else {
                false
            }
        }

        binding.btnRegister.setOnClickListener {
            binding.txtAccount.error = ""
            binding.txtPassword.error = ""
            binding.txtConfirmPassword.error = ""

            val account = edt_account.text.toString()
            val password = edt_password.text.toString()
            val confirmPass = edt_confirm_password.text.toString()

            when {
                account.isEmpty() -> binding.txtAccount.error =
                    getString(R.string.account_can_not_be_empty)
                account.length < 3 -> binding.txtAccount.error =
                    getString(R.string.account_length_over_three)
                password.isEmpty() -> binding.txtPassword.error =
                    getString(R.string.password_can_not_be_empty)
                password.length < 6 -> binding.txtPassword.error =
                    getString(R.string.password_length_over_six)
                confirmPass.isEmpty() -> binding.txtConfirmPassword.error =
                    getString(R.string.confirm_password_can_not_be_empty)
                password != confirmPass -> binding.txtConfirmPassword.error =
                    getString(R.string.two_password_are_inconsistent)
                else -> mViewModel.register(account, password)
            }
        }
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mRegisterResult.observe(this@RegisterActivity, Observer {
                if (it) {
                    ActivityManager.start(MainActivity::class.java)
                    ActivityManager.finish(RegisterActivity::class.java)
                }
            })
        }
    }
}