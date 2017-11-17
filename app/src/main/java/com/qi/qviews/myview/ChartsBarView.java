package com.qi.qviews.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 饼状图
 * Created by jings on 2017/11/8.
 */

public class ChartsBarView extends View {
    //数值（外界传入）
    private List<Integer> dataList;
    //颜色值
    private List<Integer> colorsList;

    //半径
    private int circularR;
    //画笔
    private Paint mPaint;

    //圆心坐标
    private int circularX, circularY;
    //总量
    private int numAll;

    private RectF mRectF;

    //矩形控制点
    private int lX, tY, rX, bY;



    public ChartsBarView(Context context) {
        super(context);
        init();
    }

    public ChartsBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartsBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化工具
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int heigth = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, heigth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initData();
        mPaint.setColor(Color.GRAY);
        drawCircles(canvas, mPaint, mRectF);

    }

    /**
     *扇形
     * @param canvas
     * @param paint
     */
    private void drawCircles(Canvas canvas, Paint paint, RectF rectF) {
        Log.e("the xy", "x " + circularX + "y " + circularY + " r" + circularR);
//        canvas.drawCircle(circularX, circularY, circularR+2, paint);
        float satrt=0;
        float end=0;
        if (dataList==null)return;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.size()>colorsList.size()||colorsList==null){
                end+=getCirculars(dataList.get(i));
//                paint.setColor(colorsList.get(i));
                canvas.drawArc(rectF, satrt, getCirculars(dataList.get(i)), true, paint);
                Log.e("satrt",satrt+" * "+end);
                satrt=end;
                if (i%2==0){
                    paint.setColor(Color.BLUE);
                }else{
                    paint.setColor(Color.RED);
                }
            }else {
                end+=getCirculars(dataList.get(i));
                paint.setColor(colorsList.get(i));
                canvas.drawArc(rectF, satrt, getCirculars(dataList.get(i)), true, paint);
                Log.e("satrt",satrt+" * "+end);
                satrt=end;
            }



        }

//        canvas.drawRect(f,paint);
    }

    /**
     * 计算弧度
     *
     * @param data
     * @return
     */
    private float getCirculars(int data) {
        float a = 0l;
        if (dataList != null) {
            a = (float) data / numAll * 360;
        }
        Log.e("Circulars",a+"");
        return a;
    }

    /**
     * 初始化数值
     */
    private void initData() {
        circularY = getHeight() / 2;
        circularX = getWidth() / 2;
        if (getWidth() > getHeight()) {
            circularR = getHeight() / 6 * 2;
            tY = 30;
            lX = (getWidth() - getHeight()) / 2 + tY;
            rX = getWidth() - (getWidth() - getHeight()) / 2 - tY;
            bY = getHeight() - tY;
        } else if (getWidth() < getHeight()) {
            circularR = getWidth() / 6 * 2;
            lX = 30;
            tY = (getHeight() - getWidth()) / 2 + lX;
            bY = getHeight() - (getHeight() - getWidth()) / 2 - lX;
            rX = getWidth() - lX;
        } else if (getWidth() == getHeight()) {
            circularR = getWidth() / 6 * 2;
            lX = 30;
            tY = lX;
            bY = getHeight() - lX;
            rX = getWidth() - lX;
        }
        mRectF = new RectF(lX, tY, rX, bY);

    }

    /**
     * 外界传值
     *
     * @param dataList
     * @param colorList
     * @return
     */
    public ChartsBarView setData(List<Integer> dataList, List<Integer> colorList) {
        this.dataList = dataList;
        this.colorsList = colorList;
        for (int i = 0; i < dataList.size(); i++) {
            numAll += dataList.get(i);

        }



        Log.e("data", numAll + "");
        return this;
    }

}
