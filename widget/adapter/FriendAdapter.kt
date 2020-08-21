package com.yk.silence.customnode.widget.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.util.TimeUtil
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_chat_list_layout.view.*

class FriendAdapter(
    layoutID: Int = R.layout.item_chat_list_layout
) :
    BaseQuickAdapter<FriendModel, BaseViewHolder>(layoutID) {

    var mOnItemClickListener: ((position: Int) -> Unit)? = null


    override fun convert(holder: BaseViewHolder, item: FriendModel) {
        holder.itemView.run {
            txt_item_chat_name.text = item.user_name
            txt_item_chat_time.text = TimeUtil.getChatTime(item.friend_time.toLong())
            setOnClickListener {
                mOnItemClickListener?.invoke(holder.adapterPosition)
            }
            GlideUtils.loadPathWithCircle(context,item.user_avatar,img_item_chat_avatar)
        }
    }


    open class CustomDiffCallBack : DiffUtil.ItemCallback<FriendModel>() {

        override fun areItemsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean {
            return oldItem.user_id == newItem.user_id &&
                    oldItem.id == newItem.id &&
                    oldItem.user_name == newItem.user_name &&
                    oldItem.user_avatar == newItem.user_avatar
        }


    }

}