package com.yk.silence.customnode.viewmodel.mine

import com.yk.silence.customnode.net.retrofit.RetrofitClient

class MineRepository {
    /**
     * 更新用户信息
     */
    suspend fun updateUser(
        username: String,
        password: String,
        user_avatar: String
    ) = RetrofitClient.apiService.update(username, password, user_avatar).apiData()
}