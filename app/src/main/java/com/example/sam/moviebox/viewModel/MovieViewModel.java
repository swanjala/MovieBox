package com.example.sam.moviebox.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sam.moviebox.database.MovieDatabase;
import com.example.sam.moviebox.model.MovieModel;

import java.util.List;

public class MovieViewModel extends AndroidViewModel{

    private static final String TAG = MovieViewModel.class.getSimpleName();

    private LiveData<List<MovieModel>> movies;


    public MovieViewModel(@NonNull Application application){
        super(application);
        // get the instance of the database and call load tasks
        MovieDatabase mMovieDatabase = MovieDatabase.getMovieInstance(this.getApplication());
        Log.d(TAG, "Loading Movies from Database");
        movies = mMovieDatabase.movieDao().fetchAllMovies();

    }
    public LiveData<List<MovieModel>> getMovies() {
        return movies;
    }

}
