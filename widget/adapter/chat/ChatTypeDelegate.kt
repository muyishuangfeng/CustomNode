package com.yk.silence.customnode.widget.adapter.chat

import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.MSG_TYPE_RECEIVE
import com.yk.silence.customnode.common.MSG_TYPE_SEND
import com.yk.silence.customnode.db.friend.ChatModel

/**
 * 聊天代理类
 */
class ChatTypeDelegate : BaseMultiTypeDelegate<ChatModel>() {

    init {
        addItemType(MSG_TYPE_SEND, R.layout.item_chat_send_layout)
        addItemType(MSG_TYPE_RECEIVE, R.layout.item_chat_receive_layout)
    }


    override fun getItemType(data: List<ChatModel>, position: Int): Int {
        return when (data[position].chat_type) {
            0 -> {
                MSG_TYPE_SEND
            }
            1 -> {
                MSG_TYPE_RECEIVE
            }
            else -> MSG_TYPE_SEND
        }
    }
}