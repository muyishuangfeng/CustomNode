package com.yk.silence.customnode.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore.*
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.yk.silence.customnode.R
import com.yk.silence.customnode.common.REQUEST_CODE_OPEN_PHOTO_ALBUM
import com.yk.silence.customnode.common.REQUEST_CODE_TAKE_PHOTO
import com.yk.silent.permission.HiPermission
import com.yk.silent.permission.impl.PermissionCallback
import com.yk.silent.permission.model.PermissionItem
import java.io.File

/**
 * 拍照（兼容Android10）
 */
object CameraUtil {
    private var mTakeImgUri: Uri? = null

    /**
     * 打开相机
     */
    fun takePhoto(context: Activity, code: Int) {
        initPermission(context, code)
    }

    /**
     * 打开相册
     */
    fun openAlbum(context: Activity, code: Int) {
        initPermission(context, code)
    }

    /**
     * 打开相机
     */
    private fun takeCamera(context: Activity, code: Int) {
        val intentCamera = Intent(ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
            mTakeImgUri = createImageUri(context)
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val jpgFile = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis().toString() + ".jpg"
            )
            mTakeImgUri =
                FileProvider.getUriForFile(context, AppUtil.getPackageName(context), jpgFile)
            //            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            val jpgFile = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis().toString() + ".jpg"
            )
            mTakeImgUri = Uri.fromFile(jpgFile)
        }
        //将拍照结果保存至 photo_file 的 Uri 中，不保留在相册中
        intentCamera.putExtra(EXTRA_OUTPUT, mTakeImgUri)
        context.startActivityForResult(intentCamera, code)
    }

    /**
     * 相册选择
     */
    private fun takeAlbum(context: Activity, code: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
        photoPickerIntent.type = "image/*"
        context.startActivityForResult(photoPickerIntent, code)
    }


    /**
     * Android 10 获取 图片 uri
     *
     * @return 生成的图片uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private fun createImageUri(context: Activity): Uri? {
        val values = ContentValues()
        // 需要指定文件信息时，非必须
        values.put(Images.Media.DESCRIPTION, "This is an image")
        values.put(
            Images.Media.DISPLAY_NAME,
            System.currentTimeMillis().toString() + ".jpg"
        )
        values.put(
            Images.Media.TITLE,
            System.currentTimeMillis().toString() + ".jpg"
        )
        values.put(Images.Media.RELATIVE_PATH, "Pictures/albumCameraImg")
        return context.contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    /**
     * 回调
     */
    fun onActivityResult(
        context: Context,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        mListener: OnCameraResultListener
    ) {
        when (requestCode) {
            REQUEST_CODE_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (mTakeImgUri != null) {
                        mListener.onCameraResult(mTakeImgUri!!)
                    }
                }
            }
            REQUEST_CODE_OPEN_PHOTO_ALBUM -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri? = if (data != null) {
                        data.data
                    } else {
                        return
                    }
                    if (getRealPathFromUri(context, uri!!) != null) {
                        mListener.onAlbumResult(getRealPathFromUri(context, uri)!!)
                    }
                }
            }
        }


    }


    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("ObsoleteSdkInt")
    private fun getRealPathFromUri(
        context: Context,
        uri: Uri
    ): String? {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                getRealPathFromUriAboveApiAndroidQ(context, uri)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                getRealPathFromUriAboveApiAndroidK(context, uri)
            }
            else -> {
                getRealPathFromUriBelowApiAndroidK(context, uri)
            }
        }
    }

    /**
     * 适配 Android 10以上相册选取照片操作
     *
     * @param context 上下文
     * @param uri     图片uri
     * @return 图片地址
     */
    private fun getRealPathFromUriAboveApiAndroidQ(
        context: Context,
        uri: Uri
    ): String? {
        var cursor: Cursor? = null
        val path = getRealPathFromUriAboveApiAndroidK(context, uri)
        try {
            cursor = context.contentResolver.query(
                Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(Images.Media._ID),
                Images.Media.DATA + "=? ",
                arrayOf(path),
                null
            )
            return if (cursor != null && cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(MediaColumns._ID))
                val baseUri =
                    Uri.parse("content://media/external/images/media")
                Uri.withAppendedPath(baseUri, "" + id).toString()
            } else {
                // 如果图片不在手机的共享图片数据库，就先把它插入。
                if (File(path).exists()) {
                    val values = ContentValues()
                    values.put(Images.Media.DATA, path)
                    context.contentResolver
                        .insert(Images.Media.EXTERNAL_CONTENT_URI, values).toString()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            cursor?.close()
        }
        return null
    }

    /**
     * 适配Android 4.4以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private fun getRealPathFromUriBelowApiAndroidK(
        context: Context,
        uri: Uri
    ): String? {
        return getDataColumn(context, uri, null, null)
    }

    /**
     * 适配Android 4.4及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private fun getRealPathFromUriAboveApiAndroidK(
        context: Context,
        uri: Uri
    ): String? {
        var filePath: String? = null
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            val documentId = DocumentsContract.getDocumentId(uri)
            if (isMediaDocument(uri)) {
                // 使用':'分割
                val id = documentId.split(":").toTypedArray()[1]
                val selection = Images.Media._ID + "=?"
                val selectionArgs = arrayOf(id)
                filePath = getDataColumn(
                    context,
                    Images.Media.EXTERNAL_CONTENT_URI,
                    selection,
                    selectionArgs
                )
            } else if (isDownloadsDocument(uri)) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(documentId)
                )
                filePath = getDataColumn(context, contentUri, null, null)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null)
        } else if ("file" == uri.scheme) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.path
        }
        return filePath
    }


    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     */
    private fun getDataColumn(
        context: Context,
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var path: String? = null
        val projection =
            arrayOf(Images.Media.DATA)
        var cursor: Cursor? = null
        try {
            cursor =
                context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
                path = cursor.getString(columnIndex)
            }
        } catch (e: Exception) {
            cursor?.close()
        }
        return path
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * 权限
     */
    private fun initPermission(context: Activity, code: Int) {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(
            PermissionItem(
                Manifest.permission.CAMERA,
                "", R.drawable.permission_ic_camera
            )
        )
        permissionItems.add(
            PermissionItem(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "", R.drawable.permission_ic_storage
            )
        )
        permissionItems.add(
            PermissionItem(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                "", R.drawable.permission_ic_storage
            )
        )

        HiPermission.create(context)
            .title("标题")
            .msg("")
            .permissions(permissionItems)
            .checkMutiPermission(object : PermissionCallback {
                override fun onClose() {
                }

                override fun onDeny(permission: String?, position: Int) {
                }

                override fun onFinish() {
                    when (code) {
                        REQUEST_CODE_OPEN_PHOTO_ALBUM -> {
                            takeAlbum(context, code)
                        }
                        REQUEST_CODE_TAKE_PHOTO -> {
                            takeCamera(context, code)
                        }
                    }
                }

                override fun onGuarantee(permission: String?, position: Int) {

                }
            })

    }

    /**
     * 调用相机结果回调
     */
    interface OnCameraResultListener {
        fun onCameraResult(uri: Uri)
        fun onAlbumResult(path: String)
    }
}