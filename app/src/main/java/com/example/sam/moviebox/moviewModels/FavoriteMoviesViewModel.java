package com.example.sam.moviebox.moviewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sam.moviebox.database.MovieDatabase;

import java.util.List;

public class FavoriteMoviesViewModel extends ViewModel {
   private LiveData<List<MovieModel>> movies;

   public FavoriteMoviesViewModel(MovieDatabase database, boolean favoriteMovie){
       movies = database.movieDao().fetchAllFavorite();
   }

   public LiveData<List<MovieModel>> getFavoriteMovies(){
       return movies;
   }

}
