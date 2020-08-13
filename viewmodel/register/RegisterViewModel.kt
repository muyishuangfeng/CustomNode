package com.yk.silence.customnode.viewmodel.register

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel

class RegisterViewModel : BaseViewModel() {

    private val mLoginRepository: RegisterRepository by lazy { RegisterRepository() }

    //提交
    val mSubmitting = MutableLiveData<Boolean>()

    //注册结果
    val mRegisterResult = MutableLiveData<Boolean>()


    /**
     * 登录
     */
    fun register(account: String, password: String) {
        mSubmitting.value = true
        launch(
            block = {
                //val userInfo = mLoginRepository.register(account, password)
//                mUserRepository.updateUserInfo(userInfo)
//                EventBus.post(Constants.USER_COLLECT_UPDATED, true)
                mSubmitting.value = false
                mRegisterResult.value = true
            },
            error = {
                mSubmitting.value = false
                mRegisterResult.value = false
            }

        )
    }

}