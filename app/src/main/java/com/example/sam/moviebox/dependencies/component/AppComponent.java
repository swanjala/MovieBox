package com.example.sam.moviebox.dependencies.component;


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
