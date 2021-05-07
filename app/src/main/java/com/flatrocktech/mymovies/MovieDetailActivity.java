package com.flatrocktech.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flatrocktech.mymovies.service.ApiClient;
import com.flatrocktech.mymovies.service.ApiInterface;
import com.flatrocktech.mymovies.service.models.Movie;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mPoster;
    private TextView mTitle;
    private TextView mOriginalTitle;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPoster = findViewById(R.id.movie_detail_poster);
        mTitle = findViewById(R.id.movie_detail_title);
        mOriginalTitle = findViewById(R.id.movie_detail_original_title);
        mReleaseDate = findViewById(R.id.movie_detail_release_date);
        mRating = findViewById(R.id.movie_detail_rating);
        mOverview = findViewById(R.id.movie_detail_overview);
        Intent intent = getIntent();
        String movieId = intent.getStringExtra(MovieAdapter.MOVIE_ID);
        getMovieDetails(Long.parseLong(movieId));
    }


    private void getMovieDetails(long movieId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiInterface.getMovie(movieId, BuildConfig.TMDB_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                generateDetailInfo(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDetailInfo(Movie movie) {
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
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(mPoster);
    }
}
