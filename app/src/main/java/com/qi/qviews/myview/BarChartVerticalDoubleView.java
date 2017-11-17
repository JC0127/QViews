package com.qi.qviews.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图（组）
 * Created by jings on 2017/11/16.
 */

public class BarChartVerticalDoubleView extends View {
    private Paint mPaint;
    //坐标的xy 0.0
    private float xPonit, yPonit;
    private float titleSpace;
    //柱宽
    private float barSpace;
    //设置的数值
    private List<int[]> dataLists;
    private List<String> xTltie;
    private List<Integer> colorList;

    //最大数值
    private int maxData;

    //柱长单位比值
    private float minUnit;

    //平分段
    private int barSize;

    private boolean IS_TOP_SHOW = false;
    private boolean IS_Y_SHOW = false;

    public BarChartVerticalDoubleView(Context context) {
        super(context);
        init();
    }

    public BarChartVerticalDoubleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChartVerticalDoubleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        if (dataLists == null) return;
        initValue();

        draBar(canvas, mPaint);
        draXy(canvas, mPaint);
        draXtltie(canvas, mPaint);
        draTopNum(canvas, mPaint);
        draY(canvas, mPaint);
    }

    //初始化数值
    private void initValue() {
        if (getWidth() < getHeight()) {
            titleSpace = getWidth() / 8;
            xPonit = titleSpace;
            yPonit = getHeight() - titleSpace;
        } else if (getHeight() < getWidth()) {
            titleSpace = getHeight() / 8;
            xPonit = titleSpace;
            yPonit = getHeight() - titleSpace;
        } else if (getWidth() == getHeight()) {
            titleSpace = getHeight() / 8;
            xPonit = titleSpace;
            yPonit = getHeight() - titleSpace;
        }

        barSpace = (getWidth() - 2 * titleSpace) / (dataLists.get(0).length + 1) / barSize;
        for (int i = 0; i < dataLists.size(); i++) {
            for (int j = 0; j < dataLists.get(i).length; j++) {
                if (maxData < dataLists.get(i)[j]) {
                    maxData = dataLists.get(i)[j];
                }
            }
            minUnit = (getHeight() - 2 * titleSpace) / maxData;
        }
        if (colorList == null) {
            colorList = new ArrayList<>();
            colorList.add(0xFF26C6A5);
            colorList.add(0xFFFB4852);
            colorList.add(0xFFFAC356);
            colorList.add(0xFF0189B6);
            colorList.add(0xFF1FD69B);
            colorList.add(0xFF1FD69B);
            colorList.add(0xFF1FD69B);
            colorList.add(0xFF1FD69B);
        }
    }

    /**
     * 绘制xy轴
     *
     * @param canvas
     * @param paint
     */
    private void draXy(Canvas canvas, Paint paint) {
        Paint xyP = new Paint(paint);
        xyP.setColor(Color.GRAY);
        xyP.setStrokeWidth(4);
//        Log.e("size123",xPonit+" . "+yPonit+" * "+xPonit+" . "+(getHeight()-titleSpace));
        //Y轴
        canvas.drawLine(xPonit, yPonit, xPonit, titleSpace / 2, xyP);
        canvas.drawLine(xPonit, titleSpace / 2, xPonit - 8, titleSpace / 2 + 18, xyP);
        canvas.drawLine(xPonit, titleSpace / 2, xPonit + 8, titleSpace / 2 + 18, xyP);

        //X轴
        canvas.drawLine(xPonit - 2, yPonit, getWidth() - titleSpace / 2 + 2, getHeight() - titleSpace, xyP);
        canvas.drawLine(getWidth() - titleSpace / 2 + 2, getHeight() - titleSpace, getWidth() - titleSpace / 2 - 16, getHeight() - titleSpace + 8, xyP);
        canvas.drawLine(getWidth() - titleSpace / 2 + 2, getHeight() - titleSpace, getWidth() - titleSpace / 2 - 16, getHeight() - titleSpace - 8, xyP);
    }

    /**
     * 柱状图
     *
     * @param canvas
     * @param paint
     */
    private void draBar(Canvas canvas, Paint paint) {
        Paint barP = new Paint(paint);
        if (dataLists != null) {
            for (int i = 0; i < dataLists.size(); i++) {
                for (int j = 0; j < dataLists.get(i).length; j++) {

                    barP.setColor(colorList.get(j));

//                    if (j % 2 == 0) {
//                        barP.setColor(0xff40B6E0);
//                    } else {
//                        barP.setColor(Color.RED);
//                    }
                    canvas.drawRect(barSpace * (dataLists.get(i).length + 1) * i + titleSpace + barSpace + barSpace * j + 2, getHeight() - titleSpace - dataLists.get(i)[j] * minUnit, barSpace * (dataLists.get(i).length + 1) * i + titleSpace + barSpace + barSpace * (j + 1), getHeight() - titleSpace, barP);
                }
            }
        }
    }

    /**
     * x 文字
     *
     * @param canvas
     * @param paint
     */
    private void draXtltie(Canvas canvas, Paint paint) {
        Paint paintText = new Paint(paint);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(Color.GRAY);

        if (xTltie == null) return;
        float textUnit = (getWidth() - 2 * titleSpace) / barSize;
        paintText.setTextSize(titleSpace / 2);
        for (int i = 0; i < xTltie.size(); i++) {
            canvas.drawText(xTltie.get(i), titleSpace + barSpace + textUnit * i + (textUnit - barSpace) / 2, getHeight() - titleSpace / 2, paintText);
        }
    }

    /**
     * 数值
     *
     * @param canvas
     * @param paint
     */
    private void draTopNum(Canvas canvas, Paint paint) {
        if (!IS_TOP_SHOW) return;
        Paint paintNum = new Paint(paint);
        paintNum.setTextSize(barSpace * 2 / 3);
        for (int i = 0; i < dataLists.size(); i++) {
            for (int j = 0; j < dataLists.get(i).length; j++) {
                canvas.drawText(dataLists.get(i)[j] + "", barSpace * (dataLists.get(i).length + 1) * i + titleSpace + barSpace + barSpace * j + 2, getHeight() - titleSpace - dataLists.get(i)[j] * minUnit - 4, paintNum);
            }

        }
    }

    /**
     * Y坐标轴
     *
     * @param canvas
     * @param paint
     */
    private void draY(Canvas canvas, Paint paint) {
        if (!IS_Y_SHOW) return;
        Paint paintY = new Paint(paint);
        paintY.setTextSize(barSpace * 2 / 3);
        paintY.setTextAlign(Paint.Align.RIGHT);
        float he = (getHeight() - titleSpace*2) / 10;
        int size=(int)Math.rint(maxData/10);

            for (int i = 0; i < 10; i++) {
                canvas.drawText(i * maxData/10+ "", xPonit*3/4, getHeight() - titleSpace - he * i, paintY);
            }

    }

    /**
     * 赋值
     *
     * @param dataLists
     */
    public BarChartVerticalDoubleView setData(List<int[]> dataLists) {
        this.dataLists = dataLists;
        barSize = dataLists.size();
        return this;
    }

    public void setColor(List<Integer> colorList) {
        this.colorList = colorList;
        if (colorList.size() < dataLists.get(0).length) {
            int a = dataLists.get(0).length - colorList.size();
            for (int i = 0; i < a; i++) {
                colorList.add(0xFF26C6A5);
            }
        }
    }

    public BarChartVerticalDoubleView setShowNum(boolean isShow) {
        IS_TOP_SHOW = isShow;
        return this;
    }

    public BarChartVerticalDoubleView setShowY(boolean isShow) {
        IS_Y_SHOW = isShow;
        return this;
    }

    /**
     * 赋值 x title
     *
     * @param xTitle
     * @return
     */
    public void setTitleX(List<String> xTitle) {
        this.xTltie = xTitle;
        if (dataLists.size() > xTltie.size()) {
            int a = dataLists.size() - xTitle.size();
            for (int i = 0; i < a; i++) {
                xTitle.add("");
            }
        }
    }


}
