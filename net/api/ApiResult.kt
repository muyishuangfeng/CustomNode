package com.yk.silence.customnode.net.api

import com.yk.silence.customnode.net.Exception.ApiException


/**
 * 结果
 */
data class ApiResult<T>(val errorCode: Int, val errorMsg: String, private val data: T) {
    /**
     * 获取数据
     */
    fun apiData(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(errorCode, errorMsg)
        }
    }
}