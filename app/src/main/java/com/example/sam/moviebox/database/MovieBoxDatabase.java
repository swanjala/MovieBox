package com.example.sam.moviebox.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.sam.moviebox.database.dao.GenreDao;
import com.example.sam.moviebox.database.dao.MovieDao;
import com.example.sam.moviebox.model.GenreModel;
import com.example.sam.moviebox.model.MovieModel;

@Database(entities = {MovieModel.class, GenreModel.class}, version = 37)
public abstract class MovieBoxDatabase extends RoomDatabase {

     abstract public MovieDao movieDao();

     abstract public GenreDao genreDao();


}