package com.yk.silence.customnode.viewmodel.chat

import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Message
import com.yk.silence.customnode.common.JPUSH_TARGET_APP_KEY
import java.io.File

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

    /**
     * 获取消息列表
     */
    suspend fun getMessageList(mTargetId: String): MutableList<Message> {
        return JMessageClient.getSingleConversation(mTargetId, JPUSH_TARGET_APP_KEY).allMessage
    }

    /**
     * 发送文本
     */
    suspend fun createTextMsg(userName: String, content: String): Message {
        return JMessageClient.createSingleTextMessage(userName, JPUSH_TARGET_APP_KEY, content)
    }

    /**
     * 发送图片
     */
    suspend fun createImageMsg(userName: String, file: File): Message {
        return JMessageClient.createSingleImageMessage(userName, JPUSH_TARGET_APP_KEY, file)
    }

    /**
     * 发送语音
     */
    suspend fun createAudioMsg(userName: String, file: File, duration: Int): Message {
        return JMessageClient.createSingleVoiceMessage(
            userName,
            JPUSH_TARGET_APP_KEY,
            file,
            duration
        )
    }
}