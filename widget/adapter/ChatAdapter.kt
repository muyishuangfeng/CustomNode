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

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: MutableList<Message> = arrayListOf()


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
                        ChatHelper.sendImgMsg(context, message, holder)
                    }
                    ContentType.voice -> {//语音
                        ChatHelper.sendVoice(context, message, holder, position)
                    }
                    else ->
                        ChatHelper.sendTextMsg(message, holder, position)

                }
            }
        } else if (holder is ChatReceiveHolder) {
            holder.itemView.run {
                when (message.contentType) {
                    ContentType.text -> {//文本
                        ChatHelper.receiveTextMsg(message, holder, position)
                    }
                    ContentType.image -> {//图片
                        ChatHelper.receiveImgMsg(message, holder)
                    }
                    ContentType.voice -> {//语音
                        ChatHelper.receiveVoice(context, message, holder, position)
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
     * 添加发送数据
     *
     * @param position
     * @param model
     */
    fun addSendData(model: Message) {
        mList.add(model)
        notifyDataSetChanged()
    }

    /**
     * 设置数据
     */
    fun setList(list: MutableList<Message>) {
        mList.addAll(list)
        notifyDataSetChanged()
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