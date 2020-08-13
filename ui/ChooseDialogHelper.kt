package com.yk.silence.customnode.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.yk.silence.customnode.R
import com.yk.silence.customnode.impl.OnCameraClickListener

object ChooseDialogHelper {

    /**
     * 选择拍照
     */
    fun showCamera(
        context: Activity?,
        mListener: OnCameraClickListener
    ) {
        val mDialog = Dialog(context!!, R.style.default_dialog_style)
        val inflater = LayoutInflater.from(context)
        @SuppressLint("InflateParams") val view: View =
            inflater.inflate(R.layout.dialog_camera_layout, null)
        mDialog.setContentView(view)
        val window = mDialog.window!!
        window.setWindowAnimations(R.style.city_animation) // 添加动画
        window.setGravity(Gravity.BOTTOM)
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mDialog.show()
        mDialog.setCanceledOnTouchOutside(true)
        val mBtnCancel = view.findViewById<TextView>(R.id.txt_dialog_cancel)
        val mBtnCamera = view.findViewById<TextView>(R.id.txt_dialog_camera)
        val mBtnAlbum = view.findViewById<TextView>(R.id.txt_dialog_album)
        val listener =
            View.OnClickListener { v ->
                when (v.id) {
                    R.id.txt_dialog_cancel -> mDialog.dismiss()
                    R.id.txt_dialog_camera -> {
                        mListener.onCameraClick(0)
                        mDialog.dismiss()
                    }
                    R.id.txt_dialog_album -> {
                        mListener.onCameraClick(1)
                        mDialog.dismiss()
                    }
                    else -> {
                    }
                }
            }
        mBtnCancel.setOnClickListener(listener)
        mBtnAlbum.setOnClickListener(listener)
        mBtnCamera.setOnClickListener(listener)
    }
}