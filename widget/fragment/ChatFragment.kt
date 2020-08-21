package com.yk.silence.customnode.widget.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.databinding.FragmentChatBinding
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.viewmodel.friend.FriendViewModel
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
                val model = FriendModel()
                model.user_id = "10001"
                model.user_avatar = "https://nightlesson.oss-cn-hangzhou.aliyuncs.com/0053f885c4fd91f98f.jpg"
                model.user_name = "木易"
                model.friend_time = System.currentTimeMillis().toString()
                mViewModel.addFriend(model)
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
                mAdapter.setDiffNewData(it)
            })

            mRefreshState.observe(viewLifecycleOwner, Observer {
                mBinding.srlChat.isRefreshing = it
            })

            mEmptyState.observe(viewLifecycleOwner, Observer {
                mBinding.emptyView.isVisible = it
            })

            mFriendState.observe(viewLifecycleOwner, Observer {
                mAdapter.notifyDataSetChanged()
            })

        }

    }
}