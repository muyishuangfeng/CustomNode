package com.yk.silence.customnode.util

import android.app.Activity
import android.content.Intent
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.yk.silence.customnode.common.REQUEST_CODE_OPEN_PHOTO_ALBUM
import com.yk.silence.customnode.common.REQUEST_CODE_TAKE_PHOTO
import com.yk.silence.customnode.impl.OnCameraResultListener
import com.yk.silence.customnode.util.glide.GlideEngine

object CameraUtil {

    /**
     * 打开相机
     */
    fun openCamera(context: Activity) {
        EasyPhotos.createCamera(context)
            .setFileProviderAuthority(AppUtil.getPackageName(context)+".fileprovider")
            .start(REQUEST_CODE_TAKE_PHOTO)
    }

    /**
     * 打开相册
     */
    fun openAlbum(context: Activity) {
        EasyPhotos.createAlbum(context, false, GlideEngine.getInstance())
            .start(REQUEST_CODE_OPEN_PHOTO_ALBUM)
    }

    /**
     * 回调
     */
    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        mListener: OnCameraResultListener
    ) {
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                REQUEST_CODE_TAKE_PHOTO -> {//拍照
                    if (data != null) {
                        val mPhotoList: java.util.ArrayList<Photo>? =
                            data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS)
                        if (mPhotoList!!.size > 0) {
                            mListener.onCameraResult(mPhotoList[0].path)
                        }

                    }

                }
                REQUEST_CODE_OPEN_PHOTO_ALBUM -> {//从相册选择
                    if (data != null) {
                        val mPhotoList: java.util.ArrayList<Photo>? =
                            data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS)
                        if (mPhotoList!!.size > 0) {
                            mListener.onAlbumResult(mPhotoList[0].path)
                        }

                    }
                }
            }
        }
    }
}