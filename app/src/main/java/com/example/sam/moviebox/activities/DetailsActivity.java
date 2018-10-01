package com.example.sam.moviebox.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sam.moviebox.classInterfaces.IJsonUtils;
import com.example.sam.moviebox.classInterfaces.IUrlBuilder;
import com.example.sam.moviebox.database.AppExecutors;
import com.example.sam.moviebox.database.MovieDatabase;
import com.example.sam.moviebox.jsonUtils.JsonUtils;
import com.example.sam.moviebox.moviewModels.MovieModel;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.networkUtils.UrlBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private static final String MOVIE_DATA = "movie_data", GENRES = "genres";
    private static final String LOG_TAG = "Data Error";

    private MovieDatabase movieDatabase;

    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_popularity) TextView tv_popularity;
    @BindView(R.id.tv_original_language) TextView tv_original_language;
    @BindView(R.id.tv_genre_ids) TextView tv_genre_ids;
    @BindView(R.id.tv_overview) TextView tv_overview;
    @BindView(R.id.tv_release_dates) TextView tv_release_dates;
    @BindView(R.id.iv_movie_poster) ImageView iv_poster;
//    @BindView(R.id.favoriteToggle) ToggleButton toggleButton;


    IJsonUtils jsonUtils = new JsonUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        movieDatabase = MovieDatabase.getMovieInstance(getApplicationContext());
        ButterKnife.bind(this);

        try {
            setData();
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(),e);
        }
        try {
            populateUI();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage(),e);
        }

//        try{
//            saveData();
//        }catch (Exception e){
//            Log.e(LOG_TAG, e.getMessage(),e);
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            populateUI();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage(),e);
        }
//        try{
//            saveData();
//        }catch (Exception e){
//            Log.e(LOG_TAG, e.getMessage(),e);
//        }
    }

    private void setData() throws JSONException {
        JSONObject movieObject = null;
        try {
            movieObject = new JSONObject(this.getIntent().getStringExtra(MOVIE_DATA));
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(),e);


        }
//        JSONArray genreName = null;
//        try {
//            genreName = new JSONArray(this.getIntent().getStringExtra(GENRES));
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, e.getMessage(),e);
//        }


    }

    private void populateUI() throws MalformedURLException {
        final MovieModel movieModel = (MovieModel)this.getIntent().getSerializableExtra(MOVIE_DATA);

        IUrlBuilder urlBuilder = new UrlBuilder(this);

        tv_title.setText(movieModel.getTitle());
        tv_title.bringToFront();
        tv_popularity.setText(String.valueOf(movieModel.getVoteAverage()));
        tv_original_language.setText(movieModel.getOriginalLanguage());
        tv_genre_ids.setText(String.valueOf(movieModel.getGenreNames()));
        tv_overview.setText(movieModel.getOverview());
        tv_overview.bringToFront();
        tv_release_dates.setText(movieModel.getReleaseDate());

        Picasso.with(this)
                .load(String.valueOf(urlBuilder.buildPosterURL(this
                                .getString(R.string.poster_size_path_original),
                                movieModel.getBackdropPath())))

                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(iv_poster);

        ToggleButton toggleButton = findViewById(R.id.favoriteToggle);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                if(isChecked){
                    Log.d("checked","isChecked");

                    compoundButton.startAnimation(animation);
                    movieModel.setFavorite("True");
                    AppExecutors.getDatabaseInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            movieDatabase.movieDao().updateMovie(movieModel);
                        }
                    });

                } else {
                    Log.d("checked","Not");
                    compoundButton.startAnimation(animation);
                    movieModel.setFavorite("False");
                    AppExecutors.getDatabaseInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            movieDatabase.movieDao().updateMovie(movieModel);
                        }
                    });
                }

            }
        });

    }

}
