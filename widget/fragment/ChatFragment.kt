package com.yk.silence.customnode.widget.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.CONV_TITLE
import com.yk.silence.customnode.common.TARGET_ID
import com.yk.silence.customnode.databinding.FragmentChatBinding
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.viewmodel.friend.FriendViewModel
import com.yk.silence.customnode.widget.activity.ChatActivity
import com.yk.silence.customnode.widget.adapter.FriendAdapter
import com.yk.silence.toolbar.CustomTitleBar

class ChatFragment : BaseVMFragment<FriendViewModel, FragmentChatBinding>() {

    private lateinit var mAdapter: FriendAdapter

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun getFragmentID() = R.layout.fragment_chat

    override fun viewModelClass() = FriendViewModel::class.java

    override fun initBinding(mBinding: FragmentChatBinding) {
        super.initBinding(mBinding)
        mBinding.titleChat.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {

            }

            override fun onRightClick() {
                //mViewModel.addConversation("CHAT_USER2")
            }
        })
        mBinding.srlChat.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener { mViewModel.refreshData() }
        }
        mAdapter = FriendAdapter().apply {
            mOnItemClickListener = {
                val mUserInfo: UserInfo = mAdapter.data[it].targetInfo as UserInfo
                ActivityManager.start(
                    ChatActivity::class.java, mapOf(
                        TARGET_ID to mUserInfo.userName, CONV_TITLE to mAdapter.data[it].title
                    )
                )
            }
        }
        mBinding.rlvChat.adapter = mAdapter
        // 必须设置Diff Callback
        mAdapter.setDiffCallback(FriendAdapter.CustomDiffCallBack())
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.refreshData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            mConversationList.observe(viewLifecycleOwner, Observer {
                mAdapter.setDiffNewData(it as MutableList<Conversation>?)
            })

            mRefreshState.observe(viewLifecycleOwner, Observer {
                mBinding.srlChat.isRefreshing = it
            })

            mEmptyState.observe(viewLifecycleOwner, Observer {
                mBinding.emptyView.isVisible = it
            })
            mConversation.observe(viewLifecycleOwner, Observer {
                mAdapter.addNewConversation(it)
            })
            mAddConversationState.observe(viewLifecycleOwner, Observer {
                if (it) {
                    ToastUtil.getInstance().shortToast(requireActivity(), "添加成功")
                    mViewModel.refreshData()
                } else ToastUtil.getInstance().shortToast(requireActivity(), "添加失败")
            })
        }

    }
}