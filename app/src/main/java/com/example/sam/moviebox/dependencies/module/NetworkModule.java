package com.example.sam.moviebox.dependencies.module;

import android.support.annotation.NonNull;

import com.example.sam.moviebox.BuildConfig;
import com.example.sam.moviebox.repository.api.AuthInterceptor;
import com.example.sam.moviebox.repository.resources.data.LiveDataCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.sam.moviebox.utils.Constants.BASE_URL;
import static com.example.sam.moviebox.utils.Constants.CONNECT_TIMEOUT;
import static com.example.sam.moviebox.utils.Constants.READ_TIMEOUT;
import static com.example.sam.moviebox.utils.Constants.WRITE_TIMEOUT;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    HttpLoggingInterceptor provideOkHttpInterceptors() {
        return new HttpLoggingInterceptor().
                setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE);

    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient( HttpLoggingInterceptor httpLoggingInterceptor){

        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitClient(@NonNull OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



}
