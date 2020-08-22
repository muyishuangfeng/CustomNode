package com.yk.silence.customnode.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

import com.yk.silence.customnode.R;
import com.yk.silence.customnode.impl.OnCommonDialogListener;


public class CommonDialog extends AlertDialog implements View.OnClickListener {

    private CharSequence mTitle, mContent;
    private OnCommonDialogListener mListener;
    private int mCode;

    public CommonDialog(@NonNull Context context) {
        super(context);
    }

    public CommonDialog(@NonNull Context context, @StyleRes int code) {
        super(context);
        this.mCode = code;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_layout);
        //设置背景透明，不然会出现白色直角问题
        Window window = getWindow();
        assert window != null;
        setCanceledOnTouchOutside(false);
        //初始化布局控件
        initView();
        //设置参数必须在show之后，不然没有效果
        WindowManager.LayoutParams params = window.getAttributes();
        getWindow().setAttributes(params);
        window.setBackgroundDrawableResource(R.color.colorTransparent);
        window.setWindowAnimations(R.style.updateDialogStyle); //添加动画
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //对话框标题
        TextView txtTitle = findViewById(R.id.txt_dialog_title);
        //对话框描述信息
        TextView txtContent = findViewById(R.id.txt_dialog_content);
        //确定按钮和取消
        TextView mTxtOk = findViewById(R.id.txt_dialog_sure);
        View mTxtLine = findViewById(R.id.txt_dialog_line);
        TextView mTxtCancel = findViewById(R.id.txt_dialog_cancel);
        //********************通用设置*********************//
        //设置标题、描述及确定按钮的文本内容
        assert txtTitle != null;
        txtTitle.setText(mTitle);
        assert txtContent != null;
        txtContent.setText(mContent);
        assert mTxtOk != null;
        mTxtOk.setOnClickListener(this);
        assert mTxtCancel != null;
        mTxtCancel.setOnClickListener(this);
        switch (mCode) {
            case 1: {
                mTxtOk.setVisibility(View.VISIBLE);
                assert mTxtLine != null;
                mTxtLine.setVisibility(View.GONE);
                mTxtCancel.setVisibility(View.GONE);
                break;
            }
            case 2: {
                mTxtOk.setVisibility(View.VISIBLE);
                assert mTxtLine != null;
                mTxtLine.setVisibility(View.VISIBLE);
                mTxtCancel.setVisibility(View.VISIBLE);
                break;
            }
        }

    }


    /**
     * 标题
     */
    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    /**
     * 内容
     */
    public void setContent(CharSequence content) {
        this.mContent = content;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_dialog_sure: { //确定
                if (mListener != null) {
                    mListener.onResult();
                }
                dismiss();
                break;
            }
            case R.id.txt_dialog_cancel: { //取消
                if (mListener != null) {
                    mListener.onCancel();
                }
                dismiss();
                break;
            }
        }

    }


    public void setOnCommonClickListener(OnCommonDialogListener confirmListener) {
        this.mListener = confirmListener;
    }

}
