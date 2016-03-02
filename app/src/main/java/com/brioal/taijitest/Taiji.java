package com.brioal.taijitest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class Taiji extends View {
    private Paint mPaintWhite;
    private Paint mPaintBlack;
    private int width, height;
    private int degrees = 0;
    private Matrix matrix;

    private Handler handler = new Handler(); //实现定时动画
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            degrees+=2;
            matrix = new Matrix();
            //参数:旋转角度,围绕点的坐标值
            matrix.postRotate(degrees, 0, 0);
            Taiji.this.invalidate();
            handler.postDelayed(runnable, 60); //继续循环
        }
    };

    public Taiji(Context context) {
        super(context);
        initPaint();
        handler.post(runnable);

    }

    public Taiji(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        handler.post(runnable);

    }


    public void initPaint() {
        mPaintWhite = new Paint();
        mPaintWhite.setAntiAlias(true);
        mPaintWhite.setColor(Color.WHITE);

        mPaintBlack = new Paint();
        mPaintBlack.setAntiAlias(true);
        mPaintBlack.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Point point = new Point(width / 2, height / 2); // 中心点
        canvas.translate(point.x, point.y); // 将中心点传送到正中心
        canvas.drawColor(Color.GRAY); // 绘制背景色
        //获取半径
        int radicus = Math.min(width, height) / 2 - 100;
        RectF rectF = new RectF(-radicus, -radicus, radicus, radicus);

        canvas.concat(matrix);
        canvas.drawArc(rectF, 90, 180, true, mPaintBlack);
        canvas.drawArc(rectF, -90, 180, true, mPaintWhite);
        RectF rectF1 = new RectF(-radicus / 2 - 1, -radicus, radicus / 2 - 1, 0);
        RectF rectF2 = new RectF(-radicus / 2 + 1, 0, radicus / 2 + 1, radicus);
        canvas.drawArc(rectF2, 90, 180, true, mPaintWhite);
        canvas.drawArc(rectF1, -90, 180, true, mPaintBlack);
        canvas.drawCircle(0, -radicus / 2, radicus / 8, mPaintWhite);
        canvas.drawCircle(0, radicus / 2, radicus / 8, mPaintBlack);
    }
}
