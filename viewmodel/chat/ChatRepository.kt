package com.yk.silence.customnode.viewmodel.chat

import cn.jpush.im.android.api.JMessageClient

class ChatRepository {
    /**
     * 进入聊天
     */
    suspend fun enterChat(userName: String) {
        JMessageClient.enterSingleConversation(userName)
    }

    /**
     * 退出聊天
     */
    suspend fun exitChat() {
        JMessageClient.exitConversation()
    }
}