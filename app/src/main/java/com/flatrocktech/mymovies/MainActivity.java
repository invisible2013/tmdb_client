package com.flatrocktech.mymovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {

    static int SPLASH_TIME_OUT_IN_MILLIS = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MoviesActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT_IN_MILLIS);
    }
}
