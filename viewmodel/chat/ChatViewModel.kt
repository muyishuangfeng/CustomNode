package com.yk.silence.customnode.viewmodel.chat

import com.yk.silence.customnode.base.vm.BaseViewModel

class ChatViewModel : BaseViewModel() {

    private val mChatRepository by lazy { ChatRepository() }

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