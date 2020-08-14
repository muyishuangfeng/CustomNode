package com.yk.silence.customnode.util.oss

import android.content.Context
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.yk.silence.customnode.common.BUCKET_NAME
import com.yk.silence.customnode.common.OSS_ACCESS_KEY_ID
import com.yk.silence.customnode.common.OSS_ACCESS_KEY_SECRET
import com.yk.silence.customnode.common.OSS_ENDPOINT
import com.yk.silence.customnode.impl.OnOssResultListener

class OSSHelper {


    /**
     * 初始化阿里云
     */
    private fun initOss(
        context: Context
    ): OSSClient {
        val credentialProvider: OSSCredentialProvider = OSSPlainTextAKSKCredentialProvider(
            OSS_ACCESS_KEY_ID,
            OSS_ACCESS_KEY_SECRET
        )
        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // 连接超时，默认15秒。
        conf.socketTimeout = 15 * 1000 // socket超时，默认15秒。
        conf.maxConcurrentRequest = 5 // 最大并发请求书，默认5个。
        conf.maxErrorRetry = 2 // 失败后最大重试次数，默认2次。
        return OSSClient(context, OSS_ENDPOINT, credentialProvider, conf)
    }


    /**
     * 上传头像
     */
    fun updateFile(
        context: Context,
        name: String,
        path: String,
        mListener: OnOssResultListener
    ) { // 构造上传请求。
        val put = PutObjectRequest(BUCKET_NAME, name, path)
        // 异步上传时可以设置进度回调。
        put.progressCallback = OSSProgressCallback<PutObjectRequest?> { _, _, _ ->
        }
        val task: OSSAsyncTask<*> = initOss(context).asyncPutObject(put, object :
            OSSCompletedCallback<PutObjectRequest?, PutObjectResult> {
            override fun onSuccess(
                request: PutObjectRequest?,
                result: PutObjectResult
            ) {
                mListener.onResultSuccess()
            }

            override fun onFailure(
                request: PutObjectRequest?,
                clientExcepion: ClientException,
                serviceException: ServiceException
            ) { // 请求异常。
                clientExcepion.printStackTrace()
                mListener.onResultFailed()
            }
        })
        task.waitUntilFinished()
    }

}