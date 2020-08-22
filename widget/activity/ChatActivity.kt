package com.yk.silence.customnode.widget.activity

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.databinding.ActivityChatBinding
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.service.MyService
import com.yk.silence.customnode.viewmodel.chat.ChatViewModel
import com.yk.silence.customnode.widget.adapter.ChatAdapter
import com.yk.silence.toolbar.CustomTitleBar

class ChatActivity : BaseVMActivity<ChatViewModel, ActivityChatBinding>(),
    CustomTitleBar.TitleClickListener {

    private lateinit var mFriendMode: FriendModel
    private lateinit var mAdapter: ChatAdapter


    override fun getLayoutID() = R.layout.activity_chat

    override fun viewModelClass() = ChatViewModel::class.java

    override fun initBinding(binding: ActivityChatBinding) {
        super.initBinding(binding)
        binding.titleChatContent.setTitleClickListener(this)
        mFriendMode = intent?.getParcelableExtra(PARAM_ARTICLE) ?: return
        //ActivityManager.startService(MyService::class.java)
        binding.userName = mFriendMode.user_name
        mBinding.srlChat.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener { mViewModel.refreshData(mFriendMode.user_id!!) }
        }

        binding.rlvListChat.layoutManager = LinearLayoutManager(this)
        mAdapter = ChatAdapter(this).apply {

        }
        binding.rlvListChat.adapter = mAdapter
        binding.btnChatSend.setOnClickListener {
            mFriendMode.user_id?.let { it1 ->
                mViewModel.sendTextMsg(
                    CHAT_USER_ID,
                    it1, binding.edtChatContent.text.toString()
                )
            }
        }
    }


    override fun initData() {
        super.initData()
        mViewModel.enterChat(CHAT_USER_ID, CHAT_USER_TOKEN, HOST, 1)
        mViewModel.refreshData(mFriendMode.user_id!!)
    }


    override fun onLeftClick() {
        ActivityManager.finish(ChatActivity::class.java)
    }

    override fun onRightClick() {
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mChatList.observe(this@ChatActivity, Observer {
                mAdapter.mList = it
            })

            mTextMsgSendState.observe(this@ChatActivity, Observer {
                mAdapter.notifyDataSetChanged()

            })
            mRefreshState.observe(this@ChatActivity, Observer {
                mBinding.srlChat.isRefreshing = it
            })

            mReLoadState.observe(this@ChatActivity, Observer {
                mViewModel.refreshData(mFriendMode.user_id!!)
            })
            mEmptyState.observe(this@ChatActivity, Observer {
                mBinding.emptyView.isVisible = it
            })

        }
    }

    override fun onDestroy() {
        //ActivityManager.stopService(MyService::class.java)
        super.onDestroy()
    }


}