package com.yk.silence.customnode.impl

import android.app.Dialog
import android.content.Context

/**
 * 获取对话框
 */
interface OnCallDialog {

    fun getDialog(context: Context?): Dialog
}