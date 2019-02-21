package com.example.sam.moviebox.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sam.moviebox.database.MovieBoxDatabase;
import com.example.sam.moviebox.model.MovieModel;

import java.util.List;

public class FavoriteMoviesViewModel extends ViewModel {
   private LiveData<List<MovieModel>> movies;

   public FavoriteMoviesViewModel(MovieBoxDatabase database, boolean favoriteMovie){
       movies = database.movieDao().fetchAllMovies();
   }

   public LiveData<List<MovieModel>> getFavoriteMovies(){
       return movies;
   }

}
