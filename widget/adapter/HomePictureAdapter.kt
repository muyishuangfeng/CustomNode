package com.yk.silence.customnode.widget.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.db.HomePictureModel
import com.yk.silence.customnode.util.glide.GlideUtils
import com.yk.silence.customnode.widget.activity.DetailActivity
import kotlinx.android.synthetic.main.item_home_picture_layout.view.*

class HomePictureAdapter(layoutID: Int = R.layout.item_home_picture_layout) :
    BaseQuickAdapter<HomePictureModel, BaseViewHolder>(layoutID) {


    override fun convert(holder: BaseViewHolder, item: HomePictureModel) {
        holder.itemView.run {
            GlideUtils.loadPathWithCircle(context, item.imgUrl, img_item_home_picture)
            setOnClickListener {
                ActivityManager.start(
                    DetailActivity::class.java, mapOf(
                        PARAM_ARTICLE to item.imgUrl
                    )
                )
            }
        }
    }
}