package com.yk.silence.customnode.impl

interface OnFileUploadListener {

    fun onUploadSuccess(result:String)

    fun onUploadFailed()
}