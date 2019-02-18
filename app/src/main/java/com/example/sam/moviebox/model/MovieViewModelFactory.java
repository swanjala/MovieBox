package com.example.sam.moviebox.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.sam.moviebox.database.MovieDatabase;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieDatabase movieDatabase;
    private final boolean mIsFavorite;

    public MovieViewModelFactory(MovieDatabase database, boolean isFavorite){
        movieDatabase = database;
        mIsFavorite =  isFavorite;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new FavoriteMoviesViewModel(movieDatabase, mIsFavorite);
    }

}
