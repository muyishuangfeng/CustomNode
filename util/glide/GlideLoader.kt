package com.yk.silence.customnode.util.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.lzy.ninegrid.NineGridView
import com.yk.silence.customnode.R

fun ImageView.load(
    url: String,
    loadOnlyFromCache: Boolean = false,
    onLoadingFinished: () -> Unit = {}
) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }
    val requestOptions = RequestOptions.placeholderOf(R.drawable.placeholder)
        .dontTransform()
        .onlyRetrieveFromCache(loadOnlyFromCache)
    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .listener(listener)
        .into(this)
}


/** Glide 加载  */
class GlideImageLoader : NineGridView.ImageLoader {
    override fun onDisplayImage(
        context: Context,
        imageView: ImageView,
        url: String
    ) {
        Glide.with(context).load(url) //
            .placeholder(R.drawable.placeholder) //
            .error(R.drawable.placeholder) //
            .diskCacheStrategy(DiskCacheStrategy.ALL) //
            .into(imageView)
    }

    override fun getCacheImage(url: String): Bitmap? {
        return null
    }
}