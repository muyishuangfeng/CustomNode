package com.yk.silence.customnode.base.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.APP
import com.yk.silence.customnode.common.USER_LOGIN_STATE_CHANGED
import com.yk.silence.customnode.ext.showToast
import com.yk.silence.customnode.net.Exception.ApiException
import com.yk.silence.customnode.util.EventBus
import com.yk.silence.customnode.viewmodel.user.UserRepository
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.SocketTimeoutException

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {

    //用户信息
    protected val mUserRepository by lazy { UserRepository() }
    //登录状态
    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @return Job
     */
    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e)
                        error?.invoke(e)
                    }

                }
            }
        }
    }

    /**
     * 创建并执行协程
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    /**
     * 取消协程
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCancelled && !job.isCompleted) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     */
    private fun onError(e: Exception) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    1001 -> {//登录失败处理
                        // 登录失效
                        mUserRepository.clearLoginState()
                        EventBus.post(USER_LOGIN_STATE_CHANGED, false)
                        loginStatusInvalid.value = false
                    }
                    -1 -> {
                        // 其他Api错误
                        APP.sInstance.showToast(e.message)
                    }
                    else -> {
                        // 其他错误
                        APP.sInstance.showToast(e.message)
                    }
                }
            }
            is ConnectException -> {
                // 连接失败
                APP.sInstance.showToast(APP.sInstance.getString(R.string.network_connection_failed))
            }
            is SocketTimeoutException -> {
                // 请求超时
                APP.sInstance.showToast(APP.sInstance.getString(R.string.network_request_timeout))
            }
            is JsonParseException -> {
                // 解析失败
                APP.sInstance.showToast(APP.sInstance.getString(R.string.api_data_parse_error))
            }
        }
    }

    /**
     * 登录状态
     */
    fun loginState() = mUserRepository.isLogin()
}