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

public class DashLine extends View {

    public DashLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = this.getHeight();

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);//线条颜色
        paint.setAlpha(0x26);//线条透明度
        paint.setStrokeWidth(width);//线条宽度
        Path path = new Path();
        path.moveTo(width / 2, 0);//线条起点
        path.lineTo(width / 2, height);//线条终点
        PathEffect effects = new DashPathEffect(new float[]{20, 10}, 1);//虚线规则，20px实线，10px空白
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}
