package com.flatrocktech.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.flatrocktech.mymovies.data.MovieServiceDBImpl;
import com.flatrocktech.mymovies.data.MoviesService;
import com.flatrocktech.mymovies.service.gateway.ApiClient;
import com.flatrocktech.mymovies.service.gateway.Callback;
import com.flatrocktech.mymovies.service.gateway.methods.GetMovieCall;
import com.flatrocktech.mymovies.service.models.Movie;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mPoster;
    private TextView mTitle;
    private TextView mOriginalTitle;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;
    private Movie mSelectedMovie;
    private ImageView mFavorite;

    private MoviesService moviesService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moviesService = new MovieServiceDBImpl();
        mPoster = findViewById(R.id.movie_detail_poster);
        mTitle = findViewById(R.id.movie_detail_title);
        mOriginalTitle = findViewById(R.id.movie_detail_original_title);
        mReleaseDate = findViewById(R.id.movie_detail_release_date);
        mRating = findViewById(R.id.movie_detail_rating);
        mOverview = findViewById(R.id.movie_detail_overview);
        mFavorite = findViewById(R.id.movie_detail_favorite_button);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra(MovieAdapter.MOVIE_ID);
        getMovieDetails(Long.parseLong(movieId));

        if (moviesService.isFavourite(movieId)) {
            mFavorite.setOnClickListener(this::unSetFavorite);
            DrawableCompat.setTint(mFavorite.getDrawable(),
                    ContextCompat.getColor(this, R.color.light_green));
        } else {
            mFavorite.setOnClickListener(this::setFavorite);
        }
    }


    private void getMovieDetails(long movieId) {
        GetMovieCall getMovieCall = new GetMovieCall();
        getMovieCall.setMovieId(movieId);
        getMovieCall.call(new Callback<Movie>() {
            @Override
            public void onSuccess(Movie movie) {
                generateDetailInfo(movie);
            }

            @Override
            public void onFailure(int message) {
                Toast.makeText(MovieDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDetailInfo(Movie movie) {
        mSelectedMovie = movie;
        mTitle.setText(movie.getTitle());
        mOriginalTitle.setText(movie.getOriginalTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mRating.setText(movie.getVoteAverage());
        mOverview.setText(movie.getOverview());
        mRating.setText(String.format("%s/10", movie.getVoteAverage()));

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build()
                .load(ApiClient.getImageFullPath(movie.getPosterPath()))
                .into(mPoster);
    }

    public void setFavorite(View view) {
        moviesService.setFavourite(mSelectedMovie);
        DrawableCompat.setTint(mFavorite.getDrawable(),
                ContextCompat.getColor(this, R.color.light_green));
        mFavorite.setOnClickListener(this::unSetFavorite);
        MoviesFragment.MANUAL_UPDATE = true;
    }

    public void unSetFavorite(View view) {
        moviesService.unsetFavourite(mSelectedMovie.getId());
        DrawableCompat.setTint(mFavorite.getDrawable(),
                ContextCompat.getColor(this, R.color.material_on_background_disabled));
        mFavorite.setOnClickListener(this::setFavorite);
        MoviesFragment.MANUAL_UPDATE = true;
    }
}
