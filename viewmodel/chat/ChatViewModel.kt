package com.yk.silence.customnode.viewmodel.chat

import androidx.lifecycle.MutableLiveData
import cn.jpush.im.android.api.enums.MessageStatus
import cn.jpush.im.android.api.model.Message
import com.yk.silence.customnode.base.vm.BaseViewModel
import java.io.File

class ChatViewModel : BaseViewModel() {

    private val mChatRepository by lazy { ChatRepository() }

    //消息
    val mMessage = MutableLiveData<Message>()

    //消息列表
    val mMsgList = MutableLiveData<MutableList<Message>>()

    //文本消息发送状态
    val mTextMsgSendState = MutableLiveData<Boolean>()

    //图片消息发送状态
    val mImgMsgSendState = MutableLiveData<Boolean>()

    //语音消息发送状态
    val mAudioMsgSendState = MutableLiveData<Boolean>()

    /**
     * 获取消息列表
     */
    fun getMsgList(mTargetId: String) {
        launch(
            block = {
                val currentList = mChatRepository.getMessageList(mTargetId)
                mMsgList.value = currentList
            }
        )
    }

    /**
     * 发送文本消息
     */
    fun sendTextMsg(userName: String, content: String) {
        mTextMsgSendState.value = false
        launch(
            block = {
                val currentMsg = mChatRepository.createTextMsg(userName, content)
                mMessage.value = currentMsg
                mMessage.value.let {
                    mTextMsgSendState.value = it?.status == MessageStatus.send_success
                }
            },
            error = {
                mTextMsgSendState.value = false
            }
        )
    }

    /**
     * 发送图片消息
     */
    fun sendImgMsg(userName: String, file: File) {
        mImgMsgSendState.value = false
        launch(
            block = {
                val currentMsg = mChatRepository.createImageMsg(userName, file)
                mMessage.value = currentMsg
                mMessage.value.let {
                    mImgMsgSendState.value = it?.status == MessageStatus.send_success
                }
            },
            error = {
                mImgMsgSendState.value = false
            }
        )
    }

    /**
     * 发送语音消息
     */
    fun sendAudioMsg(userName: String, file: File, duration: Int) {
        mAudioMsgSendState.value = false
        launch(
            block = {
                val currentMsg = mChatRepository.createAudioMsg(userName, file, duration)
                mMessage.value = currentMsg
                mMessage.value.let {
                    mAudioMsgSendState.value = it?.status == MessageStatus.send_success
                }
            },
            error = {
                mAudioMsgSendState.value = false
            }
        )
    }

    /**
     * 进入聊天
     */
    fun enterChat(userName: String) {
        launch(
            block = {
                mChatRepository.enterChat(userName)
            }
        )
    }

    /**
     * 退出聊天
     */
    fun exitChat() {
        launch(
            block = {
                mChatRepository.exitChat()
            }
        )
    }
}