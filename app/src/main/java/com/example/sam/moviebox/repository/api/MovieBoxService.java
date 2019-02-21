package com.example.sam.moviebox.repository.api;

import android.arch.lifecycle.LiveData;

import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.model.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieBoxService {

    @GET("movie/popular?")
    LiveData<ApiResponse<MovieResponse>> getPopularMovies();

    @GET("movie/{movie_id}")
    LiveData<ApiResponse<MovieModel>> getMovieById(@Path("movie_id") int movieId);

    @GET("genre/movie/list")
    LiveData<ApiResponse<GenreResponse>> getGenres();
}
