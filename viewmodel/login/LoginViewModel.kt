package com.yk.silence.customnode.viewmodel.login

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel

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
//                mUserRepository.updateUserInfo(userInfo)
//                EventBus.post(Constants.USER_COLLECT_UPDATED, true)
                mSubmitting.value = false
                mLoginResult.value = true
            },
            error = {
                mSubmitting.value = false
                mLoginResult.value = false
            }

        )
    }

}