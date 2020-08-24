package com.yk.silence.customnode.service

import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModelProvider
import com.yk.silence.customnode.common.CHAT_USER_ID
import com.yk.silence.customnode.common.CHAT_USER_TOKEN
import com.yk.silence.customnode.common.HOST
import com.yk.silence.customnode.common.MSG_CODE_ADD_MSG
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.im.bean.SingleMessage
import com.yk.silence.customnode.im.event.CEventCenter
import com.yk.silence.customnode.im.event.Events
import com.yk.silence.customnode.im.event.I_CEventListener
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.util.EventUtil
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.viewmodel.chat.ChatViewModel

class ChatService :LifecycleService(), I_CEventListener {



    override fun onCreate() {
        super.onCreate()
        CEventCenter.registerEventListener(this, Events.CHAT_SINGLE_MESSAGE)

    }


    override fun onCEvent(topic: String?, msgCode: Int, resultCode: Int, obj: Any?) {
        if (topic == Events.CHAT_SINGLE_MESSAGE) {
            val message = obj as SingleMessage
            CThreadPoolExecutor.runOnMainThread(Runnable {
                EventUtil.send(EventModel(MSG_CODE_ADD_MSG,message))
            })

        }
    }


    override fun onDestroy() {
        CEventCenter.unregisterEventListener(this, Events.CHAT_SINGLE_MESSAGE)
        super.onDestroy()
    }


}