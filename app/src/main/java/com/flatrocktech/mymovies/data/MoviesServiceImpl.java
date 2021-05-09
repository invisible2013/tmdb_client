package com.flatrocktech.mymovies.data;

import android.content.Context;

import com.flatrocktech.mymovies.data.storage.SharedPrefStorage;
import com.flatrocktech.mymovies.service.models.Movie;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

public class MoviesServiceImpl implements MoviesService {

    public static final String FAVORITE_MOVIE_STORAGE = "favorite_movies_storage";
    public static final String FAVORITE_MOVIE_KEY = "favorite_movies";

    private final SharedPrefStorage utils = new SharedPrefStorage(FAVORITE_MOVIE_STORAGE);

    private Context mContext;

    public MoviesServiceImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void setFavourite(Movie movie) {
        MovieStorage movieStorage = getAllRecords(mContext);
        movieStorage.getMovies().add(movie);
        utils.put(mContext, FAVORITE_MOVIE_KEY, new Gson().toJson(movieStorage));
    }

    @Override
    public void unsetFavourite(String movieId) {
        MovieStorage movieStorage = getAllRecords(mContext);
        Iterator<Movie> movieIterator = movieStorage.getMovies().iterator();
        while (movieIterator.hasNext()) {
            Movie item = movieIterator.next();
            if (item.getId().equals(movieId)) {
                movieIterator.remove();
            }
        }
        utils.put(mContext, FAVORITE_MOVIE_KEY, new Gson().toJson(movieStorage));
    }

    @Override
    public boolean isFavourite(String id) {
        MovieStorage movieStorage = getAllRecords(mContext);
        return movieStorage.getMovies().stream().anyMatch(it -> it.getId().equals(id));
    }

    @Override
    public List<Movie> getFavorites() {
        return getAllRecords(mContext).getMovies();
    }

    private MovieStorage getAllRecords(Context context) {
        String contextAsString = utils.get(context, FAVORITE_MOVIE_KEY);
        if (contextAsString == null) {
            return new MovieStorage();
        }
        return new Gson().fromJson(contextAsString, MovieStorage.class);
    }
}