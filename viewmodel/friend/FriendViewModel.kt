package com.yk.silence.customnode.viewmodel.friend

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.model.FriendBean

class FriendViewModel : BaseViewModel() {

    private val mFriendRepository by lazy { FriendRepository() }

    //好友数据
    var mFriendList = MutableLiveData<MutableList<FriendModel>>()

    //刷新
    val mRefreshState = MutableLiveData<Boolean>()

    //空状态
    val mEmptyState = MutableLiveData<Boolean>()

    //好友状态
    val mFriendState = MutableLiveData<Boolean>()

    //好友数据
    val mFriendModel = MutableLiveData<FriendBean>()

    //删除状态
    val mDeleteState = MutableLiveData<Boolean>()


    /**
     * 刷新
     */
    fun refreshData() {
        mRefreshState.value = true
        mEmptyState.value = false
        launch(
            block = {
                val currentList = mFriendRepository.queryFriend()
                mFriendList.value = currentList
                mRefreshState.value = false
                mEmptyState.value = mFriendList.value?.isEmpty()
            },
            error = {
                mRefreshState.value = false
                mEmptyState.value = true
            }
        )
    }

    /**
     * 添加好友
     */
    fun addFriend(model: FriendModel) {
        mFriendState.value = false
        launch(
            block = {
                mFriendRepository.addFriend(model).apply {
                    mFriendState.value = true
                }
            },
            error = {
                mFriendState.value = false
            }
        )
    }

    /**
     * 删除好友
     */
    fun deleteFriend(model: FriendModel) {
        mFriendState.value = false
        launch(
            block = {
                mFriendRepository.deleteFriend(model).apply {
                    mFriendState.value = true
                }
            },
            error = {
                mFriendState.value = false
            }
        )
    }

    /**
     * 添加好友
     */
    fun addFriend(id: Int) {
        launch(
            block = {
                mFriendModel.value = mFriendRepository.addNetFriend(id)
            }
        )
    }

    /**
     * 删除好友
     */
    fun deleteFriend(id: Int) {
        mDeleteState.value = false
        launch(
            block = {
                mFriendRepository.deleteNetFriend(id).apply {
                    mDeleteState.value = true
                }
            },
            error = {
                mDeleteState.value = false
            }
        )
    }
}