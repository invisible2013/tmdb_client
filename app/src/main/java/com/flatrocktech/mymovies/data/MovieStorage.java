package com.flatrocktech.mymovies.data;

import com.flatrocktech.mymovies.service.models.Movie;

import java.util.ArrayList;
import java.util.List;

class MovieStorage {

    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
