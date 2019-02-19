package com.example.sam.moviebox.repository.util;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.example.sam.moviebox.repository.model.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Adapter that instanciates a Livedata instance of type ApiResponse
 * within a retrofit call
 * @param <R>
 */

public class LiveDataCallAdapter<R> implements CallAdapter<R,
        LiveData<ApiResponse<R>>> {
    private final Type responseType;
    public LiveDataCallAdapter(Type responseType){
        this.responseType = responseType;
    }
    @Override
    public Type responseType() {
        return responseType;
    }
    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            public void onActive() {
               if(started.compareAndSet(false, true)){
                   call.enqueue(new Callback<R>() {
                       @Override
                       public void onResponse(Call<R> call, Response<R> response) {
                           postValue(new ApiResponse<R>(response));
                       }

                       @Override
                       public void onFailure(Call<R> call, Throwable throwable) {
                            postValue(new ApiResponse<R>(throwable));
                       }
                   });
               }
            }
        }

    }

}
