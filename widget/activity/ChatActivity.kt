package com.yk.silence.customnode.widget.activity

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jpush.im.android.api.enums.ConversationType
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.android.api.model.UserInfo
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.CONV_TITLE
import com.yk.silence.customnode.common.TARGET_ID
import com.yk.silence.customnode.databinding.ActivityChatBinding
import com.yk.silence.customnode.util.EventBus
import com.yk.silence.customnode.viewmodel.chat.ChatViewModel
import com.yk.silence.customnode.widget.adapter.ChatAdapter
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_chat.*
import org.greenrobot.eventbus.Subscribe

class ChatActivity : BaseVMActivity<ChatViewModel, ActivityChatBinding>(),
    CustomTitleBar.TitleClickListener {

    private var targetID: String = ""
    private lateinit var mAdapter: ChatAdapter

    override fun getLayoutID() = R.layout.activity_chat

    override fun viewModelClass() = ChatViewModel::class.java

    override fun initBinding(binding: ActivityChatBinding) {
        super.initBinding(binding)
        org.greenrobot.eventbus.EventBus.getDefault().register(this)
        binding.titleChatContent.setTitleClickListener(this)
        targetID = intent?.getStringExtra(TARGET_ID) ?: return
        val title = intent?.getStringExtra(CONV_TITLE) ?: return
        binding.userName = title
        binding.rlvListChat.layoutManager = LinearLayoutManager(this)
        mAdapter = ChatAdapter().apply {

        }
        binding.rlvListChat.adapter = mAdapter

        btn_chat_send.setOnClickListener {
            mViewModel.sendTextMsg(targetID, binding.edtChatContent.text.toString())
        }
    }


    override fun initData() {
        super.initData()
        mViewModel.enterChat(targetID)
        mViewModel.getMsgList(targetID)
    }

    override fun onDestroy() {
        mViewModel.exitChat()
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onLeftClick() {
        ActivityManager.finish(ChatActivity::class.java)
    }

    override fun onRightClick() {
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mTextMsgSendState.observe(this@ChatActivity, Observer {
                //mAdapter.addSendData(0,)
            })

            mMsgList.observe(this@ChatActivity, Observer {
                mAdapter.setList(it)
            })
        }
    }

    @Subscribe
    fun onEvent(event: MessageEvent) {
        val message = event.message
        runOnUiThread {
            if (message.targetType == ConversationType.single) {
                val userInfo = message.targetInfo as UserInfo
                val targetId = userInfo.userName
                val appKey = userInfo.appKey
                mAdapter.addSendData(message)
            }
        }
    }
}