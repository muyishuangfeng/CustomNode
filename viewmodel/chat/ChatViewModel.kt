package com.yk.silence.customnode.viewmodel.chat

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import java.io.File

class ChatViewModel : BaseViewModel() {

    private val mChatRepository by lazy { ChatRepository() }


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