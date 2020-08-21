package com.yk.silence.customnode.viewmodel.chat

import com.yk.silence.customnode.im.IMSClientBootstrap
import com.yk.silence.customnode.im.bean.SingleMessage
import com.yk.silence.customnode.im.message.MessageProcessor
import com.yk.silence.customnode.im.message.MessageType
import java.util.*


class ChatRepository {


    /**
     * 进入聊天
     */
    suspend fun enterChat(userId: String, token: String, hosts: String, appStatus: Int) {
        IMSClientBootstrap.getInstance().init(userId, token, hosts, appStatus)
    }




    /**
     * 发送消息
     */
    suspend fun sendMsg(fromID: String, toID: String, content: String) {
        val message = SingleMessage()
        message.msgId = UUID.randomUUID().toString()
        message.msgType = MessageType.SINGLE_CHAT.msgType
        message.msgContentType = MessageType.MessageContentType.TEXT.msgContentType
        message.fromId = fromID
        message.toId = toID
        message.timestamp = System.currentTimeMillis()
        message.content = content
        MessageProcessor.getInstance().sendMsg(message)
    }


}