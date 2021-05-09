package com.flatrocktech.mymovies.service.gateway.methods;

import com.flatrocktech.mymovies.BuildConfig;
import com.flatrocktech.mymovies.service.gateway.AbstractAPICall;
import com.flatrocktech.mymovies.service.gateway.ApiInterface;
import com.flatrocktech.mymovies.service.gateway.Callback;
import com.flatrocktech.mymovies.service.gateway.MovieProvider;
import com.flatrocktech.mymovies.service.models.Movie;
import com.flatrocktech.mymovies.service.models.MovieResponse;

import java.util.List;

import retrofit2.Call;

public class GetAllMoviesCall extends AbstractAPICall<MovieResponse> implements MovieProvider {

    @Override
    protected Call<MovieResponse> buildCaller(ApiInterface apiInterface) {
        return apiInterface.getMovies(BuildConfig.TMDB_KEY);
    }

    @Override
    public void getMovies(Callback<List<Movie>> movies) {
        call(new Callback<MovieResponse>() {
            @Override
            public void onSuccess(MovieResponse data) {
                movies.onSuccess(data.getItems());
            }

            @Override
            public void onFailure(int message) {
                movies.onFailure(message);
            }
        });
    }
}
