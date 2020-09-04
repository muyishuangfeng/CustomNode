package com.yk.silence.customnode.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yk.silence.customnode.util.glide.GlideUtils

object BindingUtil {
    /**
     * 设置图片
     */
    @JvmStatic
    @BindingAdapter(value = ["imageUrl"], requireAll = false)
    fun loadImage(view: ImageView, url: String) {
        GlideUtils.loadPathWithCircle(view.context, url, view)
    }

    /**
     * 设置时间
     */
    @JvmStatic
    @BindingAdapter(value = ["timeFormat"])
    fun setTime(view: TextView, time: String) {
        view.text = TimeUtil.getChatTime(time.toLong())

    }

}