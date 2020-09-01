package com.yk.silence.customnode.widget.activity

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.databinding.ActivityChatBinding
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.im.bean.SingleMessage
import com.yk.silence.customnode.impl.OnCameraResultListener
import com.yk.silence.customnode.impl.OnOssResultListener
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.service.ChatService
import com.yk.silence.customnode.util.CameraUtil
import com.yk.silence.customnode.util.EventUtil
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.util.oss.OSSHelper
import com.yk.silence.customnode.viewmodel.chat.ChatViewModel
import com.yk.silence.customnode.widget.adapter.chat.ChatTypeAdapter
import com.yk.silence.toolbar.CustomTitleBar
import org.greenrobot.eventbus.Subscribe

class ChatActivity : BaseVMActivity<ChatViewModel, ActivityChatBinding>(),
    CustomTitleBar.TitleClickListener {

    private var mFriendMode: FriendModel? = null
    private lateinit var mAdapter: ChatTypeAdapter


    override fun getLayoutID() = R.layout.activity_chat

    override fun viewModelClass() = ChatViewModel::class.java

    override fun initBinding(binding: ActivityChatBinding) {
        super.initBinding(binding)
        EventUtil.register(this)
        binding.titleChatContent.setTitleClickListener(this)
        mFriendMode = intent?.getParcelableExtra(PARAM_ARTICLE) ?: return
        ActivityManager.startService(ChatService::class.java)
        binding.userName = mFriendMode!!.user_name
        mBinding.srlChat.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener {
                mViewModel.refreshData(mFriendMode!!.user_id!!)
            }
        }
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rlvListChat.layoutManager = linearLayoutManager
        mAdapter = ChatTypeAdapter().apply {

        }
        binding.rlvListChat.adapter = mAdapter
        binding.btnChatSend.setOnClickListener {
            mFriendMode!!.user_id?.let { it1 ->
                mViewModel.sendTextMsg(
                    it1, binding.edtChatContent.text.toString().trim()
                )
            }
            binding.edtChatContent.setText("")
        }
        binding.imgChatMore.setOnClickListener {
            if (binding.lytBottomItem.visibility == View.GONE) {
                binding.lytBottomItem.visibility = View.VISIBLE
            } else {
                binding.lytBottomItem.visibility = View.GONE
            }
        }
        binding.imgChatCamera.setOnClickListener {
            CameraUtil.openCamera(this)
        }
        binding.imgChatPhoto.setOnClickListener {
            CameraUtil.openAlbum(this)
        }

    }


    override fun initData() {
        super.initData()
        mViewModel.refreshData(mFriendMode?.user_id!!)
        mViewModel.enterChat(HOST, 1)
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
                mAdapter.setList(it)
                //refresh()
            })

            mChatSendModel.observe(this@ChatActivity, Observer {
                mAdapter.addData(mAdapter.data.size, it)
                refresh()
            })

            mChatReceiveModel.observe(this@ChatActivity, Observer {
                mAdapter.addData(mAdapter.data.size, it)
                refresh()
            })

            mRefreshState.observe(this@ChatActivity, Observer {
                mBinding.srlChat.isRefreshing = it
            })

            mReLoadState.observe(this@ChatActivity, Observer {
                mBinding.reloadView.isVisible = it
            })

            mEmptyState.observe(this@ChatActivity, Observer {
                mBinding.emptyView.isVisible = it
            })

            mLoadMoreState.observe(this@ChatActivity, Observer {
                when (it) {
                    LoadMoreStatus.LOADING -> {
                        mBinding.srlChat.isRefreshing = true
                    }
                    LoadMoreStatus.COMPLETED -> {
                        mBinding.srlChat.isRefreshing = false
                    }
                    LoadMoreStatus.ERROR -> {
                        mBinding.srlChat.isRefreshing = false
                    }
                    LoadMoreStatus.END -> {
                        mBinding.srlChat.isRefreshing = false
                    }
                    else -> return@Observer
                }
            })

        }
    }

    @Subscribe
    fun onEvent(event: Any) {
        val mData = event as EventModel
        val message = event.result as SingleMessage
        if (mData.code == MSG_CODE_ADD_MSG) {
            mViewModel.receiveTextMsg(message.toId, message.content)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CameraUtil.onActivityResult(requestCode, resultCode, data, object : OnCameraResultListener {
            override fun onCameraResult(path: String?) {
                if (path != null) {
                    uploadFile(path)
                }

            }

            override fun onAlbumResult(path: String?) {
                if (path != null) {
                    uploadFile(path)
                }
            }

        })
    }


    override fun onDestroy() {
        EventUtil.unRegister(this)
        ActivityManager.stopService(ChatService::class.java)
        super.onDestroy()
    }

    /**
     * 刷新界面
     */
    private fun refresh() {
        val linearLayoutManager = LinearLayoutManager(this@ChatActivity)
        linearLayoutManager.stackFromEnd = true
        linearLayoutManager.scrollToPositionWithOffset(
            mAdapter.itemCount - 1,
            Integer.MIN_VALUE
        )
        mBinding.rlvListChat.layoutManager = linearLayoutManager
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 上传文件
     */
    private fun uploadFile(path: String) {
        val mName = System.currentTimeMillis().toString() + ".jpg"
        CThreadPoolExecutor.runInBackground(Runnable {
            OSSHelper.updateFile(
                this@ChatActivity,
                mName,
                path,
                object : OnOssResultListener {
                    override fun onResultSuccess() {
                        val mPhotoUrl =
                            BASE_OSS_URL + mName
                        Log.e("TAG", "========" + mPhotoUrl)
                        mFriendMode!!.user_id?.let { it ->
                            mViewModel.sendImgMsg(it, mPhotoUrl)
                        }
                        ToastUtil.getInstance().shortToast(this@ChatActivity, "发送成功")

                    }

                    override fun onResultFailed() {
                        Log.e("TAG", "onResultFailed")
                    }

                })
        })
    }
}