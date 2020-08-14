package com.yk.silence.customnode.viewmodel.chat

import android.view.View
import cn.jpush.im.android.api.content.TextContent
import cn.jpush.im.android.api.enums.MessageDirect
import cn.jpush.im.android.api.enums.MessageStatus
import cn.jpush.im.android.api.model.Message
import com.yk.silence.customnode.widget.adapter.ChatAdapter
import kotlinx.android.synthetic.main.item_chat_send_layout.view.*

object ChatHelper {

    /**
     * 发送消息
     */
    fun sendTextMsg(msg: Message, holder: ChatAdapter.ChatSendHolder, position: Int) {
        val content = (msg.content as TextContent).text

        holder.itemView.run {
            txt_item_chat_send_text.text = content
            txt_item_chat_send_text.tag = position
            // 检查发送状态，发送方有重发机制
            if (msg.direct == MessageDirect.send) {
                when (msg.status) {
                    MessageStatus.created -> {
                        pgb_item_text_send_status.visibility = View.VISIBLE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.GONE
                    }
                    MessageStatus.send_success -> {
                        pgb_item_text_send_status.visibility = View.GONE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.VISIBLE
                    }
                    MessageStatus.send_fail -> {
                        pgb_item_text_send_status.visibility = View.GONE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.VISIBLE

                    }
                    MessageStatus.send_going -> {
                        pgb_item_text_send_status.visibility = View.VISIBLE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }

        }


    }

}