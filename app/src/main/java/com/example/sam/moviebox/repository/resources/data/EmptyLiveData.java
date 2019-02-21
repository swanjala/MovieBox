package com.example.sam.moviebox.repository.resources.data;

import android.arch.lifecycle.LiveData;

/**
* Live data with null value
* */

public class EmptyLiveData  extends LiveData {
    private EmptyLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {
        return new EmptyLiveData();
    }

}
