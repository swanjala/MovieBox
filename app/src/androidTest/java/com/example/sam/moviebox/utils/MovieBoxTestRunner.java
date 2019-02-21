package com.example.sam.moviebox.utils;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import com.example.sam.moviebox.TestApp;

public class MovieBoxTestRunner extends AndroidJUnitRunner {
@Override
    public Application newApplication(ClassLoader classLoader,
                                      String className,
                                      Context context)
    throws InstantiationException, IllegalAccessException,
    ClassNotFoundException {
        return super.newApplication(classLoader,
                TestApp.class.getName(), context);
    }
}
