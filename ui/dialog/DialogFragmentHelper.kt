package com.yk.silence.customnode.ui.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseDialogFragment
import com.yk.silence.customnode.impl.OnCallDialog
import com.yk.silence.customnode.impl.OnCommonDialogListener
import com.yk.silence.customnode.impl.OnDialogCancelListener


/**
 * DialogFragment帮助类
 */
object DialogFragmentHelper {

    /**
     * 加载中的弹出窗
     */
    private val mTheme: Int = R.style.Base_AlertDialog
    private val TAG = DialogFragmentHelper::class.java.simpleName
    private val mProgressTag: String = "$TAG:progress"

    /**
     * 进度条对话框
     */
    fun showProgress(
        fragmentManager: FragmentManager?,
        message: String?
    ): BaseDialogFragment? {
        return showProgress(
            fragmentManager,
            message,
            true,
            null
        )
    }

    /**
     * 进度条对话框
     */
    fun showProgress(
        fragmentManager: FragmentManager?,
        message: String?,
        cancelable: Boolean
    ): BaseDialogFragment? {
        return showProgress(
            fragmentManager,
            message,
            cancelable,
            null
        )
    }

    /**
     * 进度条对话框
     */
    fun showProgress(
        fragmentManager: FragmentManager?, message: String?, cancelable: Boolean
        , cancelListener: OnDialogCancelListener?
    ): BaseDialogFragment? {
        val dialogFragment: BaseDialogFragment? =
            BaseDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context?): Dialog {
                    val progressDialog = ProgressDialog(
                        context,
                        mTheme
                    )
                    progressDialog.setMessage(message)
                    return progressDialog
                }
            }, cancelable, cancelListener)
        dialogFragment?.show(
            fragmentManager!!,
            mProgressTag
        )
        return dialogFragment
    }

    /**
     * 提示对话框
     */
    fun showTips(
        fragmentManager: FragmentManager?,
        message: String?
    ) {
        showTips(
            fragmentManager,
            message,
            true,
            null
        )
    }

    /**
     * 提示对话框
     */
    fun showTips(
        fragmentManager: FragmentManager?,
        message: String?,
        cancelable: Boolean
    ) {
        showTips(
            fragmentManager,
            message,
            cancelable,
            null
        )
    }

    /**
     * 提示对话框
     */
    fun showTips(
        fragmentManager: FragmentManager?,
        message: String?,
        cancelable: Boolean,
        cancelListener: OnDialogCancelListener?
    ) {
        val dialogFragment: BaseDialogFragment? =
            BaseDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context?): Dialog {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(
                        context!!,
                        mTheme
                    )
                    builder.setMessage(message)
                    return builder.create()
                }
            }, cancelable, cancelListener)
        dialogFragment?.show(
            fragmentManager!!,
            mProgressTag
        )
    }


    /**
     * 公共对话框
     */
    fun commonDialog(
        fragmentManager: FragmentManager?,
        title: String,
        content: String,
        code: Int,
        mListener: OnCommonDialogListener

    ) {
        val dialogFragment: BaseDialogFragment? =
            BaseDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context?): Dialog {
                    val mDialog =
                        CommonDialog(context!!, code)
                    mDialog.setTitle(title)
                    mDialog.setContent(content)
                    mDialog.setOnCommonClickListener(object : OnCommonDialogListener {
                        override fun onCancel() {
                            mListener.onCancel()
                        }

                        override fun onResult() {
                            mListener.onResult()
                        }
                    })
                    return mDialog
                }
            }, true, null)
        dialogFragment?.show(
            fragmentManager!!,
            mProgressTag
        )
    }

    /**
     * 显示录音对话框
     */
    fun voiceDialog(fragmentManager: FragmentManager?) {
        val dialogFragment: BaseDialogFragment? =
            BaseDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context?): Dialog {
                    return VoiceDialog(context!!)
                }
            }, true, null)
        dialogFragment?.show(
            fragmentManager!!,
            mProgressTag
        )
    }

}