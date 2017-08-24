package com.orangesunshine.ndk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class MyView extends View {
    private Paint mPaint;
    /**
     * 0~100:green
     * 101~200:yellow
     * 201~300:red
     */
    private int bottom;

    public void setBottomValue(int bottom) {
        this.bottom = bottom;
    }

    public MyView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setTextSize(30);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int color = getColor(bottom);
        mPaint.setColor(color);
        if (bottom > 250) {
            canvas.drawText("要爆炸了，快跑！", 0, 50, mPaint);
        } else {
            canvas.drawRect(50, 10, 100, bottom*2 + 10, mPaint);
        }
    }

    private int getColor(int bottom) {
        if (bottom <= 100 && bottom > 0) {
            return Color.GREEN;
        } else if (bottom <= 200 && bottom > 101) {
            return Color.YELLOW;
        }
        return Color.RED;
    }
}
