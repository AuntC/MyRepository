package com.example.testtextviewandtags;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by caiweixin on 7/18/15.
 */
public class DeleteTextView extends TextView {

    Paint paint;
    Path path;

    int width, height;

    public DeleteTextView(Context context) {
        super(context);
        initArgs();
    }

    public DeleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initArgs();
    }

    public DeleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initArgs();
    }

    private void initArgs() {
        paint = new Paint();
        path = new Path();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);//线条颜色
        paint.setAlpha(128);//线条透明度
        paint.setStrokeWidth(3);//线条宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        path.moveTo(0, height / 2);//线条起点
        path.lineTo(width, height / 2);//线条终点

        canvas.drawPath(path, paint);
    }
}
