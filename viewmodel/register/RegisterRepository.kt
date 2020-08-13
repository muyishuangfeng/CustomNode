package com.yk.silence.customnode.viewmodel.register

import com.yk.silence.customnode.net.retrofit.RetrofitClient


class RegisterRepository {
    /**
     * 注册
     */
    suspend fun register(username: String, password: String) =
        RetrofitClient.apiService.register(username, password).apiData()
}