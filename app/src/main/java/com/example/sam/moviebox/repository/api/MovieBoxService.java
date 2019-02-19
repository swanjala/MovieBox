package com.example.sam.moviebox.repository.api;

import android.arch.lifecycle.LiveData;

import com.example.sam.moviebox.repository.model.ApiResponse;

import retrofit2.http.GET;

public interface MovieBoxService {

    @GET("movie/popular?")
    LiveData<ApiResponse<MovieResponse>> getPopularMovies();

    @GET("genre/movie/list")
    LiveData<ApiResponse<GenreResponse>> getGenres();
}
