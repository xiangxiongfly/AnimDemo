package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.myapplication.interpolator.InterpolatorActivity;

import static android.animation.ValueAnimator.INFINITE;
import static com.example.myapplication.Constant.*;

public class AnimatorActivity extends AppCompatActivity {
    private ImageView imageView;
    private int rawLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        imageView = findViewById(R.id.imageView);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                rawLeft = imageView.getLeft();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_animator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_translation:
                translation();
                break;
            case R.id.opt_scale:
                scale();
                break;
            case R.id.opt_rotation:
                rotaion();
                break;
            case R.id.opt_alpha:
                alpha();
                break;
            case R.id.opt_set:
                set();
                break;
            case R.id.opt_value_animator:
                startValueAnimator();
            case R.id.opt_type_interpolator:
                startActivity(new Intent(this, InterpolatorActivity.class));
                break;
            case R.id.opt_type_evaluator:
                startTypeEvaluator();
                break;
            case R.id.opt_myview:
                startActivity(new Intent(this, MyViewActivity.class));
                break;
            case R.id.opt_view_property:
                startViewPropertyAnimator();
                break;
        }
        return true;
    }

    private void translation() {
        float distance = imageView.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, TRANSLATION_X, distance, 500, distance);
        animator.setDuration(2000L);
        animator.setRepeatCount(3);
        animator.start();
    }

    private void scale() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, SCALE_Y, 1f, 3f, 1f);
        animator.setDuration(2000L);
        animator.start();
    }

    //旋转动画
    private void rotaion() {
        //旋转3次
//        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ROTATION, 0f, 360f);
//        animator.setDuration(2000L);
//        animator.setRepeatCount(3);
//        animator.start();

        //匀速一直旋转
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ROTATION, 0f, 360f);
        animator.setDuration(2000L);
        animator.setRepeatCount(INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void alpha() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ALPHA, 1f, 0f, 1f);
        animator.setDuration(2000L);
        animator.start();
    }

    private void set() {
        float distance = imageView.getTranslationX();
        ObjectAnimator translation = ObjectAnimator.ofFloat(imageView, TRANSLATION_X, distance, 500, distance);
        ObjectAnimator scale = ObjectAnimator.ofFloat(imageView, SCALE_Y, 1f, 3f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, ROTATION, 0f, 360f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, ALPHA, 1f, 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        //1.同时执行动画一
//        animatorSet.play(translation).with(scale).with(rotation).with(alpha);
        //2.同时执行动画二
//        animatorSet.playTogether(translation, scale, rotation, alpha);
        //3.顺序执行动画
//        animatorSet.playSequentially(translation, scale, rotation, alpha);
        //4.指定顺序执行动画：rotation->translation->scale
        animatorSet.play(translation).before(scale).after(rotation);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    //向右移动500
    private void startValueAnimator() {
        int distance = 500;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
//                Log.e("TAG", "value:" + value);
                moveView(imageView, value, distance);
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void moveView(View targetView, float value, int distance) {
        int left = rawLeft + (int) (value * distance);
        Log.e("TAG", "left: " + (value * distance));
        int top = targetView.getTop();
        int right = left + targetView.getWidth();
        int bottom = top + targetView.getHeight();
        targetView.layout(left, top, right, bottom);
    }

    private void startTypeEvaluator() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new FloatEvaluator(), 0f, 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.e("TAG", "value:" + value);
            }
        });
        valueAnimator.start();
    }

    public class FloatEvaluator implements TypeEvaluator {
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            float startFloat = ((Number) startValue).floatValue();
            return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);
        }
    }

    private void startViewPropertyAnimator() {
        //透明度动画
//        imageView.animate().alpha(0.5f).setDuration(2000L);
//        ViewCompat.animate(imageView).alpha(0.5f);

        //平移动画
//        imageView.animate().translationX(500f).setDuration(2000L);

        //旋转动画
//        imageView.animate().rotation(360).setDuration(2000L);

        //缩放动画
        imageView.animate().scaleX(1.5F).setDuration(2000L);
    }

}