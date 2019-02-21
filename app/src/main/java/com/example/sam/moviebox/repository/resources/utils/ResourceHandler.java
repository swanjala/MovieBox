package com.example.sam.moviebox.repository.resources.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.example.sam.moviebox.repository.model.ApiResponse;
import com.example.sam.moviebox.repository.resources.data.Objects;


/**
 * Class that provisions network and database resources
 */
public abstract class ResourceHandler<ResultType,RequestType> {

    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData();

    @MainThread
    public ResourceHandler(AppExecutors appExecutors){
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));

        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if(shouldFetch(data)){
                networkFetch(dbSource);
            } else {
                result.addSource(dbSource, newData ->
                        setValue(Resource.success(newData)));
            }
        });
    }
    @MainThread
    private void setValue(Resource<ResultType> newValue){
        if(!Objects.equals(result.getValue(), newValue)){
            result.setValue(newValue);
        }
    }
    private void networkFetch(final LiveData<ResultType> dbSource){
        LiveData<ApiResponse<RequestType>> apiResponseLiveData = createCall();
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponseLiveData, response -> {
            result.removeSource(apiResponseLiveData);
            result.removeSource(dbSource);

            if (response.isSuccessful()){
                appExecutors.diskIO().execute(() ->{
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() ->
                            result.addSource(loadFromDb(),
                                    newData-> setValue(Resource.success(newData))));
                });
            }else{
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> setValue(Resource.error(response
                        .errorMessage, newData)));
            }
        });
    }
    protected  void onFetchFailed(){}

    public LiveData<Resource<ResultType>> asLiveData(){
        return result;
    }
    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response){
        return response.body;
    }
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract  boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
