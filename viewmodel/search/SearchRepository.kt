package com.yk.silence.customnode.viewmodel.search

import com.yk.silence.customnode.net.retrofit.RetrofitClient

class SearchRepository {
    /**
     * 查询好友
     */
    suspend fun searchFriend(id: Int) = RetrofitClient.apiService.searchFriend(id).apiData()
}