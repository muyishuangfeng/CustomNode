package com.yk.silence.customnode.widget.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.MSG_CODE_ADD_FRIEND
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.databinding.FragmentChatBinding
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.impl.OnCommonDialogListener
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.ui.dialog.DialogFragmentHelper
import com.yk.silence.customnode.util.EventUtil
import com.yk.silence.customnode.viewmodel.friend.FriendViewModel
import com.yk.silence.customnode.widget.activity.AddFriendActivity
import com.yk.silence.customnode.widget.activity.ChatActivity
import com.yk.silence.customnode.widget.activity.SearchActivity
import com.yk.silence.customnode.widget.adapter.FriendAdapter
import com.yk.silence.toolbar.CustomTitleBar
import org.greenrobot.eventbus.Subscribe

class ChatFragment : BaseVMFragment<FriendViewModel, FragmentChatBinding>() {

    private lateinit var mAdapter: FriendAdapter
    private lateinit var mList: MutableList<FriendModel>
    private lateinit var mFriend: FriendModel

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventUtil.register(this)
    }

    override fun getFragmentID() = R.layout.fragment_chat

    override fun viewModelClass() = FriendViewModel::class.java

    override fun initBinding(mBinding: FragmentChatBinding) {
        super.initBinding(mBinding)
        mBinding.titleChat.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {

            }

            override fun onRightClick() {
                ActivityManager.start(SearchActivity::class.java)
            }
        })
        mBinding.srlChat.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener { mViewModel.refreshData() }
        }
        mAdapter = FriendAdapter().apply {
            mOnItemClickListener = {
                ActivityManager.start(
                    ChatActivity::class.java, mapOf(
                        PARAM_ARTICLE to mList[it]
                    )
                )
            }
            mOnItemLongClickListener = {
                deleteDialog(it)

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
            mFriendList.observe(viewLifecycleOwner, Observer {
                mList = it
                mAdapter.setDiffNewData(it)
            })

            mRefreshState.observe(viewLifecycleOwner, Observer {
                mBinding.srlChat.isRefreshing = it
            })

            mEmptyState.observe(viewLifecycleOwner, Observer {
                mBinding.emptyView.isVisible = it
            })

            mFriendState.observe(viewLifecycleOwner, Observer {
                mViewModel.refreshData()
            })
            mDeleteState.observe(viewLifecycleOwner, Observer {
                if (it)
                    mViewModel.deleteFriend(mFriend)
            })


        }
    }

    @Subscribe
    fun onEvent(event: Any) {
        val mData = event as EventModel
        if (mData.code == MSG_CODE_ADD_FRIEND) {
            mViewModel.refreshData()
        }
    }

    override fun onDestroy() {
        EventUtil.unRegister(this)
        super.onDestroy()
    }


    /**
     * 删除好友
     */
    private fun deleteDialog(position: Int) {
        DialogFragmentHelper.commonDialog(
            childFragmentManager,
            getString(R.string.text_delete_fridend),
            getString(R.string.text_sure_delete_fridend),
            2,
            object : OnCommonDialogListener {
                override fun onResult() {
                    mFriend = mList[position]
                    mViewModel.deleteFriend(mList[position].id)
                }

                override fun onCancel() {
                }

            })
    }
}