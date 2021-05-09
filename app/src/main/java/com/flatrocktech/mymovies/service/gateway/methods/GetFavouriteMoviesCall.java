package com.flatrocktech.mymovies.service.gateway.methods;

import android.content.Context;

import com.flatrocktech.mymovies.data.MovieServiceDBImpl;
import com.flatrocktech.mymovies.data.MoviesService;
import com.flatrocktech.mymovies.service.gateway.Callback;
import com.flatrocktech.mymovies.service.gateway.MovieProvider;
import com.flatrocktech.mymovies.service.models.Movie;

import java.util.List;

public class GetFavouriteMoviesCall implements MovieProvider {
    private MoviesService moviesService;

    public GetFavouriteMoviesCall() {
        moviesService = new MovieServiceDBImpl();
    }

    @Override
    public void getMovies(Callback<List<Movie>> movies) {
        movies.onSuccess(moviesService.getFavorites());
    }
}
