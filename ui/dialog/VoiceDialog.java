package com.yk.silence.customnode.ui.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;

import com.yk.silence.customnode.R;
import com.yk.silence.customnode.impl.OnCommonDialogListener;


public class VoiceDialog extends AlertDialog {


    public VoiceDialog(@NonNull Context context) {
        super(context, R.style.record_voice_dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_voice_layout);
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
        //window.setWindowAnimations(R.style.record_voice_dialog); //添加动画
    }

    /**
     * 初始化控件
     */
    private void initView() {
        AppCompatImageView mVolumeIv = findViewById(R.id.img_voice_animation);
        Chronometer mVoiceTime = findViewById(R.id.voice_time);
        mVolumeIv.setImageResource(R.drawable.voice_animation);
        AnimationDrawable mVoiceAnimation = (AnimationDrawable) mVolumeIv.getDrawable();
        TextView mTimeDown = findViewById(R.id.time_down);
        LinearLayout mMicShow = findViewById(R.id.mic_show);
    }


}
