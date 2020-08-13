package com.yk.silence.customnode.receiver

import android.content.Context
import android.content.Intent
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.NotificationClickEvent
import com.yk.silence.customnode.common.CONV_TITLE
import com.yk.silence.customnode.common.TARGET_APP_KEY
import com.yk.silence.customnode.common.TARGET_ID
import com.yk.silence.customnode.widget.activity.ChatActivity

class NotificationClickEventReceiver(private val mContext: Context) {

    init {
        //注册接收消息事件
        JMessageClient.registerEventReceiver(this)
    }

    /**
     * 收到消息处理
     *
     * @param notificationClickEvent 通知点击事件
     */
    fun onEvent(notificationClickEvent: NotificationClickEvent?) {
        if (null == notificationClickEvent) {
            return
        }
        val msg = notificationClickEvent.message
        if (msg != null) {
            val targetId = msg.targetID
            val appKey = msg.fromAppKey
            val type = msg.targetType
            val notificationIntent = Intent(mContext, ChatActivity::class.java)
            val conv =
                JMessageClient.getSingleConversation(targetId, appKey)
            notificationIntent.putExtra(TARGET_ID, targetId)
            notificationIntent.putExtra(TARGET_APP_KEY, appKey)
            notificationIntent.putExtra(CONV_TITLE, conv.title)
            conv.resetUnreadCount()
            notificationIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mContext.startActivity(notificationIntent)
        }
    }


}