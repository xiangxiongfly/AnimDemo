package com.example.myapplication.interpolator;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Constant;
import com.example.myapplication.R;

import java.util.ArrayList;


public class InterpolatorActivity extends AppCompatActivity {
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<Interpolator> interpolators = new ArrayList<Interpolator>() {
        {
            add(new LinearInterpolator());//匀速
            add(new DecelerateInterpolator());//减速
            add(new AccelerateInterpolator());//加速
            add(new OvershootInterpolator());//快速完成动画，超出再回到结束样式
            add(new AccelerateDecelerateInterpolator());//先加速再减速
            add(new AnticipateInterpolator());//先退后再加速前进
            add(new AnticipateOvershootInterpolator());//先退后再加速前进，超出终点后再回终点
            add(new BounceInterpolator());//最后阶段回弹效果
            add(new CycleInterpolator(3));//周期运动
            add(new MyInterpolator());//自定义差值器
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);
        LinearLayout container = findViewById(R.id.container);
        for (int i = 0; i < container.getChildCount(); i++) {
            textViews.add((TextView) container.getChildAt(i));
        }

        getWindow().getDecorView().findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    private void startAnim() {
        for (int i = 0; i < textViews.size(); i++) {
            TextView textView = textViews.get(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(textView, Constant.TRANSLATION_X, 0, 1000F);
            animator.setDuration(5000L);
            animator.setInterpolator(interpolators.get(i));
            animator.start();
        }
    }
}