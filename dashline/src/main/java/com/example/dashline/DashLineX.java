package com.example.dashline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class DashLineX extends View {
    Paint paint;
    Path path;
    PathEffect effects = new DashPathEffect(new float[]{20, 10}, 1);//虚线规则，20px实线，10px空白
    int width, height;

    public DashLineX(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);//线条颜色
        paint.setAlpha(0x26);//线条透明度
        paint.setPathEffect(effects);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        paint.setStrokeWidth(height);//线条宽度
        path.moveTo(0, height / 2);//线条起点
        path.lineTo(width, height / 2);//线条终点

        canvas.drawPath(path, paint);
    }
}
