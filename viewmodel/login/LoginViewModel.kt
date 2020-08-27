package com.yk.silence.customnode.viewmodel.login

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.model.UserModel

class LoginViewModel : BaseViewModel() {

    private val mLoginRepository: LoginRepository by lazy { LoginRepository() }

    //提交
    val mSubmitting = MutableLiveData<Boolean>()

    //登录结果
    val mLoginResult = MutableLiveData<Boolean>()


    /**
     * 登录
     */
    fun login(account: String, password: String) {
        mSubmitting.value = true
        launch(
            block = {
                //val userInfo = mLoginRepository.login(account, password)
                val mUserInfo = UserModel(1, "1","","")
                mUserRepository.updateUserInfo(mUserInfo)
                mSubmitting.value = false
            },
            error = {
                mSubmitting.value = false
            }

        )
    }

    /**
     * 极光登录
     */
    fun jPushLogin(userID: Int) {
        mLoginResult.value = false
        launch(
            block = {
            },
            error = {
                mLoginResult.value = false
            }
        )
    }

}