package com.yk.silence.customnode.common

import android.app.Application
import com.lzy.ninegrid.NineGridView
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

    }
}