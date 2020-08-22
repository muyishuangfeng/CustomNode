package com.yk.silence.customnode.util

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

object EventUtil {

    /**
     * 发送
     */
    @Subscribe
    fun send(event: Any) {
        EventBus.getDefault().post(event)
    }

    /**
     * 注册
     */
    @Subscribe
    fun register(context: Fragment) {
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context)
        }
    }
    /**
     * 注册
     */
    @Subscribe
    fun register(context: Activity) {
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context)
        }
    }

    /**
     * 反注册
     */
    @Subscribe
    fun unRegister(context: Activity) {
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context)
        }
    }
    /**
     * 反注册
     */
    @Subscribe
    fun unRegister(context: Fragment) {
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context)
        }
    }
}