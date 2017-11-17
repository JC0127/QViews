package com.qi.qviews.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 水平柱状图
 * Created by jings on 2017/11/13.
 */

public class BarChartHorizontalView extends View {

    private List<Integer> dataList;
    private List<String> titleList;
    private List<Integer> colorList;

    private Paint mPaint;
    //最大值
    private int maxNum;

    //x控制点
    private int xPoit;

    //默认高度20px
//    private int barHeigthSize = 20;

    //单位值
    private int miniSize;


    public BarChartHorizontalView(Context context) {
        super(context);
        init();
    }

    public BarChartHorizontalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChartHorizontalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

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
        if (dataList==null)return;
        initVelue();
        mPaint.setColor(Color.BLUE);
        draBar(canvas, mPaint);
        mPaint.setTextSize(miniSize/2);
        mPaint.setColor(Color.RED);
        draTitle(canvas, mPaint);

    }

    /**
     * 初始化数值
     */
    private void initVelue() {
        xPoit = getWidth() / 5;
        miniSize = getHeight() / dataList.size();
    }

    /**
     * 计算item数值
     * @param data
     * @return
     */
    private int getItemCount(int data) {
        int lenthData = 0;
        lenthData = (int) ((float) data / maxNum * getWidth() / 2);
        Log.e("ccccsize00", lenthData + " * " + maxNum + " data " + data);
        return lenthData;
    }

    /**
     * 绘制柱状图
     * @param canvas
     * @param paint
     */
    private void draBar(Canvas canvas, Paint paint) {
        if (dataList == null) return;
        if (colorList!=null){
            for (int i = 0; i < dataList.size(); i++) {
                paint.setColor(colorList.get(i));
                canvas.drawRect(xPoit,   miniSize*i+miniSize/3, getWidth() / 3 + getItemCount(dataList.get(i)),miniSize+miniSize*i,paint);

//                canvas.drawRect(xPoit, miniSize+miniSize*i, getWidth() / 3 + getItemCount(dataList.get(i)), miniSize*i+miniSize/3, paint);

            }
        }else {
            for (int i = 0; i < dataList.size(); i++) {
                canvas.drawRect(xPoit,   miniSize*i+miniSize/3, getWidth() / 3 + getItemCount(dataList.get(i)),miniSize+miniSize*i,paint);

//                canvas.drawRect(xPoit, miniSize+miniSize*i, getWidth() / 3 + getItemCount(dataList.get(i)), miniSize*i+miniSize/3, paint);
            }
        }

    }

    /***
     * 绘制文本
     * @param canvas
     * @param paint
     */
    private void draTitle(Canvas canvas, Paint paint) {
        if (dataList == null) return;
        for (int i = 0; i < titleList.size(); i++) {
            int lenthTest = 0;
            lenthTest = titleList.get(i).length() * miniSize/2;
            paint.setColor(Color.GRAY);
            canvas.drawText(titleList.get(i), xPoit - lenthTest-10, miniSize*4/5+miniSize*i, paint);
            paint.setColor(Color.GREEN);
            canvas.drawText(dataList.get(i)+"",getWidth() / 3 + getItemCount(dataList.get(i))+10, miniSize*4/5+miniSize*i, paint);
        }
    }

    /**
     * 外部传值
     * @param dataList
     * @param titleList
     */
    public void setData(List<Integer> dataList, List<String> titleList) {
        this.dataList = dataList;
        this.titleList = titleList;
        if (dataList.size() > titleList.size()) {
//            Log.e("titleSize", dataList.size() + " * " + titleList.size());
            int size=dataList.size() - titleList.size();
            for (int i = 0; i <size; i++) {
                titleList.add("未知");
            }
        }
        for (int i = 0; i < dataList.size(); i++) {
            if (maxNum < dataList.get(i)) {
                maxNum = dataList.get(i);
            }
        }

    }
    public void setColor(List<Integer> colorList){
        this.colorList=colorList;
        if (colorList.size()<dataList.size()){
            int i = dataList.size() - colorList.size();
            for (int j = 0; j < i; j++) {
                colorList.add(Color.GREEN);
            }
        }
    }
}
