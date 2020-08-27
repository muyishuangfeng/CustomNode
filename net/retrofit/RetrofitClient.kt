package com.yk.silence.customnode.net.retrofit

import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.yk.silence.customnode.net.api.ApiService
import com.yk.silence.customnode.common.APP
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit
 */
object RetrofitClient {
    //自动cookies持久化
    private val cookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(APP.sInstance)
    )
    //okHttp客户端
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggerInterceptor)
        .callTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .build()
    //retrofit客户端
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    /**
     * 清空cookies
     */
    fun clearCookie() = cookieJar.clear()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    /**
     * 日志拦截器
     * 将你访问的接口信息
     *
     * @return 拦截器
     */
    private val loggerInterceptor: HttpLoggingInterceptor
        get() { //日志显示级别
            val level = HttpLoggingInterceptor.Level.BODY
            //新建log拦截器
            val loggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    Log.e(
                        "API_LOG",
                        "--->$message"
                    )
                })
            loggingInterceptor.level = level
            return loggingInterceptor
        }
}