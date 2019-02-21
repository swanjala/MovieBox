package com.example.sam.moviebox.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sam.moviebox.database.MovieBoxDatabase;
import com.example.sam.moviebox.database.dao.GenreDao;
import com.example.sam.moviebox.database.dao.MovieDao;
import com.example.sam.moviebox.model.GenreModel;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.api.GenreResponse;
import com.example.sam.moviebox.repository.api.MovieBoxService;
import com.example.sam.moviebox.repository.api.MovieResponse;
import com.example.sam.moviebox.repository.model.ApiResponse;
import com.example.sam.moviebox.repository.resources.utils.AppExecutors;
import com.example.sam.moviebox.repository.resources.utils.ResourceHandler;
import com.example.sam.moviebox.repository.resources.utils.Resource;

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
    private GenreDao mGenreDao;
    private final AppExecutors mAppExecutors;
    /** TODO: create Genre and video dao
     *
     */

    @Inject
    MovieBoxRepository(AppExecutors appExecutors,
                       MovieBoxService movieBoxService,
                       MovieBoxDatabase movieBoxDatabase,
                       MovieDao movieDao,
                       GenreDao genreDao){
        mMovieBoxDatabase = movieBoxDatabase;
        mAppExecutors = appExecutors;
        mMovieDao = movieDao;
        mMovieBoxService = movieBoxService;
        mGenreDao = genreDao;

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
                return mMovieDao.fetchAllMovies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                return mMovieBoxService.getPopularMovies();
            }
        }.asLiveData();
    }

    public LiveData<Resource<GenreModel>> getGenresById(int genreId){
        return new ResourceHandler<GenreModel, GenreResponse>(mAppExecutors){

            @Override
            protected void saveCallResult(@NonNull GenreResponse item) {
                    mGenreDao.insetGenres(item.getGenres());
            }

            @Override
            protected boolean shouldFetch(@Nullable GenreModel data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<GenreModel> loadFromDb() {
                return mGenreDao.searchGenresById(genreId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<GenreResponse>> createCall() {
                return mMovieBoxService.getGenres();
            }
        }.asLiveData();
    }

    public LiveData<Resource<MovieModel>> getMovieById(int movieId){
        return new ResourceHandler<MovieModel, MovieModel>(mAppExecutors){

            @Override
            protected void saveCallResult(@NonNull MovieModel item) {
                mMovieDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable MovieModel data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<MovieModel> loadFromDb() {
                return mMovieDao.fetchMovieById(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieModel>> createCall() {
                return mMovieBoxService. getMovieById(movieId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> searchNextPage(String query){
        return null;
    }

}
