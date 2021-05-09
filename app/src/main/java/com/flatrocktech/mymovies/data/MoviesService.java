package com.flatrocktech.mymovies.data;

import android.content.Context;

import com.flatrocktech.mymovies.service.models.Movie;

import java.util.List;

public interface MoviesService {

    void setFavourite(Movie movie);

    void unsetFavourite(String id);

    boolean isFavourite(String id);

    List<Movie> getFavorites();
}
