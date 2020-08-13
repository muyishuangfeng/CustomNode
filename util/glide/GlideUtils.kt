package com.yk.silence.customnode.util.glide

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.yk.silence.customnode.R
import java.io.File


/**
 * Created by Silence on 2018/1/5.
 */
object GlideUtils {
    /**
     * 清除缓存
     */
    private fun clearCache(context: Context) {
        Glide.get(context).clearMemory()
        Thread(Runnable {
            Glide.get(context).clearDiskCache()
        }).start()

    }


    /**
     * 通过uri加载图片
     */
    fun loadUri(mContext: Activity, uri: String, img: ImageView) {
        val options = RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
        Glide.with(mContext).load(uri)
            .apply(options)
            .into(img)
    }

    /**
     * 通过url加载图片
     */
    fun loadUrl(activity: Context, url: String?, img: ImageView) {
        val options = RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
        Glide.with(activity).load(url)
            .apply(options)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img)
    }

    /**
     * 通过文件路径加载图片
     */
    fun loadPathWithOutCache(activity: Context, filePath: String?, img: ImageView) {
        clearCache(activity)
        Glide.with(activity)
            .asBitmap()
            .load(filePath)
            .apply(
                RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .skipMemoryCache(true) //跳过内存缓存
            )
            .into(img)
    }

    /**
     * 通过文件路径加载图片
     */
    fun loadPath(activity: Context, filePath: String?, img: ImageView) {
        Glide.with(activity).load(filePath).into(img)
    }



    /**
     * 通过文件加载图片
     */
    fun loadFile(activity: Context, file: File?, img: ImageView) {
        Glide.with(activity).load(file).into(img)
    }



    /**
     * 圆角
     */
    fun loadPathWithCircle(context: Context, path: String,  img: ImageView) {
        val myOptions =
            RequestOptions()
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
        Glide.with(context).load(path).apply(myOptions).into(img)
    }

    /**
     * 加载原始尺寸的网络图片
     *
     * @param context           上下文
     * @param url               url
     * @param diskCacheStrategy 缓存
     * @param imageView         图片
     */
    fun loadImageWithCache(
        context: Context?,
        url: String?,
        diskCacheStrategy: DiskCacheStrategy?,
        imageView: ImageView?
    ) {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .diskCacheStrategy(diskCacheStrategy!!)
        Glide.with(context!!)
            .load(MyGlideUrl(url!!))
            .apply(options)
            .into(imageView!!)
    }

    /**
     * 加载Gif图片
     */
    fun loadGifFromUrl(
        context: Context?, url: String?,
        diskCacheStrategy: DiskCacheStrategy?, imageView: ImageView?
    ) {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .diskCacheStrategy(diskCacheStrategy!!)
        Glide.with(context!!)
            .asGif()
            .load(MyGlideUrl(url!!))
            .apply(options)
            .into(imageView!!)
    }

    /**
     * 加载静态图片
     *
     */
    fun loadBitmapFromUrl(
        context: Context?, url: String?,
        diskCacheStrategy: DiskCacheStrategy?, imageView: ImageView?
    ) {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .diskCacheStrategy(diskCacheStrategy!!)
        Glide.with(context!!)
            .asBitmap()
            .load(MyGlideUrl(url!!))
            .apply(options)
            .into(imageView!!)
    }

    /**
     * 加载应用资源
     *
     * @param context           上下文
     * @param diskCacheStrategy 缓存
     * @param imageView         资源文件
     */
    fun loadResource(
        context: Context?, resource: Int,
        diskCacheStrategy: DiskCacheStrategy?, imageView: ImageView?
    ) {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .diskCacheStrategy(diskCacheStrategy!!)
        Glide.with(context!!)
            .load(resource)
            .apply(options)
            .into(imageView!!)
    }

    /**
     * 加载流资源
     *
     * @param context           上下文
     * @param diskCacheStrategy 缓存
     * @param thumbnailSize     缩略图大小
     * @param imageView         资源文件
     */
    fun loadStream(
        context: Context?, image: ByteArray?,
        diskCacheStrategy: DiskCacheStrategy?, thumbnailSize: Float,
        imageView: ImageView?
    ) {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .diskCacheStrategy(diskCacheStrategy!!)
        Glide.with(context!!)
            .load(image)
            .thumbnail(thumbnailSize)
            .apply(options)
            .into(imageView!!)
    }


}