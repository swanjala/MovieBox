package com.example.sam.moviebox.dependencies.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.sam.moviebox.database.MovieBoxDatabase;
import com.example.sam.moviebox.database.dao.GenreDao;
import com.example.sam.moviebox.database.dao.MovieDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Singleton
    @Provides
    MovieBoxDatabase providesRoomDatabase(Application application){
        return Room.databaseBuilder(application, MovieBoxDatabase.class, "movieboxdb")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    MovieDao provideMovieDao(MovieBoxDatabase movieBoxDb){
        return movieBoxDb.movieDao();

    }

    @Singleton
    @Provides
    GenreDao provideGenreDao(MovieBoxDatabase database){
        return database.genreDao();
    }

}
