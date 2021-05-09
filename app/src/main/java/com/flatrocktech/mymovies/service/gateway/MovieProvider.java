package com.flatrocktech.mymovies.service.gateway;

import com.flatrocktech.mymovies.service.models.Movie;

import java.util.List;

public interface MovieProvider {

    void getMovies(Callback<List<Movie>> movies);
}
