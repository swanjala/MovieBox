package com.example.sam.moviebox.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class EspressoTestUtil {

    public static void disableProgressBarAnimation(
            ActivityTestRule<? extends FragmentActivity>
            activityTestRule) {
        activityTestRule.getActivity().getSupportFragmentManager()
                .registerFragmentLifecycleCallbacks(
                        new FragmentManager.FragmentLifecycleCallbacks() {
                            @Override
                            public void onFragmentViewCreated(FragmentManager fragmentManager
                                    , Fragment fragment, View view,Bundle savedInstanceState) {
                                traverseViews(view);
                            }
                        }, true);
    }
    private static void traverseViews(View view) {
        if(view instanceof ViewGroup){
            traverseViewsGroup((ViewGroup) view);
        } else{
            if(view instanceof ProgressBar){
                disableProgressBarAnimation((ProgressBar) view);
            }
        }
    }

    public static void traverseViewsGroup(ViewGroup viewGroup){
        final int count = viewGroup.getChildCount();
        for (int i = 0; i < count ; i++) {
             traverseViews(viewGroup.getChildAt(i));
        }
    }

    private static void disableProgressBarAnimation(ProgressBar progressBar){
        progressBar.setIndeterminateDrawable(new ColorDrawable(Color.BLUE));
    }

}
