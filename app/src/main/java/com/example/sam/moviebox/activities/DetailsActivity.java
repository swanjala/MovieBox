package com.example.sam.moviebox.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.moviebox.jsonUtils.IJsonUtils;
import com.example.sam.moviebox.jsonUtils.JsonUtils;
import com.example.sam.moviebox.moviewModels.IMovieModel;
import com.example.sam.moviebox.moviewModels.MovieModel;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.networkUtils.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class DetailsActivity extends AppCompatActivity {

    ImageView iv_poster;
    TextView tv_title, tv_popularity, tv_original_language,
            tv_genre_ids, tv_overview, tv_release_dates;
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
        populateUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
            populateUI();
    }

    private void loadUI(){
        tv_title = findViewById(R.id.tv_title);
        tv_popularity = findViewById(R.id.tv_popularity);
        tv_original_language = findViewById(R.id.tv_original_language);
        tv_genre_ids = findViewById(R.id.tv_genre_ids);
        tv_overview = findViewById(R.id.tv_overview);
        tv_release_dates = findViewById(R.id.tv_release_dates);
        iv_poster = findViewById(R.id.iv_moview_poster);
    }


    private void setData() throws JSONException {
        JSONObject movieObject = null;
        try {
            movieObject = new JSONObject(this.getIntent().getStringExtra("movie_data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray genreName = null;
        try {
            genreName = new JSONArray(this.getIntent().getStringExtra("genres"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        movieModel = jsonUtils.modelBuilder(movieObject,genreName);
    }
    private void populateUI(){

        tv_title.setText(movieModel.getTitle());
        tv_title.bringToFront();
        tv_popularity.setText(String.valueOf(movieModel.getVoteAverage()));
        tv_original_language.setText(movieModel.getOriginalLanguage());
        tv_genre_ids.setText(String.valueOf(movieModel.getGenreNames()));
        tv_overview.setText(movieModel.getOverview());
        tv_overview.bringToFront();
        tv_release_dates.setText(movieModel.getReleaseDate());
        Log.d("posterpath", this.getString(R.string.base_poster_url) + this.getString(R.string.poster_size_path_w185)
                + movieModel.getBackdropPath()
        );

        Picasso.with(this)
                .load(this.getString(R.string.base_poster_url)
                        + this.getString(R.string.poster_size_path_original)
                        + movieModel.getBackdropPath())
                .fit()
                .centerCrop()
                .into(iv_poster);


    }



}
