package com.yk.silence.customnode.widget.adapter.chat

import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yk.silence.customnode.common.CHAT_USER_AVATAR
import com.yk.silence.customnode.common.MSG_TYPE_RECEIVE
import com.yk.silence.customnode.common.MSG_TYPE_SEND
import com.yk.silence.customnode.db.friend.ChatModel
import com.yk.silence.customnode.util.glide.GlideUtils
import kotlinx.android.synthetic.main.item_chat_receive_layout.view.*
import kotlinx.android.synthetic.main.item_chat_send_layout.view.*

class ChatTypeAdapter : BaseDelegateMultiAdapter<ChatModel, BaseViewHolder>() {

    init {
        setMultiTypeDelegate(ChatTypeDelegate())
    }

    override fun convert(holder: BaseViewHolder, item: ChatModel) {
        when (holder.itemViewType) {
            MSG_TYPE_SEND -> {
                holder.itemView.run {
                    GlideUtils.loadPathWithCircle(
                        context, item.chat_avatar,
                        img_item_chat_send_avatar
                    )
                    when (item.chat_content_type) {
                        0 -> {//文本
                            txt_item_chat_send_text.text = item.chat_content
                        }
                        1 -> {//图片

                        }
                        2 -> {//语音

                        }
                    }
                }
            }
                MSG_TYPE_RECEIVE->{
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
    }