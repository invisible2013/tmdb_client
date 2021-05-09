package com.flatrocktech.mymovies.data.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "movie";
    private static final int DB_VERSION = 1;

    public MovieDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE FAVORITE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "MOVIE_ID TEXT, "
                    + "TITLE TEXT, "
                    + "POSTER_PATH TEXT);");
        }
    }

}
