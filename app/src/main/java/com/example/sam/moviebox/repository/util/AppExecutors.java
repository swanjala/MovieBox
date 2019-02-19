package com.example.sam.moviebox.repository.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppExecutors {
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    public AppExecutors(Executor diskIO, Executor networkIO,
                        Executor mainThread){
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    @Inject
    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    public Executor getDiskIO(){
        return diskIO;
    }
    public Executor getNetworkIO(){
        return networkIO;
    }
    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command){
            mainThreadHandler.post(command);
        }
    }
}
