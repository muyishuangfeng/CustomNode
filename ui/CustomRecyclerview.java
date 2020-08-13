package com.yk.silence.customnode.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 解决recyclerview和viewpager滑动冲突
 */
public class CustomRecyclerview extends RecyclerView {

    private int startX;

    public CustomRecyclerview(@NonNull Context context) {
        this(context, null);
    }

    public CustomRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = (int) ev.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (ev.getX() < startX) getParent().requestDisallowInterceptTouchEvent(true);
//                else getParent().requestDisallowInterceptTouchEvent(false);
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
