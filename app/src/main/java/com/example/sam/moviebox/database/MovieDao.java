package com.example.sam.moviebox.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sam.moviebox.model.MovieModel;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movieData")
    LiveData<List<MovieModel>> fetchAllMovies();

    @Query("SELECT * FROM movieData WHERE id=:id")
    MovieModel fetchMovieById(int id);

    @Query("SELECT * FROM movieData WHERE favorite = 1")
    LiveData<List<MovieModel>> fetchAllFavorite();

    @Insert
    void insertMovie(List<MovieModel> movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieModel movieEntry);

    @Delete
    void deleteMovie(MovieModel movieModel);


}
