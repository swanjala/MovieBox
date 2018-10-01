package com.example.sam.moviebox.database;

import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final Object LOCK = new Object();
    private static AppExecutors databaseInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread){
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }
    public static AppExecutors getDatabaseInstance(){
        if(databaseInstance == null){
            synchronized (LOCK){
                databaseInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return databaseInstance;
    }
    public Executor getDiskIO() {
        return diskIO;
    }
    public Executor mainThread(){
        return mainThread;
    }
    public Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command){
            mainThreadHandler.post(command);
        }

    }


}
