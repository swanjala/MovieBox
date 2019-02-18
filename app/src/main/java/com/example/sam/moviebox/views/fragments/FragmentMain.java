package com.example.sam.moviebox.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.viewModel.MovieViewModel;
import com.example.sam.moviebox.views.adapters.MainRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMain extends Fragment implements Injectable {

    @Inject
    public ViewModelProviders.Factory mViewModelFactory;

    @Inject
    public FragmentNavigator fragmentNavigator;

    @BindView(R.id.rv_main_layout_recyclerView);
    RecyclerView mRecylerView;


    private MainRecyclerAdapter mMainRecyclerAdapter;
    private MovieViewModel mMoviewViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecylerView.getContext(),
                2);

        mRecylerView.setLayoutManager(gridLayoutManager);
        mMainRecyclerAdapter =
                new MovieViewModel((vMoviePoser,movie) -> fragmentNavigator
                .navigateToDetailsFragment(ivMoviePoster, movie.id)
        );
        mRecylerView.setAdapter(mMainRecyclerAdapter);
        mMoviewViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(MovieViewModel.class);
        mMoviewViewModel.getMovies().observe(this, this::handleResponse);

    }

//    @Override
//    public void onCreatOptionsMenu(Menu menu, MenuInflater inflater){
//        inflater.i
//    } come back to this after core functionality

    /* have a helper class for this and use a contract class
    * to ring it in.--(Separation of concerns)*/

    private void handleResponse(Resource<List<MovieModel>> listResource){
        if(listResource != null){
            switch (listResource.status){
                case ERROR;
                   progressBar.setVisibility(View.GONE);
                   errorTextView.setVisibility(View.VISIBLE);
                   errorTextView.setText(listResource.message);
                   break;
                case LOADING:
                    progreeBar.setVisibility(View.VISIBLE);
                    errorTextView.setVisibility(View.GONE);
                    break;

                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    errorTextView.setVisibility(View.GONE);
                    if(listResource.data != null && listResource.data.size() > 0){
                        mMovieList = listResource.data;
                        mMainRecyclerAdapter.setData(listResource.data);
                        mMainRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        errorTextView.setText("Unable to load movies");
                        errorTextView.setVisibility(View.VISIBLE);
                    }
                    break;

                    default:
                        progressBar.setVisibility(View.GONE);
                        errorTextView.setText("Movies not found");
                        break;
            }
        }
    }






}
