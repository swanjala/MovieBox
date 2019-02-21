package com.example.sam.moviebox.repository.api;

import android.support.annotation.NonNull;

import com.example.sam.moviebox.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    public AuthInterceptor() {

    }
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException{
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.ApiKey)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
