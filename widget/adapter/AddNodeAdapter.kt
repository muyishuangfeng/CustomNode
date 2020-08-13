package com.yk.silence.customnode.widget.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_add_node_layout.view.*

class AddNodeAdapter(layoutID: Int = R.layout.item_add_node_layout) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutID) {

    var mOnItemDeleteListener: ((position: Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.itemView.run {
            GlideUtils.loadPathWithCircle(context, item!!, img_item_add_node)
            btn_item_node_delete.setOnClickListener {
                mOnItemDeleteListener?.invoke(helper.adapterPosition)
            }
        }
    }
}