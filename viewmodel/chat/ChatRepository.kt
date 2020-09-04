package com.yk.silence.customnode.viewmodel.chat

import com.yk.silence.customnode.db.friend.ChatModel
import com.yk.silence.customnode.db.helper.RoomHelper
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
     * 查询所有消息
     */
    suspend fun searchAllMsg(chatID: String, userID: String, start: Int) =
        RoomHelper.queryChats(chatID, userID, start)

    /**
     * 查询所有消息
     */
    suspend fun searchAllMsg(chatID: String, userID: String) = RoomHelper.queryChats(chatID, userID)

    /**
     * 添加消息
     */
    suspend fun addChat(chatModel: ChatModel) = RoomHelper.addChat(chatModel)


    /**
     * 发送消息
     */
    suspend fun sendMsg(fromID: String, toID: String, content: String, code: Int) {
        val message = SingleMessage()
        message.msgId = UUID.randomUUID().toString()
        message.msgType = MessageType.SINGLE_CHAT.msgType
        when (code) {
            0 -> {
                message.msgContentType = MessageType.MessageContentType.TEXT.msgContentType
            }
            1 -> {
                message.msgContentType = MessageType.MessageContentType.IMAGE.msgContentType
            }
            else -> {
                message.msgContentType = MessageType.MessageContentType.VOICE.msgContentType
            }
        }
        message.fromId = fromID
        message.toId = toID
        message.timestamp = System.currentTimeMillis()
        message.content = content
        MessageProcessor.getInstance().sendMsg(message)
    }


}