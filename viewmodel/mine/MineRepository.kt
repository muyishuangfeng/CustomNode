package com.yk.silence.customnode.viewmodel.mine

import com.yk.silence.customnode.net.retrofit.RetrofitClient

class MineRepository {
    /**
     * 更新用户信息
     */
    suspend fun updateUser(
        id:Int,
        username: String,
        password: String,
        user_avatar: String
    ) = RetrofitClient.apiService.update(id,username, password, user_avatar).apiData()
}