package com.yk.silence.customnode.net.api

import com.yk.silence.customnode.model.FriendBean
import com.yk.silence.customnode.model.UserModel
import com.yk.silence.customnode.model.UserNodeMode
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASE_URL = "http://192.168.196.126:8080/"
    }

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("User/login")
    suspend fun login(
        @Field("user_name") username: String,
        @Field("user_pass") password: String
    ): ApiResult<UserModel>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("User/register")
    suspend fun register(
        @Field("user_name") username: String,
        @Field("user_pass") password: String
    ): ApiResult<UserModel>

    /**
     * 更新用户信息
     */
    @FormUrlEncoded
    @POST("/User/update")
    suspend fun update(
        @Field("id") id: Int,
        @Field("user_name") username: String,
        @Field("user_pass") password: String,
        @Field("user_avatar") user_avatar: String
    ): ApiResult<UserModel>

    /**
     * 查询好友
     */
    @FormUrlEncoded
    @POST("/User/searchUser")
    suspend fun searchFriend(
        @Field("id") id: Int
    ): ApiResult<UserModel>

    /**
     * 添加好友
     */
    @FormUrlEncoded
    @POST("/friend/addFriend")
    suspend fun addFriend(
        @Field("id") id: Int
    ): ApiResult<FriendBean>

    /**
     * 删除好友
     */
    @FormUrlEncoded
    @POST("/friend/deleteFriend")
    suspend fun deleteFriend(
        @Field("id") id: Int
    ): ApiResult<String>

    /**
     * 添加笔记
     */
    @POST("/Node/saveNode")
    suspend fun addNode(
        @Query("nodeJson") nodeJson: String,
        @Query("imgList") imgList: String
    ): ApiResult<UserNodeMode>

    /**
     * 删除笔记
     */
    @POST("/Node/deleteNode")
    suspend fun deleteNode(
        @Query("id") id: Int
    ): ApiResult<String>


}