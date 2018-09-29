package com.example.sam.moviebox.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sam.moviebox.moviewModels.MovieModel;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM moviemodel")
    List<MovieModel> fetchAllMovies();


    @Query("SELECT * FROM moviemodel WHERE favorite =:favorite")
    List<MovieModel> fetchAllFavorite(boolean favorite);

    @Insert
    void insertTask(MovieModel movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(MovieModel movieEntry);

    @Delete
    void deleteTask(MovieModel movieModel);


}
