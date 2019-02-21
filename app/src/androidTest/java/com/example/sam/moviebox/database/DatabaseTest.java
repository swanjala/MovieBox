package com.example.sam.moviebox.database;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

public class DatabaseTest {

    protected MovieBoxDatabase movieBoxDatabase;

    @Before
    public void init() {
        movieBoxDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                MovieBoxDatabase.class
        ).build();
    }

    @After
    public void close() {
        movieBoxDatabase.close();
    }
}

