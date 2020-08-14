package com.yk.silence.customnode.viewmodel.user

import com.yk.silence.customnode.model.UserModel
import com.yk.silence.customnode.net.retrofit.RetrofitClient

class UserRepository {

    /**
     * 更新用户信息
     */
    fun updateUserInfo(userInfo: UserModel) = UserInfoStore.setUserInfo(userInfo)

    /**
     * 是否登录
     */
    fun isLogin() = UserInfoStore.isLogin()

    /**
     * 清除用户登录状态
     */
    fun clearLoginState() {
        UserInfoStore.clearUserInfo()
        RetrofitClient.clearCookie()
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo() = UserInfoStore.getUserInfo()
}