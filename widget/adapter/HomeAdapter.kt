package com.yk.silence.customnode.widget.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.db.HomeNode
import com.yk.silence.customnode.util.TimeUtil
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_home_layout.view.*
import java.util.*

class HomeAdapter(layoutID: Int = R.layout.item_home_layout) :
    BaseQuickAdapter<HomeNode, BaseViewHolder>(layoutID) {

    var mOnItemClickListener: ((position: Int) -> Unit)? = null
    var mOnItemDeleteListener: ((position: Int) -> Unit)? = null

    override fun convert(holder: BaseViewHolder, item: HomeNode) {
        holder.run {
            itemView.run {
                txt_home_content.text = item.homeModel!!.content
                txt_home_name.text = item.homeModel!!.name
                txt_home_time.text = TimeUtil.getChatTime(item.homeModel!!.time.toLong())
                img_home_edit.setOnClickListener {
                    mOnItemDeleteListener?.invoke(holder.adapterPosition)
                }
                setOnClickListener {
                    mOnItemClickListener?.invoke(holder.adapterPosition)
                }
                rlv_item_home.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rlv_item_home.adapter = HomePictureAdapter().apply {
                    setNewData(item.pictures)
                }
                GlideUtils.loadPathWithCircle(context, item.homeModel!!.avatar, img_home_avatar)
            }
        }
    }
}