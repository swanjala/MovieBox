package com.example.sam.moviebox.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.sam.moviebox.database.MovieBoxDatabase;
import com.example.sam.moviebox.database.dao.MovieDao;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.api.MovieBoxService;
import com.example.sam.moviebox.repository.api.MovieResponse;
import com.example.sam.moviebox.repository.model.ApiResponse;
import com.example.sam.moviebox.repository.resources.AppExecutors;
import com.example.sam.moviebox.repository.resources.ResourceHandler;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Communication manager between view models and repositories
 */

@Singleton
public class MovieBoxRepository {
    private MovieBoxService mMovieBoxService;
    private MovieBoxDatabase mMovieBoxDatabase;
    private MovieDao mMovieDao;
    private final AppExecutors mAppExecutors;
    /** TODO: create Genre and video dao
     *
     */

    @Inject
    MovieBoxRepository(AppExecutors appExecutors,
                       MovieBoxService movieBoxService,
                       MovieBoxDatabase movieBoxDatabase,
                       MovieDao movieDao){
        mMovieBoxDatabase = movieBoxDatabase;
        mAppExecutors = appExecutors;
        mMovieDao = movieDao;
        mMovieBoxService = movieBoxService;

    }
    public LiveData<Resource<List<MovieModel>>> getAllMovies(){
        return new ResourceHandler<List<MovieModel>, MovieResponse>(mAppExecutors){

            @Override
            protected void saveCallResult(@NonNull MovieResponse item) {
                mMovieDao.insertMovie(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<MovieModel> data) {
                return data== null|| data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<MovieModel>> loadFromDb() {
                return mMovieDao.fetchAllMovies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                return mMovieBoxService.getPopularMovies();
            }
        }.asLiveData();
    }
    public LiveData<Resource<List<MovieModel>>> getFavouriteMovies(){
        return new ResourceHandler<List<MovieModel>, MovieResponse>(mAppExecutors){

            @Override
            protected void saveCallResult(@NonNull MovieResponse item) {
                mMovieDao.insertMovie(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<MovieModel> data) {
                return data== null|| data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<MovieModel>> loadFromDb() {
                return mMovieDao.fetchAllFavorite();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                return mMovieBoxService.getPopularMovies();
            }
        }.asLiveData();
    }

}
