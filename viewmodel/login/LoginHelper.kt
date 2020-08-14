package com.yk.silence.customnode.viewmodel.login

import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.api.BasicCallback
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.util.JPushLoginUtil
import com.yk.silence.customnode.util.SPUTil

class LoginHelper private constructor() {


    companion object {
        val instance: LoginHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LoginHelper()
        }


    }

    /**
     * 极光登录
     */
    suspend fun jPushLogin(userID: Int): Boolean {
        var mResult = false
        JMessageClient.login(CHAT_USER + userID, CHAT_USER + userID,
            object : BasicCallback() {
                override fun gotResult(responseCode: Int, responseMessage: String) {
                    mResult = responseCode == 0
                }
            })
        return mResult
    }

    /**
     * 极光注册
     */
    suspend fun jPushRegister(userID: Int): Boolean {
        var mResult = false
        JMessageClient.register(CHAT_USER + userID, CHAT_USER + userID,
            object : BasicCallback() {
                override fun gotResult(i: Int, s: String) {
                    mResult = i == 0 || i == 898001
                }
            })
        return mResult
    }
}