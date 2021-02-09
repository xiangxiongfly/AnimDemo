package com.example.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

public class MyView extends View {
    public static final int RADIUS = 50;
    private Point currentPoint;
    private Paint mPaint;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.x;
        float y = currentPoint.y;
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void setColor(String color) {
        mPaint.setColor(Color.parseColor(color));
    }

    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth(), getHeight());

        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setColor((String) animation.getAnimatedValue());
                invalidate();
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim).with(anim2);
        animSet.setDuration(5000);
        //速度加快
        //animSet.setInterpolator(new AccelerateInterpolator(2f));
        //弹跳效果
        animSet.setInterpolator(new BounceInterpolator());
        animSet.start();
    }

    public class PointEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            //fraction:动画的完成度
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            int x = (int) (startPoint.x + fraction * endPoint.x - startPoint.x);
            int y = (int) (startPoint.y + fraction * endPoint.y - startPoint.y);
            Point point = new Point(x, y);
            return point;
        }
    }

    public class ColorEvaluator implements TypeEvaluator {
        private int mRed = -1;
        private int mGreen = -1;
        private int mBlue = -1;

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            String startColor = (String) startValue;
            String endColor = (String) endValue;

            int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
            int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
            int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
            int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
            int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
            int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

            // 初始化颜色的值
            if (mRed == -1) {
                mRed = startRed;
            }
            if (mGreen == -1) {
                mGreen = startGreen;
            }
            if (mBlue == -1) {
                mBlue = startBlue;
            }

            // 计算初始颜色和结束颜色之间的差值
            int redDiff = Math.abs(startRed - endRed);
            int greenDiff = Math.abs(startGreen - endGreen);
            int blueDiff = Math.abs(startBlue - endBlue);
            int colorDiff = redDiff + greenDiff + blueDiff;

            if (mRed != endRed) {
                mRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
            } else if (mGreen != endGreen) {
                mGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
            } else if (mBlue != endBlue) {
                mBlue = getCurrentColor(startBlue, endBlue, colorDiff, redDiff + greenDiff, fraction);
            }

            // 将计算出的当前颜色的值组装返回
            String currentColor = "#" + getHexString(mRed) + getHexString(mGreen) + getHexString(mBlue);
            return currentColor;
        }

        /**
         * 根据fraction值来计算当前的颜色。
         */
        private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                    int offset, float fraction) {
            int currentColor;
            if (startColor > endColor) {
                currentColor = (int) (startColor - (fraction * colorDiff - offset));
                if (currentColor < endColor) {
                    currentColor = endColor;
                }
            } else {
                currentColor = (int) (startColor + (fraction * colorDiff - offset));
                if (currentColor > endColor) {
                    currentColor = endColor;
                }
            }
            return currentColor;
        }

        /**
         * 将10进制颜色值转换成16进制。
         */
        private String getHexString(int value) {
            String hexString = Integer.toHexString(value);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            return hexString;
        }
    }
}
