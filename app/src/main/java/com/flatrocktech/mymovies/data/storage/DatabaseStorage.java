package com.flatrocktech.mymovies.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flatrocktech.mymovies.service.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseStorage {
    private SQLiteDatabase db;
    private Context context;

    public DatabaseStorage(Context context) {
        this.context = context;
    }

    private void getWritableInstance() {
        SQLiteOpenHelper movieDatabaseHelper = new MovieDatabaseHelper(context);
        db = movieDatabaseHelper.getWritableDatabase();
    }

    private void getReadableInstance() {
        SQLiteOpenHelper movieDatabaseHelper = new MovieDatabaseHelper(context);
        db = movieDatabaseHelper.getReadableDatabase();
    }


    public List<Movie> getAll() {
        getReadableInstance();
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = db.query("FAVORITE",
                new String[]{"MOVIE_ID", "TITLE", "POSTER_PATH"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getString(0));
            movie.setTitle(cursor.getString(1));
            movie.setPosterPath(cursor.getString(2));
            movies.add(movie);
        }
        cursor.close();
        db.close();

        return movies;
    }

    public void setFavorite(Movie movie) {
        getWritableInstance();
        db.beginTransaction();
        ContentValues favorite = new ContentValues();
        favorite.put("MOVIE_ID", movie.getId());
        favorite.put("TITLE", movie.getTitle());
        favorite.put("POSTER_PATH", movie.getPosterPath());
        long result = db.insert("FAVORITE", null, favorite);
        if (result >= 0) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();
        db.close();
    }

    public Movie get(String movieId) {
        getReadableInstance();
        Movie movie = null;
        Cursor cursor = db.query("FAVORITE",
                new String[]{"MOVIE_ID", "TITLE", "POSTER_PATH"},
                "MOVIE_ID = ?",
                new String[]{movieId}, null, null, null);
        if (cursor.moveToFirst()) {
            movie = new Movie();
            movie.setId(cursor.getString(0));
            movie.setTitle(cursor.getString(1));
            movie.setPosterPath(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return movie;
    }

    public void unSetFavorite(String movieId) {
        getWritableInstance();
        db.delete("FAVORITE", "MOVIE_ID = ?", new String[]{movieId});
        db.close();
    }
}
