package com.yk.silence.customnode.impl


/**
 * 拍照和从相册选择结果接口
 */
interface OnCameraResultListener {

    fun onCameraResult(path: String?)

    fun onAlbumResult(path: String?)
}