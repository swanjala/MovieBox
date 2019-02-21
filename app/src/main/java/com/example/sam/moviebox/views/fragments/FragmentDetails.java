package com.example.sam.moviebox.views.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sam.moviebox.R;
import com.example.sam.moviebox.dependencies.Injectable;
import com.example.sam.moviebox.model.GenreModel;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.resources.utils.Resource;
import com.example.sam.moviebox.viewModel.DetailViewModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDetails extends Fragment implements Injectable {

    private static final String BUNDLE_MOVIE_ID = "MOVIE_ID";

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @Inject
    public FragmentNavigator fragmentNavigator;

    @BindView(R.id.iv_movie_poster)
    ImageView iv_moviePoster;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_popularity)
    TextView tv_popularity;
    @BindView(R.id.tv_original_language)
    TextView tv_original_language;
    @BindView(R.id.tv_overview)
    TextView tv_overview;
    @BindView(R.id.imageView)
    ImageView tv_image_view;
    @BindView(R.id.rv_trailer)
    RecyclerView rv_trailer;
    @BindView(R.id.playTrailer)
    Button bt_playTrailer;
    @BindView(R.id.rv_reviews)
    RecyclerView rv_reviews;
    @BindView(R.id.view3)
    View thirdView;
    @BindView(R.id.tv_release_dates)
    TextView tv_release_dates;
    @BindView(R.id.tv_genre_ids)
    TextView tv_genre_ids;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
//    @BindView(R.id.playTrailer)
//    Button bt_play_trailer;


    private DetailViewModel mDetailViewModel;

    private List<String> genres = new ArrayList<>();

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

        mDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DetailViewModel.class);
        // add details
        mDetailViewModel.setMovieId(getArguments().getInt(BUNDLE_MOVIE_ID));
        mDetailViewModel.getMovie().observe(this, this::handleResponse);

    }
    // add response handlers

    private void handleGenreResponse(Resource<GenreModel> genreResource){
        if(genreResource != null){
            switch (genreResource.status) {
                case LOADING:
                    mProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"Loading", Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS:
                    if (genreResource.data != null) {
                        mProgressBar.setVisibility(View.GONE);
                        genres.add(genreResource.data.name);
                    }
                    break;
                case ERROR:
                    mProgressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }
    private void handleResponse(Resource<MovieModel> movieResult){
        switch(movieResult.status) {
            case LOADING:

                mProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Loading data",Toast.LENGTH_LONG).show();
                break;
            case SUCCESS:
                mProgressBar.setVisibility(View.GONE);
                displayMovieInfo(movieResult.data);
                break;
            case ERROR:
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Error loading data",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void displayMovieInfo(MovieModel movieResults) {
        if (movieResults != null) {
            String imagePath = getString(R.string.base_poster_url) +
                    getString(R.string.poster_size_path_w70) + movieResults.posterUrl;
            String imageBackDrop = getString(R.string.base_poster_url) +
                    getString(R.string.poster_size_path_w70) + movieResults.backdropPath;


            for (int genreId : movieResults.genreIds) {
                mDetailViewModel.getMovieGenresById(genreId)
                        .observe(this, this::handleGenreResponse);
            }
            tv_title.setText(movieResults.title);
            tv_title.bringToFront();
            tv_popularity.setText(String.valueOf(movieResults.popularity));
            tv_original_language.setText(movieResults.originalLanguage);
            tv_genre_ids.setText(String.valueOf(movieResults.genreIds));
            tv_overview.setText(movieResults.overview);
            tv_overview.bringToFront();
            tv_release_dates.setText(movieResults.releaseYear);
//            bt_play_trailer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent webTrailerIntent = null;
//                    try {
//                        webTrailerIntent = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("http:www.youtube.com/watch?v=" + trailerObject.getString("key")));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    startActivity(webTrailerIntent);
//
//                }
//            });


            Glide.with(getActivity())
                    .load(imageBackDrop)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            startPostponedEnterTransition();
                            startPostponedEnterTransition();
                            return false;
                        }
                    })
                    .into(iv_moviePoster);

        }
    }

}
