package com.yk.silence.customnode.widget.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.databinding.ItemAboutLayoutBinding
import com.yk.silence.customnode.db.mine.MyselfModel

class AboutAdapter(var layoutID: Int = R.layout.item_about_layout) :
    BaseQuickAdapter<MyselfModel, BaseDataBindingHolder<ItemAboutLayoutBinding>>(layoutID) {

    var mOnItemClickListener: ((position: Int) -> Unit)? = null

    override fun convert(holder: BaseDataBindingHolder<ItemAboutLayoutBinding>, item: MyselfModel) {
        holder.dataBinding?.mineData = item
        holder.itemView.run {
            setOnClickListener {
                mOnItemClickListener?.invoke(holder.adapterPosition)
            }
        }
    }
}