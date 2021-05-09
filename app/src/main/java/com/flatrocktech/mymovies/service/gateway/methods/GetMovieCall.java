package com.flatrocktech.mymovies.service.gateway.methods;

import com.flatrocktech.mymovies.service.gateway.AbstractAPICall;
import com.flatrocktech.mymovies.service.gateway.ApiInterface;
import com.flatrocktech.mymovies.service.models.Movie;

import retrofit2.Call;

public class GetMovieCall extends AbstractAPICall<Movie> {

    private long movieId;

    @Override
    protected Call<Movie> buildCaller(ApiInterface apiInterface) {
        return apiInterface.getMovie(movieId, getApiKey());
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}
