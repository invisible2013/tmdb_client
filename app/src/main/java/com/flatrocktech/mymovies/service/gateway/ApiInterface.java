package com.flatrocktech.mymovies.service.gateway;

import com.flatrocktech.mymovies.service.models.FilteredMovieResponse;
import com.flatrocktech.mymovies.service.models.Movie;
import com.flatrocktech.mymovies.service.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("list/1")
    Call<MovieResponse> getMovies(@Query("api_key") String api_key);

    @GET("movie/popular")
    Call<FilteredMovieResponse> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<FilteredMovieResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") Long id, @Query("api_key") String api_key);
}
