package com.yk.silence.customnode.widget.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.CONV_TITLE
import com.yk.silence.customnode.common.HOST
import com.yk.silence.customnode.common.TARGET_ID
import com.yk.silence.customnode.databinding.ActivityChatBinding
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.im.bean.SingleMessage
import com.yk.silence.customnode.im.event.CEventCenter
import com.yk.silence.customnode.im.event.Events.CHAT_SINGLE_MESSAGE
import com.yk.silence.customnode.im.event.I_CEventListener
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.viewmodel.chat.ChatViewModel
import com.yk.silence.toolbar.CustomTitleBar

class ChatActivity : BaseVMActivity<ChatViewModel, ActivityChatBinding>(),
    CustomTitleBar.TitleClickListener, I_CEventListener {

    private var targetID: String = ""
    //private lateinit var mAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CEventCenter.registerEventListener(this, CHAT_SINGLE_MESSAGE)
    }

    override fun getLayoutID() = R.layout.activity_chat

    override fun viewModelClass() = ChatViewModel::class.java

    override fun initBinding(binding: ActivityChatBinding) {
        super.initBinding(binding)
        binding.titleChatContent.setTitleClickListener(this)
//        targetID = intent?.getStringExtra(TARGET_ID) ?: return
//        val title = intent?.getStringExtra(CONV_TITLE) ?: return
//        binding.userName = title
       // binding.rlvListChat.layoutManager = LinearLayoutManager(this)
//        mAdapter = ChatAdapter().apply {
//
//        }
        // binding.rlvListChat.adapter = mAdapter
        binding.userName="10002"
        binding.btnChatSend.setOnClickListener {
            mViewModel.sendTextMsg("10002", "10001", binding.edtChatContent.text.toString())
        }
    }


    override fun initData() {
        super.initData()
        mViewModel.enterChat("10002", "token_10002", HOST, 1)

        //mViewModel.getMsgList(targetID)
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


        }
    }

    override fun onCEvent(topic: String?, msgCode: Int, resultCode: Int, obj: Any?) {
        if (topic == CHAT_SINGLE_MESSAGE) {
            val message = obj as SingleMessage
            CThreadPoolExecutor.runOnMainThread(Runnable {
                ToastUtil.getInstance()
                    .shortToast(this, "收到来自：" + message.fromId + "的消息====" + message.content)
            })

        }

    }
    override fun onDestroy() {
        super.onDestroy()
        CEventCenter.unregisterEventListener(this,CHAT_SINGLE_MESSAGE)
    }

}