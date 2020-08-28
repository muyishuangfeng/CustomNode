package com.yk.silence.customnode.net.api

import com.yk.silence.customnode.model.UserModel
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
     * 注册
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/User/update")
    suspend fun update(
        @Field("id") id: Int,
        @Field("user_name") username: String,
        @Field("user_pass") password: String,
        @Field("user_avatar") user_avatar: String
    ): ApiResult<UserModel>


}