package com.example.sam.moviebox;

import android.app.Activity;
import android.app.Application;

import com.example.sam.moviebox.dependencies.AppInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class MovieBoxApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        AppInjector.init(this);
    }
    @Override
    public AndroidInjector<Activity> activityInjector(){
       return dispatchingAndroidInjector;
    }

}
