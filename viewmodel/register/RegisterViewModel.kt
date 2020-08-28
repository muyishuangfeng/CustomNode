package com.yk.silence.customnode.viewmodel.register

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.model.UserModel

class RegisterViewModel : BaseViewModel() {

    private val mRegisterRepository: RegisterRepository by lazy { RegisterRepository() }

    //注册结果
    val mRegisterResult = MutableLiveData<Boolean>()


    /**
     * 注册
     */
    fun register(account: String, password: String) {
        mRegisterResult.value = false
        launch(
            block = {
                val userInfo = mRegisterRepository.register(account, password)
                mUserRepository.updateUserInfo(userInfo)
                mUserRepository.isLogin()
                mRegisterResult.value = true
            },
            error = {
                mRegisterResult.value = false
            }

        )
    }


}