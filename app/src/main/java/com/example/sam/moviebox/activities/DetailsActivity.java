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

    private final IMovieModel movieModel = new MovieModel();

    ImageView iv_poster;
    TextView tv_title, tv_popularity, tv_original_language,
            tv_genre_ids, tv_overview, tv_release_dates;
    JSONArray  trailerArrays;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        loadUI();
        loadData();
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

    private void loadData() {
        try {
            JSONObject movieObject = new JSONObject(this.getIntent().getStringExtra("movie_data"));
            JSONArray genreName = new JSONArray(getIntent().getStringExtra("genres"));
            populateModel(movieObject, genreName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateModel(JSONObject jsonObject, JSONArray genreArrayNames) throws JSONException {

        String genreNames = "";

        movieModel.setVoteAverage(jsonObject.getString("vote_average"));
        movieModel.setId(jsonObject.getInt("id"));
        movieModel.setVideo(jsonObject.getBoolean("video"));
        movieModel.setTitle(jsonObject.getString("title"));
        movieModel.setPopularity(jsonObject.getInt("popularity"));
        movieModel.setOriginalLanguage(jsonObject.getString("original_language"));
        movieModel.setOriginalTitle(jsonObject.getString("original_title"));
        movieModel.setGenreIds(jsonObject.getJSONArray("genre_ids"));
        movieModel.setPosterPath(jsonObject.getString("poster_path"));
        movieModel.setBackdropPath(jsonObject.getString("backdrop_path"));
        movieModel.setAdultFilm(jsonObject.getBoolean("adult"));
        movieModel.setOverview(jsonObject.getString("overview").trim());
        movieModel.setReleaseDate(jsonObject.getString("release_date"));

        JSONArray genreIds = movieModel.getGenreIds();
        Log.d("genre Ids", String.valueOf(genreIds));
        for (int i = 0; i < genreIds.length(); i++) {
            int genreid = genreIds.getInt(i);

            for (int counter = 0; counter < genreArrayNames.length(); counter++) {
                int id = genreArrayNames.getJSONObject(counter).getInt("id");

                Log.d("genre name data Id ", String.valueOf(id));
                Log.d("genre Ids", String.valueOf(genreid));

                if (id == genreid) {
                    String genre = genreArrayNames.getJSONObject(i).getString("name");

                    genreNames = genreNames.concat(genre).concat(" . ");
                    Log.d("actual genre", genreNames);
                }

            }

        }
        Log.d("genre names", genreNames);
        movieModel.setGenreNames(genreNames);

    }

    private void populateUI() {
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

    public class getTrailer extends AsyncTask<Context,Void, JSONArray>{
    /*Todo
    * To complete implementation in stage 2
    * */

        @Override
        protected JSONArray doInBackground(Context... contexts){

            JSONArray networkCallResults = new JSONArray();

            final INetworkCalls networkCalls = new NetworkCalls(getApplicationContext());

            try {
                trailerArrays = networkCalls.getTrailers(String.valueOf(movieModel.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return networkCallResults;
        }

        @Override
        protected void onPostExecute(JSONArray trailerList) {

            if (trailerArrays != null) {
                mAdapter = new TrailerRecyclerAdapter(getApplicationContext()
                        ,trailerList);
                mRecyclerView.setAdapter(mAdapter);


            }

        }
    }




}
