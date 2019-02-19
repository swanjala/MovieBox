package com.example.sam.moviebox.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.sam.moviebox.database.MovieBoxDatabase;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class MovieViewModelFactory implements ViewModelProvider.Factory{

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public MovieViewModelFactory(Map<Class<?extends ViewModel>, Provider<ViewModel>> creators){
        this.creators = creators;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if(creator == null){
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry :
                    creators.entrySet()){
                if(modelClass.isAssignableFrom(entry.getKey())){
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if(creator == null){
            throw new IllegalArgumentException("Class is incorrect" + modelClass);
        }
        try {
            return (T) creator.get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
