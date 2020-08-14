package com.yk.silence.customnode.widget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.jpush.im.android.api.enums.ContentType
import cn.jpush.im.android.api.enums.MessageDirect
import cn.jpush.im.android.api.model.Message
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.MSG_TYPE_RECEIVE
import com.yk.silence.customnode.common.MSG_TYPE_SEND
import com.yk.silence.customnode.viewmodel.chat.ChatHelper
import kotlinx.android.synthetic.main.item_chat_send_layout.view.*

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class ChatAdapter(list: MutableList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            MSG_TYPE_RECEIVE -> {
                return ChatReceiveHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_chat_receive_layout, parent, false)
                )
            }
            MSG_TYPE_SEND -> {
                return ChatSendHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_chat_send_layout, parent, false)
                )
            }
            else -> {
                return ChatSendHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_chat_send_layout, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = mList[position]
        if (holder is ChatSendHolder) {
            holder.itemView.run {
                when (message.contentType) {
                    ContentType.text -> {//文本
                        ChatHelper.sendTextMsg(message, holder, position)
                    }
                    ContentType.image -> {//图片

                    }
                    ContentType.voice -> {//语音

                    }
                    else ->
                        ContentType.text

                }
            }
        }


    }


    override fun getItemViewType(position: Int): Int {
        val message: Message = mList[position]
        return when (message.direct) {
            MessageDirect.send -> {
                MSG_TYPE_SEND
            }
            MessageDirect.receive -> {
                MSG_TYPE_RECEIVE
            }
            else -> getItemViewType(position)
        }
    }

    /**
     * 发送
     */
    class ChatSendHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 接收
     */
    class ChatReceiveHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}