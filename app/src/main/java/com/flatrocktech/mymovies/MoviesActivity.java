package com.flatrocktech.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.flatrocktech.mymovies.service.ApiClient;
import com.flatrocktech.mymovies.service.ApiInterface;
import com.flatrocktech.mymovies.service.models.Movie;
import com.flatrocktech.mymovies.service.models.MovieResponse;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {

    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        progressDialog = new ProgressDialog(MoviesActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        getMovies();
    }

    public void getMovies() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiInterface.getMovies(BuildConfig.TMDB_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MoviesActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(MovieResponse response) {
        recyclerView = findViewById(R.id.movies_recycler_view);
        movieList = response.getItems();
        adapter = new MovieAdapter(movieList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MoviesActivity.this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}