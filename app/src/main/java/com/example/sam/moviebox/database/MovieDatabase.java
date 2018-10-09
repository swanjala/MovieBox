package com.example.sam.moviebox.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import com.example.sam.moviebox.moviewModels.MovieModel;

@Database(entities = {MovieModel.class}, version = 32, exportSchema = false)
@TypeConverters(JSONConverter.class)
public abstract class MovieDatabase extends RoomDatabase{

    private static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "moviebox";
    private static MovieDatabase movieInstance;

    public  static MovieDatabase getMovieInstance(Context context) {

            if (movieInstance == null) {
                synchronized (LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance");
                    movieInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
            Log.d(LOG_TAG, "Getting the database Instance");
            return movieInstance;

    }

    public abstract MovieDao movieDao();


}