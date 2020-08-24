package com.yk.silence.customnode.viewmodel.chat

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.common.CHAT_USER_AVATAR
import com.yk.silence.customnode.common.LoadMoreStatus
import com.yk.silence.customnode.db.friend.ChatModel
import java.io.File

class ChatViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
    }

    private val mChatRepository by lazy { ChatRepository() }

    /**
     * 聊天消息
     */
    val mChatList = MutableLiveData<MutableList<ChatModel>>()

    //文本消息发送状态
    val mTextMsgSendState = MutableLiveData<Boolean>()

    //发送
    val mChatSendModel = MutableLiveData<ChatModel>()

    //接收
    val mChatReceiveModel = MutableLiveData<ChatModel>()

    //图片消息发送状态
    val mImgMsgSendState = MutableLiveData<Boolean>()

    //语音消息发送状态
    val mAudioMsgSendState = MutableLiveData<Boolean>()

    //刷新
    val mRefreshState = MutableLiveData<Boolean>()

    //重新加载
    val mReLoadState = MutableLiveData<Boolean>()

    //加载更多
    val mLoadMoreState = MutableLiveData<LoadMoreStatus>()

    //空状态
    val mEmptyState = MutableLiveData<Boolean>()

    //页面
    private var mPage = INITIAL_PAGE

    //总页面
    private var mTotal: Int = 0


    /**
     * 刷新消息
     */
    fun refreshData(chatID: String, userID: String) {
        mRefreshState.value = true
        mReLoadState.value = false
        mEmptyState.value = false
        launch(
            block = {
                val currentList = mChatRepository.searchAllMsg(chatID, userID, INITIAL_PAGE)
                val mCurrentList = mChatRepository.searchAllMsg(chatID, userID)
                mTotal = mCurrentList!!.size
                mChatList.value = currentList
                mReLoadState.value = currentList!!.isEmpty()
                mRefreshState.value = false
            },
            error = {
                mRefreshState.value = false
                mReLoadState.value = mPage == INITIAL_PAGE
            }
        )
    }


    /**
     * 加载更多
     */
    fun loadMore(chatID: String, userID: String) {
        mLoadMoreState.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val currentList = mChatRepository.searchAllMsg(chatID, userID, mPage)
                val mCurrentList = mChatList.value ?: mutableListOf()
                mCurrentList.addAll(currentList!!)
                mChatList.value = mCurrentList
                mLoadMoreState.value = if (mPage * 10 >= mTotal) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }

            },
            error = {
                mLoadMoreState.value = LoadMoreStatus.ERROR
            }
        )
    }


    /**
     * 发送文本消息
     */
    fun sendTextMsg(fromID: String, toID: String, content: String) {
        launch(
            block = {
                mChatRepository.sendMsg(fromID, toID, content)
                val model = ChatModel()
                model.chat_avatar = CHAT_USER_AVATAR
                model.user_id = fromID
                model.chat_id = toID
                model.chat_content = content
                model.chat_type = 0
                model.chat_content_type = 0
                mChatRepository.addChat(model)
                mChatSendModel.value = model
            }
        )
    }

    /**
     * 接收文本消息
     */
    fun receiveTextMsg(fromID: String, toID: String, content: String) {
        launch(
            block = {
                val model = ChatModel()
                model.chat_avatar = CHAT_USER_AVATAR
                model.user_id = toID
                model.chat_id = fromID
                model.chat_content = content
                model.chat_type = 1
                model.chat_content_type = 0
                mChatRepository.addChat(model)
                mChatReceiveModel.value = model
            }
        )
    }


    /**
     * 进入聊天
     */
    fun enterChat(userId: String, token: String, hosts: String, appStatus: Int) {
        launch(
            block = {
                mChatRepository.enterChat(userId, token, hosts, appStatus)
            }
        )
    }


}