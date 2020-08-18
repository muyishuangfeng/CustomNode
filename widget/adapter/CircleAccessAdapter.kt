package com.yk.silence.customnode.widget.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.model.CircleAccess
import com.yk.silence.customnode.model.CircleItemImage
import kotlinx.android.synthetic.main.item_circle_access_layout.view.*

class CircleAccessAdapter(layoutID: Int = R.layout.item_circle_access_layout) :
    BaseQuickAdapter<CircleAccess, BaseViewHolder>(layoutID) {

    override fun convert(holder: BaseViewHolder, item: CircleAccess) {
        holder.itemView.run {
            txt_item_circle_access_name.text = item.accessName
            txt_item_circle_access_other.text = item.accessOtherName
            txt_item_circle_access_content.text = item.accessContent
            if (item.accessOtherName.isNotEmpty()) {
                txt_item_circle_access_reply.visibility = View.VISIBLE
                txt_item_circle_access_other.visibility = View.VISIBLE
            } else {
                txt_item_circle_access_reply.visibility = View.GONE
                txt_item_circle_access_other.visibility = View.GONE
            }
        }
    }

    open class CircleAccessDiffCallBack : DiffUtil.ItemCallback<CircleAccess>() {

        override fun areItemsTheSame(oldItem: CircleAccess, newItem: CircleAccess): Boolean {
            return oldItem.accessName == newItem.accessName
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CircleAccess,
            newItem: CircleAccess
        ): Boolean {
            return oldItem.accessName == newItem.accessName && oldItem.accessContent == newItem.accessContent
        }
    }
}