package com.yk.silence.customnode.viewmodel.login

import com.yk.silence.customnode.net.retrofit.RetrofitClient


class LoginRepository {
    /**
     * 登录
     */
    suspend fun login(username: String, password: String) =
        RetrofitClient.apiService.login(username, password).apiData()

    /**
     * 极光登录
     */
    suspend fun jPushLogin(userId: Int) = LoginHelper.instance.jPushLogin(userId)


}