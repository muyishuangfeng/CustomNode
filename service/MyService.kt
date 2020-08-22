package com.yk.silence.customnode.service

import androidx.lifecycle.LifecycleService
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.im.bean.SingleMessage
import com.yk.silence.customnode.im.event.CEventCenter
import com.yk.silence.customnode.im.event.Events
import com.yk.silence.customnode.im.event.I_CEventListener
import com.yk.silence.customnode.util.ToastUtil

class MyService :LifecycleService(), I_CEventListener {

    override fun onCreate() {
        super.onCreate()
        CEventCenter.registerEventListener(this, Events.CHAT_SINGLE_MESSAGE)
    }


    override fun onCEvent(topic: String?, msgCode: Int, resultCode: Int, obj: Any?) {
        if (topic == Events.CHAT_SINGLE_MESSAGE) {
            val message = obj as SingleMessage
            CThreadPoolExecutor.runOnMainThread(Runnable {
                ToastUtil.getInstance()
                    .shortToast(this, "收到来自：" + message.fromId + "的消息====" + message.content)
            })

        }
    }


    override fun onDestroy() {
        CEventCenter.unregisterEventListener(this, Events.CHAT_SINGLE_MESSAGE)
        super.onDestroy()
    }


}