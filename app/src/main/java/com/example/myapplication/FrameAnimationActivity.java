package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FrameAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;
    private Button btnStop;
    private ImageView imgAnim;
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        initViews();
        anim = (AnimationDrawable) imgAnim.getDrawable();
    }

    private void initViews() {
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        imgAnim = findViewById(R.id.imgAnim);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                anim.start();
                break;
            case R.id.btnStop:
                anim.stop();
                break;
        }
    }
}