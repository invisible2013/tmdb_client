package com.flatrocktech.mymovies.service;

import com.flatrocktech.mymovies.R;
import com.flatrocktech.mymovies.service.models.Movie;
import com.flatrocktech.mymovies.service.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("list/1")
    Call<MovieResponse> getMovies(@Query("api_key") String api_key);

    @GET("/movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key);

    @GET("/movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") Long id, @Query("api_key") String api_key);
}
