package com.yk.silence.customnode.viewmodel.friend

class FriendRepository {

    /**
     * 获取会话列表
     */
    suspend fun getData() = FriendHelper.getFriendList()

    /**
     * 添加会话
     */
    suspend fun addConversation(userName: String) = FriendHelper.addFConversation(userName)
}