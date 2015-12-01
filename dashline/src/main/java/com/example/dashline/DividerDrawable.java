package com.example.dashline;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by caiweixin on 8/6/15.
 */
public class DividerDrawable extends ColorDrawable {

    private int mPadding;
    private Paint mPaint;

    public DividerDrawable(int padding) {
        this.mPadding = padding;
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(0x00, 0x00, 0x00));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(new Rect(mPadding, 0, 10 * mPadding, 2), mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
