@file:Suppress("NAME_SHADOWING")

package com.yk.silence.customnode.viewmodel.chat

import android.content.Context
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.DownloadCompletionCallback
import cn.jpush.im.android.api.content.ImageContent
import cn.jpush.im.android.api.content.TextContent
import cn.jpush.im.android.api.content.VoiceContent
import cn.jpush.im.android.api.enums.ConversationType
import cn.jpush.im.android.api.enums.MessageDirect
import cn.jpush.im.android.api.enums.MessageStatus
import cn.jpush.im.android.api.model.Message
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.JPUSH_KEY
import com.yk.silence.customnode.common.JPUSH_TARGET_APP_KEY
import com.yk.silence.customnode.util.glide.GlideUtils
import com.yk.silence.customnode.widget.adapter.ChatAdapter
import kotlinx.android.synthetic.main.item_chat_receive_layout.view.*
import kotlinx.android.synthetic.main.item_chat_send_layout.view.*
import java.io.File

object ChatHelper {



    /**
     * 发送文本
     */
    fun sendTextMsg(msg: Message, holder: ChatAdapter.ChatSendHolder, position: Int) {
        val content = (msg.content as TextContent).text

        holder.itemView.run {
            txt_item_chat_send_text.text = content
            txt_item_chat_send_text.tag = position
            // 检查发送状态，发送方有重发机制
            if (msg.direct == MessageDirect.send) {
                when (msg.status) {
                    MessageStatus.created -> {
                        pgb_item_text_send_status.visibility = View.VISIBLE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.GONE
                    }
                    MessageStatus.send_success -> {
                        pgb_item_text_send_status.visibility = View.GONE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.VISIBLE
                    }
                    MessageStatus.send_fail -> {
                        pgb_item_text_send_status.visibility = View.GONE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.VISIBLE

                    }
                    MessageStatus.send_going -> {
                        pgb_item_text_send_status.visibility = View.VISIBLE
                        txt_item_text_send_read.visibility = View.GONE
                        txt_item_chat_send_text.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }

        }


    }

    /**
     * 接收文本
     */
    fun receiveTextMsg(msg: Message, holder: ChatAdapter.ChatReceiveHolder, position: Int) {
        val content = (msg.content as TextContent).text

        holder.itemView.run {
            txt_item_chat_receive_text.text = content
            txt_item_chat_receive_text.tag = position
            // 检查发送状态，发送方有重发机制
            if (msg.direct == MessageDirect.receive) {
                when (msg.status) {
                    MessageStatus.created -> {
                        txt_item_text_receive_read.visibility = View.GONE
                        txt_item_chat_receive_text.visibility = View.GONE
                    }
                    MessageStatus.send_success -> {
                        txt_item_text_receive_read.visibility = View.GONE
                        txt_item_chat_receive_text.visibility = View.VISIBLE
                    }
                    MessageStatus.send_fail -> {
                        txt_item_text_receive_read.visibility = View.GONE
                        txt_item_chat_receive_text.visibility = View.VISIBLE

                    }
                    MessageStatus.send_going -> {
                        txt_item_text_receive_read.visibility = View.GONE
                        txt_item_chat_receive_text.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }

        }


    }

    /**
     * 发送图片
     */
    fun sendImgMsg(
        context: Context,
        msg: Message,
        holder: ChatAdapter.ChatSendHolder
    ) {
        val imgContent = msg.content as ImageContent
        val jiguang = imgContent.getStringExtra("jiguang")
        // 先拿本地缩略图
        val path = imgContent.localThumbnailPath
        holder.itemView.run {
            if (path == null) {
                //从服务器上拿缩略图
                imgContent.downloadThumbnailImage(msg, object : DownloadCompletionCallback() {
                    override fun onComplete(
                        status: Int,
                        desc: String,
                        file: File
                    ) {
                        if (status == 0) {
                            val imageView: ImageView =
                                setPictureScale(jiguang, file.path, img_item_chat_send_photo)
                            GlideUtils.loadFile(context, file, imageView)
                        }
                    }
                })
            } else {
                val imageView: ImageView =
                    setPictureScale(jiguang, path, img_item_chat_send_photo)
                GlideUtils.loadPath(context, path, imageView)
            }
            // 检查发送状态，发送方有重发机制
            if (msg.direct == MessageDirect.send) {
                when (msg.status) {
                    MessageStatus.created -> {
                        pgb_item_img_send_status.visibility = View.VISIBLE
                        txt_item_img_send_read.visibility = View.GONE
                        img_item_chat_send_photo.visibility = View.GONE
                    }
                    MessageStatus.send_success -> {
                        pgb_item_img_send_status.visibility = View.GONE
                        txt_item_img_send_read.visibility = View.GONE
                        img_item_chat_send_photo.visibility = View.VISIBLE
                    }
                    MessageStatus.send_fail -> {
                        pgb_item_img_send_status.visibility = View.GONE
                        txt_item_img_send_read.visibility = View.GONE
                        img_item_chat_send_photo.visibility = View.VISIBLE

                    }
                    MessageStatus.send_going -> {
                        pgb_item_img_send_status.visibility = View.VISIBLE
                        txt_item_img_send_read.visibility = View.GONE
                        img_item_chat_send_photo.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }

        }


    }

    /**
     * 接收图片
     */
    fun receiveImgMsg(
        msg: Message,
        holder: ChatAdapter.ChatReceiveHolder
    ) {
        val imgContent = msg.content as ImageContent
        // 先拿本地缩略图
        val path = imgContent.localThumbnailPath
        holder.itemView.run {
            if (msg.direct == MessageDirect.receive) {
                when (msg.status) {
                    MessageStatus.created -> {
                        txt_item_img_receive_read.visibility = View.GONE
                        img_item_chat_receive_photo.visibility = View.GONE
                    }
                    MessageStatus.send_success -> {
                        txt_item_img_receive_read.visibility = View.VISIBLE
                        img_item_chat_receive_photo.visibility = View.VISIBLE
                    }
                    MessageStatus.send_fail -> {
                        txt_item_img_receive_read.visibility = View.VISIBLE
                        img_item_chat_receive_photo.visibility = View.VISIBLE

                    }
                    MessageStatus.send_going -> {
                        txt_item_img_receive_read.visibility = View.GONE
                        img_item_chat_receive_photo.visibility = View.GONE
                    }
                    else -> {
                    }
                }
            }
        }


    }

    /**
     * 设置图片最小宽高
     *
     * @param path      图片路径
     * @param imageView 显示图片的View
     */
    private fun setPictureScale(
        extra: String,
        path: String,
        imageView: ImageView
    ): ImageView {
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, opts)


        //计算图片缩放比例
        val imageWidth = opts.outWidth.toDouble()
        val imageHeight = opts.outHeight.toDouble()
        return setDensity(extra, imageWidth, imageHeight, imageView)
    }

    private fun setDensity(
        extra: String?,
        imageWidth: Double,
        imageHeight: Double,
        imageView: ImageView
    ): ImageView {
        var imageWidth = imageWidth
        var imageHeight = imageHeight
        if (extra != null) {
            imageWidth = 200.0
            imageHeight = 200.0
        } else {
            if (imageWidth > 350) {
                imageWidth = 550.0
                imageHeight = 250.0
            } else if (imageHeight > 450) {
                imageWidth = 300.0
                imageHeight = 450.0
            } else if (imageWidth < 50 && imageWidth > 20 || imageHeight < 50 && imageHeight > 20) {
                imageWidth = 200.0
                imageHeight = 300.0
            } else if (imageWidth < 20 || imageHeight < 20) {
                imageWidth = 100.0
                imageHeight = 150.0
            } else {
                imageWidth = 300.0
                imageHeight = 450.0
            }
        }
        val params = imageView.layoutParams
        params.width = imageWidth.toInt()
        params.height = imageHeight.toInt()
        imageView.layoutParams = params
        return imageView
    }

    /**
     * 发送语音
     */
    fun sendVoice(
        context: Context,
        msg: Message,
        holder: ChatAdapter.ChatSendHolder,
        position: Int
    ) {
        val content = msg.content as VoiceContent
        val msgDirect = msg.direct
        val length = content.duration
        val lengthStr: String = "" + length + context.getString(R.string.text_symbol_second)

        holder.itemView.run {
            txt_item_send_audio.text = lengthStr
            //控制语音长度显示，长度增幅随语音长度逐渐缩小
            val width = (-0.04 * length * length + 4.526 * length + 75.214).toInt()
            txt_item_send_audio.width = (width * 1) as Int
            //要设置这个position
            txt_item_send_audio.tag = position
            if (msgDirect == MessageDirect.send) {
                img_item_send_audio.setImageResource(R.drawable.ic_user)
                when (msg.status) {
                    MessageStatus.created -> {
                        pgb_item_audio_send_status.visibility = View.VISIBLE
                        txt_item_audio_send_read.visibility = View.GONE
                        fly_item_send_audio.visibility = View.GONE
                    }
                    MessageStatus.send_success -> {
                        pgb_item_audio_send_status.visibility = View.GONE
                        txt_item_audio_send_read.visibility = View.VISIBLE
                        fly_item_send_audio.visibility = View.VISIBLE
                    }
                    MessageStatus.send_fail -> {
                        pgb_item_audio_send_status.visibility = View.GONE
                        txt_item_audio_send_read.visibility = View.VISIBLE
                        fly_item_send_audio.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }
        }
    }

    /**
     * 接收语音
     */
    fun receiveVoice(
        context: Context,
        msg: Message,
        holder: ChatAdapter.ChatReceiveHolder,
        position: Int
    ) {
        val content = msg.content as VoiceContent
        val msgDirect = msg.direct
        val length = content.duration
        val lengthStr: String = "" + length + context.getString(R.string.text_symbol_second)

        holder.itemView.run {
            txt_item_receive_audio.text = lengthStr
            //控制语音长度显示，长度增幅随语音长度逐渐缩小
            val width = (-0.04 * length * length + 4.526 * length + 75.214).toInt()
            txt_item_receive_audio.width = (width * 1) as Int
            //要设置这个position
            txt_item_receive_audio.tag = position
            if (msgDirect == MessageDirect.receive) {
                when (msg.status) {
                    MessageStatus.receive_success -> {
                        // 收到语音，设置未读
//                        if (msg.content.getBooleanExtra("isRead") == null
//                            || !msg.content.getBooleanExtra("isRead")
//                        ) {
//                            mConv.updateMessageExtra(msg, "isRead", false)
//                            holder.readStatus.setVisibility(View.VISIBLE)
//                            if (mIndexList.size > 0) {
//                                if (!mIndexList.contains(position)) {
//                                    addToListAndSort(position)
//                                }
//                            } else {
//                                addToListAndSort(position)
//                            }
//                            if (nextPlayPosition == position && autoPlay) {
//                                playVoice(position, holder, false)
//                            }
//                        } else if (msg.content.getBooleanExtra("isRead")) {
//                            holder.readStatus.setVisibility(View.GONE)
//                        }
                    }
                    MessageStatus.receive_fail -> {
                        // 接收失败，从服务器上下载
                        content.downloadVoiceFile(msg,
                            object : DownloadCompletionCallback() {
                                override fun onComplete(
                                    status: Int,
                                    desc: String,
                                    file: File
                                ) {
                                }
                            })
                    }
                    MessageStatus.receive_going -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }


}