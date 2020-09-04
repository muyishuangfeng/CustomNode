package com.yk.silence.customnode.widget.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.databinding.ItemHomeLayoutBinding
import com.yk.silence.customnode.db.node.HomeNode
import com.yk.silence.customnode.util.TimeUtil
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_home_layout.view.*

class HomeAdapter(layoutID: Int = R.layout.item_home_layout) :
    BaseQuickAdapter<HomeNode, BaseDataBindingHolder<ItemHomeLayoutBinding>>(layoutID) {

    var mOnItemClickListener: ((position: Int) -> Unit)? = null
    var mOnItemDeleteListener: ((position: Int) -> Unit)? = null


    override fun convert(holder: BaseDataBindingHolder<ItemHomeLayoutBinding>, item: HomeNode) {
        holder.dataBinding?.model = item.homeModel
        holder.dataBinding?.rlvItemHome?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.dataBinding?.rlvItemHome?.adapter = HomePictureAdapter().apply {
            setList(item.pictures)
        }
        holder.run {
            itemView.run {
                img_home_edit.setOnClickListener {
                    mOnItemDeleteListener?.invoke(holder.adapterPosition)
                }
                setOnClickListener {
                    mOnItemClickListener?.invoke(holder.adapterPosition)
                }
            }
        }
    }
}