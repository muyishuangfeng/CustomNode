package com.yk.silence.customnode.widget.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.db.node.HomePictureModel
import com.yk.silence.customnode.util.glide.load
import com.yk.silence.customnode.widget.activity.PictureDetailActivity
import kotlinx.android.synthetic.main.item_home_picture_layout.view.*

class HomePictureAdapter(layoutID: Int = R.layout.item_home_picture_layout) :
    BaseQuickAdapter<HomePictureModel, BaseViewHolder>(layoutID) {


    override fun convert(holder: BaseViewHolder, item: HomePictureModel) {
        holder.itemView.run {
            img_item_home_picture.apply {
                load(item.imgUrl)
                transitionName = item.imgUrl
            }
            setOnClickListener {
                ActivityManager.start(
                    PictureDetailActivity::class.java, mapOf(
                        PARAM_ARTICLE to item.imgUrl
                    ), img_item_home_picture
                )
            }

        }
    }
}