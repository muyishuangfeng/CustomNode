package com.yk.silence.customnode.viewmodel.friend

import androidx.lifecycle.MutableLiveData
import cn.jpush.im.android.api.model.Conversation
import com.yk.silence.customnode.base.vm.BaseViewModel

class FriendViewModel : BaseViewModel() {

    private val mFriendRepository by lazy { FriendRepository() }

    //会话列表
    val mConversationList = MutableLiveData<List<Conversation>>()

    //会话
    val mConversation = MutableLiveData<Conversation>()

    //刷新
    val mRefreshState = MutableLiveData<Boolean>()

    //空状态
    val mEmptyState = MutableLiveData<Boolean>()

    //添加会话状态
    val mAddConversationState = MutableLiveData<Boolean>()

    /**
     * 刷新
     */
    fun refreshData() {
        mRefreshState.value = true
        mEmptyState.value = false
        launch(
            block = {
                val mCurrentList = mFriendRepository.getData()
                mConversationList.value = mCurrentList?.toMutableList()
                mRefreshState.value = false
                mEmptyState.value = mConversationList.value?.isEmpty()
            },
            error = {
                mRefreshState.value = false
                mEmptyState.value = true
            }
        )
    }

    /**
     * 添加会话
     */
    fun addConversation(userName: String) {
        mAddConversationState.value = false
        launch(
            block = {
                val currentData = mFriendRepository.addConversation(userName)
                mConversation.value = currentData
                mAddConversationState.value = mConversation.value != null
            },
            error = {
                mAddConversationState.value = false
            }
        )
    }
}