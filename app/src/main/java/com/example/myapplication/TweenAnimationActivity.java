package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class TweenAnimationActivity extends AppCompatActivity {

    private ImageView logo;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_animation);
        context = this;
        initVews();
    }

    private void initVews() {
        logo = findViewById(R.id.logo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_xml_translate:
                translateByXML();
                break;
            case R.id.opt_java_translate:
                translateByJava();
                break;
            case R.id.opt_xml_scale:
                scaleByXML();
                break;
            case R.id.opt_java_scale:
                scaleByJava();
                break;
            case R.id.opt_xml_rotate:
                rotateByXML();
                break;
            case R.id.opt_java_rotate:
                rotateByJava();
                break;
            case R.id.opt_xml_alpha:
                alphaByXML();
                break;
            case R.id.opt_java_alpha:
                alphaByJava();
                break;
            case R.id.opt_xml_set:
                setByXML();
                break;
            case R.id.opt_java_set:
                setByJava();
                break;
        }
        return true;
    }

    private void translateByXML() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_translate);
        logo.startAnimation(animation);
    }

    private void translateByJava() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f
        );
        animation.setDuration(2000);
        logo.startAnimation(animation);
    }

    private void scaleByXML() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale);
        logo.startAnimation(animation);
    }

    private void scaleByJava() {
        ScaleAnimation animation = new ScaleAnimation(
                0.5f, 1.5f, 0.5f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(2000);
        logo.startAnimation(animation);
    }

    private void rotateByXML() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate);
        logo.startAnimation(animation);
    }

    private void rotateByJava() {
        RotateAnimation animation = new RotateAnimation(
                0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 05f
        );
        animation.setRepeatCount(3);
        animation.setDuration(2000);
        logo.startAnimation(animation);
    }

    private void alphaByXML() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
        logo.startAnimation(animation);
    }

    private void alphaByJava() {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.1f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(2000);
        logo.startAnimation(animation);
    }


    private void setByXML() {
        logo.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_set);
        logo.setAnimation(animation);
    }

    private void setByJava() {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation anim1 = new ScaleAnimation(
                0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        RotateAnimation anim2 = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation anim3 = new AlphaAnimation(0f, 1f);
        animationSet.addAnimation(anim1);
        animationSet.addAnimation(anim2);
        animationSet.addAnimation(anim3);
        animationSet.setDuration(2000);
        logo.startAnimation(animationSet);
    }

}