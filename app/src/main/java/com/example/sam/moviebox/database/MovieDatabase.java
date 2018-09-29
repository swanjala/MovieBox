package com.example.sam.moviebox.database;

import android.arch.persistence.room.Database;

import com.example.sam.moviebox.moviewModels.MovieModel;

@Database(entities = {MovieModel.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase {

    public abstract MovieDao movieDao();
}
