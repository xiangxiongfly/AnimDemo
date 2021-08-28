package com.example.myapplication.interpolator;

import android.util.Log;
import android.view.animation.Interpolator;

public class MyInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        Log.e("TAG", "input: " + input);
        input = input - 0.5f;
        return (float) Math.sin(60 * input) + 0.5f;
    }
}
