package com.example.sam.moviebox.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.sam.moviebox.model.GenreModel;

import java.util.List;

@Dao
public abstract class GenreDao {
    @Query("SELECT * FROM Genre")
    public abstract LiveData<List<GenreModel>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(GenreModel... genreModels);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    public abstract void insetGenres(List<GenreModel> genreModelList);

    @Query("SELECT * FROM Genre where id = :id")
    public abstract LiveData<GenreModel> searchGenresById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract  long createGenreIfNotExists(GenreModel genreModel);

}
