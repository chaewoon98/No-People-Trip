package com.test.mosun.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class RectangleLinearLayout extends LinearLayout {

    public RectangleLinearLayout(Context context) {
        super(context);
    }

    public RectangleLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectangleLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 너비에 맞춰 정사각형으로 만듦
        super.onMeasure(widthMeasureSpec, widthMeasureSpec/2);
    }

}

