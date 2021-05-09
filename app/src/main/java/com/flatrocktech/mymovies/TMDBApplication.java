package com.flatrocktech.mymovies;

import android.app.Application;

public class TMDBApplication extends Application {

    public static TMDBApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
