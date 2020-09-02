package com.yk.silence.customnode.util.media

import android.app.Activity
import android.content.Context
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import com.yk.silence.customnode.common.BASE_OSS_URL
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.impl.OnOssResultListener
import com.yk.silence.customnode.impl.OnUploadListener
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.util.oss.OSSHelper
import java.io.File
import java.io.IOException

/**
 * 录音工具类
 */
class RecordManager {

    private var mFilePath: String = ""
    private var mMediaRecorder: MediaRecorder? = null
    private var mFileName = ""
    private var isRecording = false


    companion object {

        @Volatile
        private var sInstance: RecordManager? = null
        fun getInstance(): RecordManager {
            if (sInstance === null) {
                synchronized(RecordManager::class.java) {
                    if (sInstance == null) {
                        sInstance =
                            RecordManager()
                    }
                }
            }
            return sInstance!!
        }

    }

    /**
     * 开始录音
     */
    fun startRecord(activity: Activity) {
        CThreadPoolExecutor.runInBackground(Runnable {
            mFileName = System.currentTimeMillis().toString() + ".amr"
            val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File(storageDir, mFileName)
            mFilePath = file.absolutePath
            mMediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setOutputFile(mFilePath)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setAudioChannels(1)
                // 设置录音文件的清晰度
                setAudioSamplingRate(44100)
                setAudioEncodingBitRate(192000)
                setOnErrorListener { mediaRecorder, i, i2 ->
                    // 发生错误，停止录制
                    mediaRecorder.stop()
                    mediaRecorder.release()
                    isRecording = false
                }
                try {
                    prepare()
                    start()
                    isRecording = true
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
    }

    /**
     * 停止录音
     */
    fun stopRecord(context: Context,mListener: OnUploadListener) {
        CThreadPoolExecutor.runInBackground(Runnable {
            if (isRecording) {
                if (mMediaRecorder != null) {
                    mMediaRecorder?.apply {
                        try {
                            stop()
                            release()
                            isRecording = false
                        } catch (ex: RuntimeException) {
                            release()
                        }
                        upload(context,mListener)
                    }
                }
            }
        })
    }

    /**
     * 上传文件
     */
    private fun upload(context: Context, mListener: OnUploadListener) {
        if (mFileName.isNotEmpty() && mFilePath.isNotEmpty())
            CThreadPoolExecutor.runInBackground(Runnable {
                OSSHelper.updateFile(
                    context,
                    mFileName,
                    mFilePath,
                    object : OnOssResultListener {
                        override fun onResultSuccess() {
                            val mVoiceUrl = BASE_OSS_URL + mFileName
                            mListener.onResultSuccess(mVoiceUrl)
                            ToastUtil.getInstance()
                                .shortToast(context, "发送成功")

                        }

                        override fun onResultFailed() {
                        }

                    })
            })
    }


}