package com.yk.silence.customnode.viewmodel.register

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.model.UserModel

class RegisterViewModel : BaseViewModel() {

    private val mLoginRepository: RegisterRepository by lazy { RegisterRepository() }

    //提交
    val mSubmitting = MutableLiveData<Boolean>()

    //注册结果
    val mRegisterResult = MutableLiveData<Boolean>()

    //登录结果
    val mLoginResult = MutableLiveData<Boolean>()


    /**
     * 登录
     */
    fun register(account: String, password: String) {
        mSubmitting.value = true
        launch(
            block = {
                //val userInfo = mLoginRepository.register(account, password)
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
     * 极光注册
     */
    fun jPushRegister(userID: Int) {
        mRegisterResult.value = false
        launch(
            block = {
            },
            error = {
                mRegisterResult.value = false
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