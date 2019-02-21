package com.example.sam.moviebox.dependencies.module;

import com.example.sam.moviebox.views.fragments.FragmentDetails;
import com.example.sam.moviebox.views.fragments.FragmentMain;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract FragmentMain contributeFragmentMain();

    @ContributesAndroidInjector
    abstract FragmentDetails contributeFragmentDetail();
}
