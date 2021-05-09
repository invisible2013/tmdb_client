package com.flatrocktech.mymovies.data;

import com.flatrocktech.mymovies.TMDBApplication;
import com.flatrocktech.mymovies.data.storage.DatabaseStorage;
import com.flatrocktech.mymovies.service.models.Movie;

import java.util.List;

public class MovieServiceDBImpl implements MoviesService {

    DatabaseStorage storage;

    public MovieServiceDBImpl() {
        this.storage = new DatabaseStorage(TMDBApplication.sInstance);
    }

    @Override
    public void setFavourite(Movie movie) {
        storage.setFavorite(movie);
    }

    @Override
    public void unsetFavourite(String id) {
        storage.unSetFavorite(id);
    }

    @Override
    public boolean isFavourite(String id) {
        Movie movie = storage.get(id);
        return movie != null;
    }

    @Override
    public List<Movie> getFavorites() {
        return storage.getAll();
    }
}
