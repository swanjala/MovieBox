package com.example.sam.moviebox.views.fragments;

import android.arch.lifecycle.ViewModelProvider;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.dependencies.Injectable;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.resources.utils.Resource;
import com.example.sam.moviebox.viewModel.MovieViewModel;
import com.example.sam.moviebox.views.adapters.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMain extends Fragment implements Injectable {

    @Inject
    public ViewModelProvider.Factory mViewModelFactory;

    @Inject
    public FragmentNavigator fragmentNavigator;

    @BindView(R.id.rv_main_layout_recyclerView)
    RecyclerView mRecylerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;


    private MainRecyclerAdapter mMainRecyclerAdapter;
    private MovieViewModel mMoviewViewModel;
   // private List<MovieModel> mMovieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = layoutInflater.inflate(R.layout.fragment_main, container, false);
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
                new MainRecyclerAdapter((movie) -> fragmentNavigator
                .navigateToFragmentDetails( movie.id)
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
    * to ring it in.--(Separation of concerns)
    * See if this can be generified  */

    private void handleResponse(Resource<List<MovieModel>> listResource){
        if(listResource != null){
            switch (listResource.status){
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Error fetching " + listResource.message
                            ,Toast.LENGTH_LONG).show();
                   break;
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"Loading "
                            ,Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if(listResource.data != null && listResource.data.size() > 0){
                        mMainRecyclerAdapter.setData(listResource.data);
                        mMainRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(),"Unable to load movies",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                    default:
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Movies not found",
                                Toast.LENGTH_LONG).show();
                        break;
            }
        }
    }






}
