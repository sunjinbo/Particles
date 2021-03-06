package com.particles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CanvasView extends View implements Runnable {
    private static final int MAX_PARTICLES_NUM = 300;
    private static final long FRAME_INTERVAL_TIME = 1000L / 16;

    private Paint mPaint;
    private List<Particle> mParticleList;
    private Random mRandom = new Random();
    private Path mPath;
    private PathMeasure mPathMeasure;

    public CanvasView(Context context) {
        super(context);
        initView(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    public void run() {
        while (true) {
            long startTime = SystemClock.elapsedRealtime();
            for (int i = 0; i < mParticleList.size(); ++i) {
                mParticleList.get(i).step();
            }
            invalidate();

            long calcTime = SystemClock.elapsedRealtime() - startTime;
            long delayTime = FRAME_INTERVAL_TIME - (long)(calcTime / 1000F);
            if (delayTime > 0L) {
                SystemClock.sleep(delayTime);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (int i = 0; i < mParticleList.size(); ++i) {
            mParticleList.get(i).draw(canvas, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float centerX = w / 2f;
        float centerY = h / 2f;
        float radius = Math.min(w / 4f, h / 4f);

        mPath.addCircle(centerX, centerY, radius, Path.Direction.CCW);
        mPathMeasure.setPath(mPath, true);

        float[] pos = new float[2];
        float[] tan = new float[2];

        mParticleList.clear();
        for (int i = 0; i < MAX_PARTICLES_NUM; ++i) {
            mPathMeasure.getPosTan((float)i / (float)MAX_PARTICLES_NUM * mPathMeasure.getLength(), pos, tan);
            float x = pos[0] + mRandom.nextInt(6) - 3f; // X值随机偏移
            float y =  pos[1] + mRandom.nextInt(6) - 3f; // Y值随机偏移
            float speed = mRandom.nextInt(10) + 5;
            float angle = (float) getAngle(x, y, centerX, centerY, radius);
            Particle p = new Particle(x, y, 3, angle, speed, Color.RED);
            mParticleList.add(p);
        }
    }

    private void initView(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mParticleList = new ArrayList<>();
        mPath = new Path();
        mPathMeasure = new PathMeasure();
        new Thread(this).start();
    }

    private double getAngle(float x, float y, float centerX, float centerY, float radius) {
        double angle = 0F;
        if (x - centerX > 0F && y - centerY == 0F) { // 在x轴的正向轴上
            angle = 0F;
        } else if (x - centerX > 0F && y - centerY > 0F) { // 在第一象限
            angle = Math.acos(((x - centerX) / radius)); // 反余弦函数可以得到弧度值
        } else if (x - centerX == 0f && y - centerY < 0F) { // 在y轴的正向轴上
            angle = Math.PI / 2f;
        } else if (x - centerX < 0F && y - centerY > 0F) { // 在第二象限
            angle = Math.PI - Math.acos(((centerX - x) / radius)); // 反余弦函数可以得到弧度值
        } else if (x - centerX < 0F && y - centerY == 0F) { // 在x轴的负向轴上
            angle = Math.PI;
        } else if (x - centerX < 0F && y - centerY < 0F) { // 在第三象限
            angle = Math.PI + Math.acos(((centerX - x) / radius)); // 反余弦函数可以得到弧度值
        } else if (x - centerX == 0f && y - centerY > 0F) { // 在y轴的负向轴上
            angle = Math.PI * 1.5F;
        } else if (x - centerX > 0F && y - centerY < 0F) { // 在第四象限
            angle = Math.PI * 2F - Math.acos(((x - centerX) / radius)); // 反余弦函数可以得到弧度值
        }

        return angle;
    }
}
