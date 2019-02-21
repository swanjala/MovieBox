package com.example.sam.moviebox.dependencies.component;


import android.app.Application;

import com.example.sam.moviebox.MovieBoxApp;
import com.example.sam.moviebox.dependencies.module.AppModule;
import com.example.sam.moviebox.dependencies.module.MainActivityModule;
import com.example.sam.moviebox.dependencies.module.RoomModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class,
        RoomModule.class,
})
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application );
        AppComponent build();
    }
    void inject(MovieBoxApp movieBoxApp);
}
