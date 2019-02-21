package com.example.sam.moviebox.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.sam.moviebox.model.MovieModel;

import java.util.List;

@Dao
public abstract class MovieDao {

    @Query("SELECT * FROM movieData")
    public abstract LiveData<List<MovieModel>> fetchAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(MovieModel... movieModel);

    @Query("SELECT * FROM movieData WHERE id=:id")
    public abstract LiveData<MovieModel> fetchMovieById(int id);

    @Query("SELECT * FROM movieData where title = :title")
    public abstract LiveData<List<MovieModel>> searchMovieByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long createMovieIfNotExists(MovieModel movieModel);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovie(List<MovieModel> movieEntry);

}
