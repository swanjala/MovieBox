package com.example.sam.moviebox.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sam.moviebox.R;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class FragmentDetails extends Fragment implements Injectable{

    @Inject
    public ViewModelProviders.Factory mViewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        postponeEnterTransition();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_movie_details,container,false);
        ButterKnife.bind(this, view);
        return view;
    }
    public static FragmentDetails create(int movieId){
        FragmentDetails fragmentDetails = new FragmentDetails();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_MOVIE_ID, movieId);
        fragmentDetails.setArguments(args);
        return fragmentDetails;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // add details
    }
    // add response handlers

}
