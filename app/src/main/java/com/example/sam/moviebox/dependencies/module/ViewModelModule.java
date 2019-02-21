package com.example.sam.moviebox.dependencies.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.example.sam.moviebox.viewModel.DetailViewModel;
import com.example.sam.moviebox.viewModel.MovieViewModel;
import com.example.sam.moviebox.viewModel.MovieViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel.class)
    abstract ViewModel bindMovieViewModel(MovieViewModel movieViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel  detailViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MovieViewModelFactory movieViewModelFactory);

}
