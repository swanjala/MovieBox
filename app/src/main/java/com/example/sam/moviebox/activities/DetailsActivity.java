package com.example.sam.moviebox.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.moviebox.moviewModels.IMovieModel;
import com.example.sam.moviebox.moviewModels.MovieModel;

import com.example.sam.moviebox.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    private final IMovieModel movieModel = new MovieModel();

    ImageView iv_poster;
    TextView tv_title,tv_popularity, tv_original_language,
            tv_genre_ids,tv_overview, tv_release_dates;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        tv_title= findViewById(R.id.tv_title);
        tv_popularity = findViewById(R.id.tv_popularity);
        tv_original_language = findViewById(R.id.tv_original_language);
        tv_genre_ids  = findViewById(R.id.tv_genre_ids);
        tv_overview=findViewById(R.id.tv_overview);
        tv_release_dates= findViewById(R.id.tv_release_dates);
        iv_poster= findViewById(R.id.iv_moview_poster);
        try {
            JSONObject movieObject = new JSONObject(getIntent().getStringExtra("movie_data"));
            JSONArray genreName = new JSONArray(getIntent().getStringExtra("genres"));
            populateModel(movieObject,genreName);
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
        Log.d("genre Ids",String.valueOf(genreIds));
        for (int i = 0; i < genreIds.length() ; i++) {
            int genreid = genreIds.getInt(i);

            for (int counter = 0; counter < genreArrayNames.length(); counter++) {
                int id = genreArrayNames.getJSONObject(counter).getInt("id");

                Log.d("genre name data Id ",String.valueOf(id));
                Log.d("genre Ids",String.valueOf(genreid));

                if (id == genreid){
                    String genre = genreArrayNames.getJSONObject(i).getString("name");

                    genreNames = genreNames.concat(genre).concat(" . ");
                    Log.d("actual genre", genreNames);
                }

            }

        }
        Log.d("genre names", genreNames);
        movieModel.setGenreNames(genreNames);

       // Log.d("movie Object", movieObject.toString());
    }

    private void populateUI(){

//        tv_has_video.setText(movieModel.getVoteAverage());
//        tv_has_video.setText(String.valueOf(movieModel.isVideo()));
        tv_title.setText(movieModel.getTitle());
        tv_title.bringToFront();
        tv_popularity.setText(String.valueOf(movieModel.getVoteAverage()));
        tv_original_language.setText(movieModel.getOriginalLanguage());
        tv_genre_ids.setText(String.valueOf(movieModel.getGenreNames()));
//        tv_adult.setText(String.valueOf(movieModel.isAdultFilm()));
        tv_overview.setText(movieModel.getOverview());
        tv_overview.bringToFront();
        tv_release_dates.setText(movieModel.getReleaseDate());
        Log.d("posterpath", this.getString(R.string.base_poster_url)+this.getString(R.string.poster_size_path_w185)
                + movieModel.getBackdropPath()
                 );

        Picasso.with(this)
                .load(this.getString(R.string.base_poster_url)
                        +this.getString(R.string.poster_size_path_original)
                        + movieModel.getBackdropPath())
                .fit()
                .centerCrop()
                .into(iv_poster);


    }


}
