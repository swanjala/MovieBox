package com.example.sam.moviebox.dependencies.module;

import com.example.sam.moviebox.repository.api.MovieBoxService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes ={
        ViewModelModule.class,
        NetworkModule.class
})
public class AppModule {

    @Provides
    @Singleton
    MovieBoxService provideMovieBoxService(Retrofit retrofit){
        return retrofit.create(MovieBoxService.class);
    }
}
