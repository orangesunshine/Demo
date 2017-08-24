package com.orange.demo.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.orange.demo.utils.LogUtil;


/**
 * Created by zhanglei on 2017/6/8
 */

public class HeartProgressBar extends View{
    //默认的进度未到达的颜色
    private final static int DEFAULT_UNREACH_COLOR=0xff69b4;
    //默认的进度达到的颜色。
    private final static int DEFAULT_REACH_COLOR=0xff1493;

    private int unreachColor;
    private int reachColor;


    private final static int UNREACHEDCOLOR_DEFAULT = 0xff888888;
    private final static int REACHEDCOLOR_DEFAULT = 0xFFFF1493;
    private final static int INNERTEXTCOLOR_DEFAULT = 0xFFDC143C;
    private final static int INNERTEXTSIZE_DEFAULT = 10;
    private static final int PROGRESS_DEFAULT = 0;
    private int unReachedColor;
    private int reachedColor;
    private int innerTextColor;
    private int innerTextSize;
    private int progress;
    private int realWidth;
    private int realHeight;
    private Paint underPaint;
    private Paint textPaint;
    private Paint test;
    private Path path;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private ArgbEvaluator argbEvaluator;


    public HeartProgressBar(Context context) {
        this(context,null);
    }

    public HeartProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeartProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        argbEvaluator = new ArgbEvaluator();
        unReachedColor=UNREACHEDCOLOR_DEFAULT;
        reachedColor = REACHEDCOLOR_DEFAULT;
        innerTextColor = INNERTEXTCOLOR_DEFAULT;
        innerTextSize = INNERTEXTSIZE_DEFAULT;
        progress = PROGRESS_DEFAULT;

        //声明区
        underPaint = new Paint();
        textPaint = new Paint();
        test=new Paint();
        path = new Path();
        //构造画笔区
        underPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        PorterDuffXfermode porterDuffXfermode=new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
        test.setXfermode(porterDuffXfermode);
        test.setColor(0xffff0000);
        test.setStyle(Paint.Style.FILL_AND_STROKE);
        underPaint.setStrokeWidth(5.0f);
        textPaint.setColor(innerTextColor);
        textPaint.setTextSize(innerTextSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }



    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int usedHeight = getRealHeight(heightMeasureSpec);
        int usedWidth = getRealWidth(widthMeasureSpec);
        LogUtil.log("usedWidth="+usedWidth+"   usedHeight="+usedHeight);
        setMeasuredDimension(usedWidth,usedHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        realWidth = w;
        realHeight = h;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        float pro = ((float)progress)/100.0f;
        LogUtil.log("pro"+pro);
        int nowColor = (int) argbEvaluator.evaluate(pro,unReachedColor,reachedColor);
        underPaint.setColor(0xff888888);
        path.moveTo((float) (0.5*realWidth), (float) (0.20*realHeight));
        path.cubicTo((float) (0.15*realWidth), (float) (-0.35*realHeight), (float) (-0.4*realWidth), (float) (0.45*realHeight), (float) (0.5*realWidth),realHeight);
        path.moveTo((float) (0.5*realWidth),realHeight);
        path.cubicTo((float) (realWidth+0.4*realWidth), (float) (0.45*realHeight),(float) (realWidth-0.15*realWidth), (float) (-0.35*realHeight),(float) (0.5*realWidth), (float) (0.20*realHeight));
        path.close();
        canvas.drawPath(path,underPaint);
        canvas.save();
        canvas.drawRect(0,0,realWidth,0.5f*realHeight,test);

        canvas.drawText(String.valueOf(progress),realWidth/2,realHeight/2,textPaint);
    }


    public int getRealHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightVal = MeasureSpec.getSize(heightMeasureSpec);
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        if(heightMode == MeasureSpec.EXACTLY){
            return paddingTop + paddingBottom + heightVal;
        }else if(heightMode == MeasureSpec.UNSPECIFIED){
            return (int) (Math.abs(underPaint.ascent()-underPaint.descent()) + paddingTop + paddingBottom);
        }else{
            return (int) Math.min((Math.abs(underPaint.ascent()-underPaint.descent()) + paddingTop + paddingBottom),heightVal);
        }
    }

    public int getRealWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        if(widthMode == MeasureSpec.EXACTLY){
            return paddingLeft+paddingRight+widthVal;
        }else if(widthMode == MeasureSpec.UNSPECIFIED){
            return (int) (Math.abs(underPaint.ascent()-underPaint.descent()) + paddingLeft + paddingRight);
        }else{
            return (int) Math.min((Math.abs(underPaint.ascent()-underPaint.descent()) + paddingLeft + paddingRight),widthVal);
        }
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }



}
