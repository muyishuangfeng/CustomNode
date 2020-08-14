package com.yk.silence.customnode.widget.adapter

import cn.jpush.im.android.api.model.Conversation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yk.silence.customnode.R

class ChatListAdapter(layoutID: Int = R.layout.item_chat_list_layout) :
    BaseQuickAdapter<Conversation, BaseViewHolder>(layoutID) {

    override fun convert(helper: BaseViewHolder, item: Conversation?) {
    }
}