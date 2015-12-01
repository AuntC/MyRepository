package com.example.testtextviewandtags;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by caiweixin on 7/16/15.
 */
public class Container extends LinearLayout {

    public Container(Context context) {
        super(context);
    }

    public Container(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Container(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int textwidth = 0;
        int otherswidth = 0;

        final int count = getChildCount();

        View child0 = getChildAt(0);
        if (null != child0) {
            if (child0.getVisibility() != GONE) {
                textwidth += child0.getMeasuredWidth();
            }
        }

        for (int i = 1; i < count; i++) {
            View child = getChildAt(i);
            final int childWidth = child.getMeasuredWidth();

            if (child.getVisibility() != GONE) {
                otherswidth += childWidth;
            }
        }

        int maxWidth = widthSize - otherswidth;
        if (textwidth > maxWidth && child0 instanceof TextView) {
            ((TextView) child0).setMaxWidth(maxWidth);
        }

        setMeasuredDimension(widthSize, heightSize);
    }
}
