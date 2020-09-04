package com.yk.silence.customnode.widget.adapter.chat

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.CHAT_USER_AVATAR
import com.yk.silence.customnode.common.MSG_TYPE_RECEIVE
import com.yk.silence.customnode.common.MSG_TYPE_SEND
import com.yk.silence.customnode.db.friend.ChatModel
import com.yk.silence.customnode.util.DensityUtils
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_chat_receive_layout.view.*
import kotlinx.android.synthetic.main.item_chat_send_layout.view.*
import java.io.IOException

class ChatTypeAdapter : BaseDelegateMultiAdapter<ChatModel, BaseViewHolder>() {

    private var player: MediaPlayer? = null
    private var mIsPlaying = false
    private var mVoiceAnimation: AnimationDrawable? = null
    private var mPosition = -1

    init {
        setMultiTypeDelegate(ChatTypeDelegate())
    }

    override fun convert(holder: BaseViewHolder, item: ChatModel) {
        mPosition = getItemPosition(item)
        when (holder.itemViewType) {
            MSG_TYPE_SEND -> {
                holder.itemView.run {
                    GlideUtils.loadPathWithCircle(
                        context, item.chat_avatar,
                        img_item_chat_send_avatar
                    )
                    when (item.chat_content_type) {
                        0 -> {//文本
                            img_item_chat_send_photo.visibility = View.GONE
                            fly_item_send_audio.visibility = View.GONE
                            txt_item_chat_send_text.visibility = View.VISIBLE
                            txt_item_chat_send_text.text = item.chat_content
                        }
                        1 -> {//图片
                            txt_item_chat_send_text.visibility = View.GONE
                            fly_item_send_audio.visibility = View.GONE
                            img_item_chat_send_photo.visibility = View.VISIBLE
                            GlideUtils.loadPathWithCircle(
                                context, item.chat_content,
                                img_item_chat_send_photo
                            )
                        }
                        2 -> {//语音
                            txt_item_chat_send_text.visibility = View.GONE
                            img_item_chat_send_photo.visibility = View.GONE
                            fly_item_send_audio.visibility = View.VISIBLE
                            txt_item_send_audio.width = DensityUtils.dp2px(context, 100.0f)
                            fly_item_send_audio.setOnClickListener {
                                img_item_send_audio.setImageResource(R.drawable.voice_receive)
                                mVoiceAnimation = img_item_send_audio.drawable as AnimationDrawable
                                if (mIsPlaying && getItem(mPosition).chat_content == item.chat_content) {
                                    stopPlay()
                                } else {
                                    startPlay(item.chat_content)
                                }
                            }
                        }
                    }
                }
            }
            MSG_TYPE_RECEIVE -> {
                holder.itemView.run {
                    GlideUtils.loadPathWithCircle(
                        context, CHAT_USER_AVATAR,
                        img_item_chat_receive_avatar
                    )
                    when (item.chat_content_type) {
                        0 -> {//文本
                            txt_item_chat_receive_text.text = item.chat_content
                        }
                        1 -> {//图片

                        }
                        2 -> {//语音

                        }
                    }
                }
            }
        }
    }

    /**
     * 播放语音
     */
    private fun startPlay(fileName: String) {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
                mIsPlaying = true
                setOnCompletionListener {
                    if (mVoiceAnimation != null) {
                        if (mVoiceAnimation!!.isRunning) {
                            mVoiceAnimation!!.stop()
                        }
                    }
                }
                if (mVoiceAnimation != null) {
                    if (!mVoiceAnimation!!.isRunning) {
                        mVoiceAnimation!!.start()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    /**
     * 停止播放
     */
    private fun stopPlay() {
        if (player != null) {
            player?.release()
            player = null
            mIsPlaying = false
            if (mVoiceAnimation != null) {
                if (mVoiceAnimation!!.isRunning) {
                    mVoiceAnimation!!.stop()
                }
            }
        }

    }


}