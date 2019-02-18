package com.example.sam.moviebox.views.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.views.activities.MainActivity;

import javax.inject.Inject;

public class FragmentNavigator {

    private final FragmentManager fragmentManager;
    private final int container;

    @Inject
    public FragmentNavigator(MainActivity mainActivity){
        container = R.id.main_activity_container;
        fragmentManager = mainActivity.getSupportFragmentManager();

    }
    public void navigateToFragmentMain() {
        FragmentMain fragmentMain = new FragmentMain();
        fragmentManager.beginTransaction()
                .replace(container, fragmentMain)
                .commitAllowingStateLoss();
    }

    public void navigateToFragmentDetails(View imageView, int movieId) {
        FragmentDetails fragmentDetails = FragmentDetails.create(movieId);
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
                .replace(container, fragmentDetails)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

}
