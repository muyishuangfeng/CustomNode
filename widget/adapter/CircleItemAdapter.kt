package com.yk.silence.customnode.widget.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.model.CircleItemImage
import com.yk.silence.customnode.util.glide.load
import com.yk.silence.customnode.widget.activity.PictureDetailActivity
import kotlinx.android.synthetic.main.item_circle_image_layout.view.*

class CircleItemAdapter(layoutID: Int = R.layout.item_circle_image_layout) :
    BaseQuickAdapter<CircleItemImage, BaseViewHolder>(layoutID) {

    override fun convert(holder: BaseViewHolder, item: CircleItemImage) {
        holder.itemView.run {
            img_item_circle_photo.apply {
                load(item.imgUrl)
                transitionName = item.imgUrl
            }
            setOnClickListener {
                ActivityManager.start(
                    PictureDetailActivity::class.java, mapOf(
                        PARAM_ARTICLE to item.imgUrl
                    ), img_item_circle_photo
                )
            }
        }
    }


    open class CircleItemDiffCallBack : DiffUtil.ItemCallback<CircleItemImage>() {

        override fun areItemsTheSame(oldItem: CircleItemImage, newItem: CircleItemImage): Boolean {
            return oldItem.index == newItem.index
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CircleItemImage,
            newItem: CircleItemImage
        ): Boolean {
            return oldItem.index == newItem.index && oldItem.imgUrl == newItem.imgUrl
        }


    }
}