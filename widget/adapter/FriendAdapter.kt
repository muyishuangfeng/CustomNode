package com.yk.silence.customnode.widget.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import cn.jpush.im.android.api.enums.ConversationType
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.util.TimeFormat
import kotlinx.android.synthetic.main.item_chat_list_layout.view.*

class FriendAdapter(
    layoutID: Int = R.layout.item_chat_list_layout
) :
    BaseQuickAdapter<Conversation, BaseViewHolder>(layoutID) {

    var mOnItemClickListener: ((position: Int) -> Unit)? = null


    override fun convert(holder: BaseViewHolder, item: Conversation) {
        holder.itemView.run {
            val mUserInfo = item.targetInfo as UserInfo
            txt_item_chat_name.text = mUserInfo.nickname
            txt_item_chat_time.text = TimeFormat(context, item.lastMsgDate).time
            if (item.unReadMsgCnt > 0) {
                txt_item_chat_number.visibility = View.VISIBLE
                if (item.type == ConversationType.single) {
                    if (item.unReadMsgCnt < 100) {
                        txt_item_chat_number.text = item.unReadMsgCnt.toString()
                    } else {
                        txt_item_chat_number.text =
                            context.resources.getString(R.string.text_99)
                    }
                }
            } else {
                txt_item_chat_number.visibility = View.INVISIBLE
            }
            setOnClickListener {
                mOnItemClickListener?.invoke(holder.adapterPosition)
            }
        }
    }

    /**
     * 添加新的会话
     */
    fun addNewConversation(conv: Conversation) {
        addData(0, conv)
        notifyDataSetChanged()
    }


    open class CustomDiffCallBack : DiffUtil.ItemCallback<Conversation>() {

        override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.id == newItem.id &&
                    oldItem.latestMessage == newItem.latestMessage &&
                    oldItem.targetInfo == newItem.targetInfo
        }


    }

}