package com.example.testtextviewandtags;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by caiweixin on 7/16/15.
 */
public class TagTextView extends TextView {

    private int maxWidth = 300;

    public TagTextView(Context context) {
        super(context);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int width = 0;
//        int height = 0;
//
//        if (getMeasuredWidth() > maxWidth) {
//            width = maxWidth;
//        } else {
//            width = widthSize;
//        }
//
//        height = getMeasuredHeight();
//
//        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
//                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }
}
