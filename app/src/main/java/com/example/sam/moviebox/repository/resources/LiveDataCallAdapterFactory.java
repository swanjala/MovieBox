package com.example.sam.moviebox.repository.util;

import android.arch.lifecycle.LiveData;

import com.example.sam.moviebox.repository.model.ApiResponse;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?,?> get(Type returnType, Annotation[] annotations,
                                Retrofit retrofit){
        if(getRawType(returnType) != LiveData.class){
            return null;
        }
        Type observeType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observeType);
        if(!(observeType instanceof ParameterizedType)){
            throw new IllegalArgumentException("Resource not parameterized");
        }
        if(rawObservableType != ApiResponse.class){
            throw  new IllegalArgumentException("Type not resource");
        }
        Type bodyType = getParameterUpperBound(0,(ParameterizedType) observeType);
        return new LiveDataCallAdapter<>(bodyType);

    }
}
