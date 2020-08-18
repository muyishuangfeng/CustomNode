package com.yk.silence.customnode.common

import android.app.Application
import cn.jpush.im.android.api.JMessageClient
import com.lzy.ninegrid.NineGridView
import com.yk.silence.customnode.receiver.NotificationClickEventReceiver
import com.yk.silence.customnode.util.ProcessHelper
import com.yk.silence.customnode.util.glide.GlideImageLoader


class APP : Application() {
    companion object {
        lateinit var sInstance: APP
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        if (ProcessHelper.isMainProcess(this)) {
            init()
        }
    }

    /**
     * 初始化
     */
    private fun init() {
        registerActivityCallbacks()
        initJPush()
        NineGridView.setImageLoader(GlideImageLoader())
    }


    /**
     * 监听activity回调
     */
    private fun registerActivityCallbacks() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksAdapter(
            onActivityCreated = { activity, _ ->
                ActivityManager.activities.add(activity)
            },
            onActivityDestroyed = { activity ->
                ActivityManager.activities.remove(activity)
            }

        ))
    }

    /**
     * 初始化极光
     */
    private fun initJPush(){
        //极光
        JMessageClient.setDebugMode(true)
        JMessageClient.init(this, true)
        //设置Notification的模式
        JMessageClient.setNotificationFlag(
            JMessageClient.FLAG_NOTIFY_WITH_SOUND or
                    JMessageClient.FLAG_NOTIFY_WITH_LED or JMessageClient.FLAG_NOTIFY_WITH_VIBRATE
        )
        //注册Notification点击的接收器
        NotificationClickEventReceiver(applicationContext)
    }
}