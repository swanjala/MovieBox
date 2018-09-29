package com.example.sam.moviebox.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sam.moviebox.classInterfaces.IJsonUtils;
import com.example.sam.moviebox.classInterfaces.IUrlBuilder;
import com.example.sam.moviebox.jsonUtils.JsonUtils;
import com.example.sam.moviebox.classInterfaces.IMovieModel;
import com.example.sam.moviebox.moviewModels.MovieModel;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.networkUtils.UrlBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

import butterknife.BindView;

public class DetailsActivity extends AppCompatActivity {

    private static final String MOVIE_DATA = "movie_data", GENRES = "genres";
    private static final String LOG_TAG = "Data Error";

    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_popularity) TextView tv_popularity;
    @BindView(R.id.tv_original_language) TextView tv_original_language;
    @BindView(R.id.tv_genre_ids) TextView tv_genre_ids;
    @BindView(R.id.tv_overview) TextView tv_overview;
    @BindView(R.id.tv_release_dates) TextView tv_release_dates;
    @BindView(R.id.iv_movie_poster) ImageView iv_poster;
    @BindView(R.id.favoriteToggle) ToggleButton toggleButton;
//    @BindView(R.id.img_favorite) ImageView img_favorite;
 //   @BindView(R.id.checkBox) CheckBox checkBox;

    IJsonUtils jsonUtils = new JsonUtils();
    IMovieModel movieModel = new MovieModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        loadUI();
        try {
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            populateUI();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            populateUI();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void loadUI() {
        tv_title = findViewById(R.id.tv_title);
        tv_popularity = findViewById(R.id.tv_popularity);
        tv_original_language = findViewById(R.id.tv_original_language);
        tv_genre_ids = findViewById(R.id.tv_genre_ids);
        tv_overview = findViewById(R.id.tv_overview);
        tv_release_dates = findViewById(R.id.tv_release_dates);
        iv_poster = findViewById(R.id.iv_movie_poster);
    }


    private void setData() throws JSONException {
        JSONObject movieObject = null;
        try {
            movieObject = new JSONObject(this.getIntent().getStringExtra(MOVIE_DATA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray genreName = null;
        try {
            genreName = new JSONArray(this.getIntent().getStringExtra(GENRES));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        movieModel = jsonUtils.modelBuilder(movieObject, genreName);
    }

    private void populateUI() throws MalformedURLException {

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
                .fit()
                .centerCrop()
                .into(iv_poster);


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                if(isChecked){
                    Log.d("checked","isChecked");

                    compoundButton.startAnimation(animation);
                } else {
                    Log.d("checked","Not");
                    compoundButton.startAnimation(animation);
                }

            }
        });

//        final CheckBox chk = null;

//        int drawableWhiteInt = R.drawable.ic_favorite_white_24dp;



       // img_favorite.setImageResource(R.drawable.ic_favorite_white_24dp);

//        img_favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (chk.isChecked() ) {
//                    img_favorite.setImageResource(R.drawable.ic_favorite_red_24dp);
//
//                }else if (num == 1) {
//                    img_favorite.setImageResource(R.drawable.ic_favorite_white_24dp);
//                }
//            }
//        });

    }

}
