package com.yk.silence.customnode.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.im.android.api.ContactManager
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.api.BasicCallback
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.widget.activity.ChatActivity
import com.yk.silence.customnode.widget.activity.LoginActivity
import com.yk.silence.customnode.widget.activity.MainActivity
import java.io.File
import java.util.*

/**
 * 极光登录帮助类
 */
object JPushLoginUtil {


    /**
     * 登录界面
     */
    fun login(mUserID: Int) {
        val myInfo = JMessageClient.getMyInfo()
        if (myInfo == null) {//注册
            if (SPUTil.getString(APP.sInstance.applicationContext, LOGIN_STATUS) != null) {
                jPushLogin( mUserID)
            } else {
                jPushRegister(mUserID)
            }
        } else {//登录
            jPushLogin(mUserID)
        }

    }


    /**
     * 极光登录
     */
    fun jPushLogin( mUserID: Int) {
        if (mUserID != 0) {
            JMessageClient.login(
                CHAT_USER + "$mUserID",
                CHAT_USER + "$mUserID",
                object : BasicCallback() {
                    override fun gotResult(responseCode: Int, responseMessage: String) {
                        if (responseCode == 0) {
                            val myInfo = JMessageClient.getMyInfo()
                            val avatarFile = myInfo.avatarFile
                            //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                            if (avatarFile != null) {
                                SPUTil.putAsyncString(
                                    APP.sInstance.applicationContext,
                                    USER_AVATAR_PATH,
                                    avatarFile.absolutePath
                                )
                            }
                            //数据库保存
                            val username = myInfo.userName
                            val appKey = myInfo.appKey
                            SPUTil.putAsyncString(
                                APP.sInstance.applicationContext,
                                JPUSH_KEY,
                                appKey
                            )
                            SPUTil.putAsyncInt(
                                APP.sInstance.applicationContext,
                                USER_ID,
                                mUserID
                            )
                            SPUTil.putAsyncString(
                                APP.sInstance.applicationContext,
                                LOGIN_STATUS,
                                LOGIN_STATUS_VALUE
                            )
                            SPUTil.putAsyncString(
                                APP.sInstance.applicationContext,
                                USER_NICE_NAME,
                                username
                            )
                            SPUTil.putAsyncString(
                                APP.sInstance.applicationContext,
                                LOGIN_STATUS,
                                LOGIN_STATUS_VALUE
                            )
                            ActivityManager.start(MainActivity::class.java)
                        } else {
                            jPushRegister(mUserID)
                        }
                    }
                })

        }

    }


    /**
     * 极光注册
     */
    fun jPushRegister( mUserID: Int) {
        if (mUserID != 0) {
            JMessageClient.register(
                CHAT_USER + "$mUserID", CHAT_USER + "$mUserID",
                object : BasicCallback() {
                    override fun gotResult(i: Int, s: String) {
                        if (i == 0) {
                            jPushLogin( mUserID)
                        } else if (i == 898001) {//已经注册
                            jPushLogin( mUserID)
                        }
                    }
                })
        }

    }



    /**
     * 欢迎界面登录
     */
    fun welcome() {
        if (SPUTil.getInt(APP.sInstance.applicationContext,USER_ID, 0) != 0) {
            val myInfo = JMessageClient.getMyInfo()
            if (myInfo == null) {//注册
                if (SPUTil.getString(APP.sInstance.applicationContext, LOGIN_STATUS) != null) {
                    jPushLogin()
                } else {
                    jPushRegister()
                }
            } else {//登录
                jPushLogin()
            }
        } else {
            ActivityManager.start(LoginActivity::class.java)
        }


    }

    /**
     * 极光登录
     */
    private fun jPushLogin() {
        if (SPUTil.getInt(APP.sInstance.applicationContext, USER_ID, 0) != 0) {
            JMessageClient.login(
                CHAT_USER + SPUTil.getInt(APP.sInstance.applicationContext, USER_ID),
                CHAT_USER + SPUTil.getInt(APP.sInstance.applicationContext, USER_ID),
                object : BasicCallback() {
                    override fun gotResult(responseCode: Int, responseMessage: String) {
                        if (responseCode == 0) {
                            val myInfo = JMessageClient.getMyInfo()
                            val avatarFile = myInfo.avatarFile
                            //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                            if (avatarFile != null) {
                                SPUTil.putAsyncString(
                                    APP.sInstance.applicationContext,
                                    USER_AVATAR_PATH,
                                    avatarFile.absolutePath
                                )
                            }
                            //数据库保存
                            val username = myInfo.userName
                            val appKey = myInfo.appKey
                            SPUTil.putAsyncString(APP.sInstance.applicationContext,JPUSH_KEY, appKey)
                            SPUTil.putAsyncString(APP.sInstance.applicationContext, USER_NICE_NAME, username)
                            SPUTil.putAsyncString(
                                APP.sInstance.applicationContext,
                                LOGIN_STATUS,
                                LOGIN_STATUS_VALUE
                            )
                            ActivityManager.start( MainActivity::class.java)
                        } else {
                            ActivityManager.start( LoginActivity::class.java)
                        }
                    }
                })

        }

    }

    /**
     * 极光注册
     */
    private fun jPushRegister() {
        if (SPUTil.getInt(APP.sInstance.applicationContext, USER_ID, 0) != 0) {
            JMessageClient.register(
                CHAT_USER + SPUTil.getInt(APP.sInstance.applicationContext, USER_ID),
                CHAT_USER + SPUTil.getInt(APP.sInstance.applicationContext, USER_ID),
                object : BasicCallback() {
                    override fun gotResult(i: Int, s: String) {
                        if (i == 0) {
                            jPushLogin()
                        } else if (i == 898001) {
                            jPushLogin()
                        }
                    }
                })
        }

    }

    /**
     * 添加好友
     */
    fun addFriend(context: Context, userName: String, reason: String) {
        ContactManager.sendInvitationRequest(userName, JPUSH_TARGET_APP_KEY,
            reason, object : BasicCallback() {
                override fun gotResult(responseCode: Int, responseMessage: String) {
                    when (responseCode) {
                        0 -> {
                            ToastUtil.getInstance().shortToast(context.applicationContext, "申请成功")
                        }
                        871317 -> {
                            ToastUtil.getInstance()
                                .shortToast(context.applicationContext, "不能添加自己为好友")
                        }
                        else -> {
                            ToastUtil.getInstance().shortToast(context.applicationContext, "申请失败")
                        }
                    }
                }
            })
    }

    /**
     * 打开聊天界面
     */
    fun startConversation(mActivity: Activity, userName: String, title: String) {
        val intent = Intent(mActivity, ChatActivity::class.java)
        intent.putExtra(TARGET_ID, userName)
        intent.putExtra(TARGET_APP_KEY, JPUSH_TARGET_APP_KEY)
        intent.putExtra(CONV_TYPE, "single")
        intent.putExtra(CONV_TITLE, title)
        mActivity.startActivity(intent)
    }


    /**
     * 创建个会话
     */
    fun addConversation(userName: String) {
        val conversation = Conversation.createSingleConversation(
            userName,
            JPUSH_TARGET_APP_KEY
        )
//        EventBus.getDefault().post(
//            Event.Builder()
//                .setType(EventType.createConversation)
//                .setConversation(conversation)
//                .build()
//        )
    }

    /**
     * 更新个人信息
     */
    fun updateUserInfo(nickName: String, avatarUrl: String) {
        val mMyInfo = JMessageClient.getMyInfo()
        mMyInfo?.nickname = nickName
        JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, object : BasicCallback() {
            override fun gotResult(responseCode: Int, responseMessage: String) {
            }
        })
        val map = WeakHashMap<String, String>()
        map[USER_AVATAR_PATH] = avatarUrl
        mMyInfo.setUserExtras(map)
        JMessageClient.updateMyInfo(UserInfo.Field.extras, mMyInfo, object : BasicCallback() {
            override fun gotResult(responseCode: Int, responseMessage: String) {
            }
        })
    }


    /**
     * 更新头像
     */
    fun updateAvatar(
        file: File
    ) {
        val mMyInfo = JMessageClient.getMyInfo()
        JMessageClient.updateUserAvatar(file, object : BasicCallback() {
            override fun gotResult(responseCode: Int, responseMessage: String) {
            }
        })
    }





    /**
     * 获取UserID
     */
    fun getUserInfo(): Long {
        val mUserInfo = JMessageClient.getMyInfo()
        return mUserInfo.userID
    }

}