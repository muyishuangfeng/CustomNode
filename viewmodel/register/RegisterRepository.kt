package com.yk.silence.customnode.viewmodel.register

import com.yk.silence.customnode.net.retrofit.RetrofitClient
import com.yk.silence.customnode.viewmodel.login.LoginHelper


class RegisterRepository {
    /**
     * 注册
     */
    suspend fun register(username: String, password: String) =
        RetrofitClient.apiService.register(username, password).apiData()

    /**
     * 极光注册
     */
    suspend fun jPushRegister(userId: Int) = LoginHelper.instance.jPushRegister(userId)

    /**
     * 极光登录
     */
    suspend fun jPushLogin(userId: Int) = LoginHelper.instance.jPushLogin(userId)
}