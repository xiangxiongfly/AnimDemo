package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFrameAnimation;
    private Button btnTweenAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnFrameAnimation = findViewById(R.id.btnFrameAnimation);
        btnTweenAnimation = findViewById(R.id.btnTweenAnimation);

        btnFrameAnimation.setOnClickListener(this);
        btnTweenAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFrameAnimation:
                startActivity(new Intent(this, FrameAnimationActivity.class));
                break;
            case R.id.btnTweenAnimation:
                startActivity(new Intent(this, TweenAnimationActivity.class));
                break;
        }
    }
}