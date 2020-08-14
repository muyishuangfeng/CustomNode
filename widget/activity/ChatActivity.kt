package com.yk.silence.customnode.widget.activity

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.CONV_TITLE
import com.yk.silence.customnode.common.TARGET_ID
import com.yk.silence.customnode.databinding.ActivityChatBinding
import com.yk.silence.customnode.viewmodel.chat.ChatViewModel
import com.yk.silence.toolbar.CustomTitleBar

class ChatActivity : BaseVMActivity<ChatViewModel, ActivityChatBinding>(),
    CustomTitleBar.TitleClickListener {

    private var targetID: String = ""

    override fun getLayoutID() = R.layout.activity_chat

    override fun viewModelClass() = ChatViewModel::class.java

    override fun initBinding(binding: ActivityChatBinding) {
        super.initBinding(binding)
        binding.titleChatContent.setTitleClickListener(this)
        targetID = intent?.getStringExtra(TARGET_ID) ?: return
        val title = intent?.getStringExtra(CONV_TITLE) ?: return
        binding.userName = title

    }


    override fun initData() {
        super.initData()
        mViewModel.enterChat(targetID)
    }

    override fun onDestroy() {
        mViewModel.exitChat()
        super.onDestroy()
    }

    override fun onLeftClick() {
        ActivityManager.finish(ChatActivity::class.java)
    }

    override fun onRightClick() {
    }
}