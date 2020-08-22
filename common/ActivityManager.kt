package com.yk.silence.customnode.common

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import com.yk.silence.customnode.ext.putExtras

/**
 * activity管理类
 */
object ActivityManager {

    //activity集合
    val activities = mutableListOf<Activity>()

    /**
     * 开始
     */
    fun start(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    /**
     * 开始
     */
    fun startForResult(
        clazz: Class<out Activity>,
        code: Int
    ) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        currentActivity.startActivityForResult(intent, code)
    }

    /**
     * 开始
     */
    fun start(
        clazz: Class<out Activity>,
        params: Map<String, Any> = emptyMap(),
        img: AppCompatImageView
    ) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            currentActivity, img,
            img.transitionName
        ).toBundle()
        currentActivity.startActivity(intent, options)
    }

    /**
     * finish指定的一个或多个Activity
     */
    fun finish(vararg clazz: Class<out Activity>) {
        activities.forEach { activity ->
            if (clazz.contains(activity::class.java)) {
                activity.finish()
            }
        }
    }

}