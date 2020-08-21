package com.yk.silence.customnode.widget.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.FragmentChatBinding
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.viewmodel.friend.FriendViewModel
import com.yk.silence.customnode.widget.activity.AddFriendActivity
import com.yk.silence.customnode.widget.adapter.FriendAdapter
import com.yk.silence.toolbar.CustomTitleBar

class ChatFragment : BaseVMFragment<FriendViewModel, FragmentChatBinding>() {

    private lateinit var mAdapter: FriendAdapter
    private lateinit var mList: MutableList<FriendModel>

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
                ActivityManager.start(AddFriendActivity::class.java)
            }
        })
        mBinding.srlChat.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener { mViewModel.refreshData() }
        }
        mAdapter = FriendAdapter().apply {
            mOnItemClickListener = {

            }
            mOnItemLongClickListener = {
                mViewModel.deleteFriend(mList[it])
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

        }

    }
}