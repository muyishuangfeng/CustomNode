package com.yk.silence.customnode.widget.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.CHAT_USER_AVATAR
import com.yk.silence.customnode.common.MSG_TYPE_RECEIVE
import com.yk.silence.customnode.common.MSG_TYPE_SEND
import com.yk.silence.customnode.db.friend.ChatModel
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_chat_receive_layout.view.*
import kotlinx.android.synthetic.main.item_chat_send_layout.view.*

class ChatAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    var mList: MutableList<ChatModel> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MSG_TYPE_SEND) {
            ChatSendViewHolder(
                LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_send_layout, parent, false)
            )
        } else {
            ChatReceiveViewHolder(
                LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_receive_layout, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return mList.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList[position].chat_type) {
            0 -> {//发送
                MSG_TYPE_SEND
            }
            1 -> {//接收
                MSG_TYPE_RECEIVE
            }
            else -> MSG_TYPE_SEND
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mType = mList[position].chat_content_type
        val model = mList[position]
        if (holder is ChatSendViewHolder) {
            holder.itemView.run {
                GlideUtils.loadPathWithCircle(
                    mContext, model.chat_avatar,
                    img_item_chat_send_avatar
                )
                when (mType) {
                    0 -> {//文本
                        txt_item_chat_send_text.text = model.chat_content
                    }
                    1 -> {//图片

                    }
                    2 -> {//语音

                    }
                }
            }

        } else if (holder is ChatReceiveViewHolder) {
            holder.itemView.run {
                GlideUtils.loadPathWithCircle(
                    mContext, CHAT_USER_AVATAR,
                    img_item_chat_receive_avatar
                )
                when (mType) {
                    0 -> {//文本
                        txt_item_chat_receive_text.text = model.chat_content
                    }
                    1 -> {//图片

                    }
                    2 -> {//语音

                    }
                }
            }

        }
    }



    /**
     * 添加发送数据
     *
     * @param position
     * @param model
     */
    fun addData(position: Int, model: ChatModel) {
        mList.add(position, model)
        notifyItemInserted(position) //通知演示插入动画
        notifyItemRangeChanged(position, mList.size - position) //通知数据与界面重新绑定
    }

    /**
     * 发送
     */
    class ChatSendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 接收
     */
    class ChatReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}