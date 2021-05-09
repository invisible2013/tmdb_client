package com.flatrocktech.mymovies.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefStorage {

    private String targetFile;

    public SharedPrefStorage(String targetFile) {
        this.targetFile = targetFile;
    }

    public void put(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(Context context, String key) {
        SharedPreferences sharedPreferences = getInstance(context);
        return sharedPreferences.getString(key, null);
    }

    public void remove(Context context, String key) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public boolean exists(Context context, String key) {
        SharedPreferences sharedPreferences = getInstance(context);
        return sharedPreferences.contains(key);
    }

    private SharedPreferences getInstance(Context context) {
        return context.getSharedPreferences(targetFile, Context.MODE_PRIVATE);
    }
}
