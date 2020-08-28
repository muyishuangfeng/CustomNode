package com.yk.silence.customnode.viewmodel.login

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.model.UserModel

class LoginViewModel : BaseViewModel() {

    private val mLoginRepository: LoginRepository by lazy { LoginRepository() }



    //登录结果
    val mLoginResult = MutableLiveData<Boolean>()


    /**
     * 登录
     */
    fun login(account: String, password: String) {
        mLoginResult.value = false
        launch(
            block = {
                val userInfo = mLoginRepository.login(account, password)
                mUserRepository.updateUserInfo(userInfo)
                mUserRepository.isLogin()
                mLoginResult.value = true
            },
            error = {
                mLoginResult.value = false
            }

        )
    }

}