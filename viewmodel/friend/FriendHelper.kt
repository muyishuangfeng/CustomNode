package com.yk.silence.customnode.viewmodel.friend

import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Conversation
import com.yk.silence.customnode.common.JPUSH_TARGET_APP_KEY

object FriendHelper {
    /**
     * 获取会话列表
     */
    suspend fun getFriendList(): MutableList<Conversation>? {
        return JMessageClient.getConversationList()
    }

    /**
     * 添加会话
     */
    suspend fun addFConversation(userName: String): Conversation {
        return Conversation.createSingleConversation(userName, JPUSH_TARGET_APP_KEY)
    }
}