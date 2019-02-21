package com.example.sam.moviebox.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.views.fragments.FragmentNavigator;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    FragmentNavigator fragmentNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);
        if (savedInstanceState == null) {
            fragmentNavigator.navigateToFragmentMain();
        }
        supportPostponeEnterTransition();

        }
        @Override
        public AndroidInjector<Fragment> supportFragmentInjector(){
         return dispatchingAndroidInjector;
        }

    }

