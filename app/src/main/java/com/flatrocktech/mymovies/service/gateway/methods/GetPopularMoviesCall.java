package com.flatrocktech.mymovies.service.gateway.methods;

import com.flatrocktech.mymovies.BuildConfig;
import com.flatrocktech.mymovies.service.gateway.AbstractAPICall;
import com.flatrocktech.mymovies.service.gateway.ApiInterface;
import com.flatrocktech.mymovies.service.gateway.Callback;
import com.flatrocktech.mymovies.service.gateway.MovieProvider;
import com.flatrocktech.mymovies.service.models.FilteredMovieResponse;
import com.flatrocktech.mymovies.service.models.Movie;

import java.util.List;

import retrofit2.Call;

public class GetPopularMoviesCall extends AbstractAPICall<FilteredMovieResponse> implements MovieProvider {

    @Override
    protected Call<FilteredMovieResponse> buildCaller(ApiInterface apiInterface) {
        return apiInterface.getPopularMovies(BuildConfig.TMDB_KEY);
    }

    @Override
    public void getMovies(Callback<List<Movie>> movies) {
        call(new Callback<FilteredMovieResponse>() {
            @Override
            public void onSuccess(FilteredMovieResponse data) {
                movies.onSuccess(data.getItems());
            }

            @Override
            public void onFailure(int message) {
                movies.onFailure(message);
            }
        });
    }
}
