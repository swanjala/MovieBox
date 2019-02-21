package com.example.sam.moviebox.repository.utils;

import com.example.sam.moviebox.repository.resources.utils.AppExecutors;

import java.util.concurrent.Executor;

public class InstantAppExecutors extends AppExecutors {
    private static Executor instant = command -> command.run();

    public InstantAppExecutors() {
        super(instant,instant,instant);
    }
}
