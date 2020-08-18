package com.yk.silence.customnode.widget.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.preview.NineGridViewClickAdapter
import com.yk.silence.customnode.R
import com.yk.silence.customnode.model.CircleModel
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_circle_layout.view.*
import java.util.*

class CircleAdapter(layoutID: Int = R.layout.item_circle_layout) :
    BaseQuickAdapter<CircleModel, BaseViewHolder>(layoutID) {


    override fun convert(holder: BaseViewHolder, item: CircleModel) {
        holder.itemView.run {
            GlideUtils.loadPathWithCircle(context, item.avatar, img_item_circle_avatar)
            txt_item_circle_name.text = item.userName
            txt_item_circle_content.text = item.content
            val imageInfo =
                ArrayList<ImageInfo>()
            for (image in item.pictures) {
                val info = ImageInfo()
                info.setThumbnailUrl(image.imgUrl)
                info.setBigImageUrl(image.imgUrl)
                imageInfo.add(info)
            }
            rlv_item_circle.setAdapter(NineGridViewClickAdapter(context,imageInfo))
            rlv_item_access.adapter = CircleAccessAdapter().apply {
                setDiffCallback(CircleAccessAdapter.CircleAccessDiffCallBack())
                setDiffNewData(item.AccessList)
            }
        }
    }


    open class CircleDiffCallBack : DiffUtil.ItemCallback<CircleModel>() {

        override fun areItemsTheSame(oldItem: CircleModel, newItem: CircleModel): Boolean {
            return oldItem.userName == newItem.userName
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CircleModel,
            newItem: CircleModel
        ): Boolean {
            return oldItem.userName == newItem.userName &&
                    oldItem.avatar == newItem.avatar &&
                    oldItem.content == newItem.content
        }
    }
}

