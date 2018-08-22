package com.example.sam.moviebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.moviebox.moviewModels.IMovieModel;
import com.example.sam.moviebox.moviewModels.MovieModel;

import com.example.sam.moviebox.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    private final IMovieModel movieModel = new MovieModel();

    ImageView iv_poster;
    TextView tv_has_video,tv_title,tv_popularity, tv_original_language,
            tv_genre_ids,tv_adult,tv_overview, tv_release_dates;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tv_has_video = findViewById(R.id.tv_video);
        tv_title= findViewById(R.id.tv_title);
        tv_popularity = findViewById(R.id.tv_popularity);
        tv_original_language = findViewById(R.id.tv_original_language);
        tv_genre_ids  = findViewById(R.id.tv_genre_ids);
        tv_adult=findViewById(R.id.tv_adult);
        tv_overview=findViewById(R.id.tv_overview);
        tv_release_dates= findViewById(R.id.tv_release_dates); ;



        try {
            JSONObject movieObject = new JSONObject(getIntent().getStringExtra("movie_data"));
            populateModel(movieObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        populateUI();
    }

    private void populateModel(JSONObject jsonObject) throws JSONException {

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
        movieModel.setOverview(jsonObject.getString("overview"));
        movieModel.setReleaseDate(jsonObject.getString("release_date"));
    }

    private void populateUI(){

        tv_has_video.setText(movieModel.getVoteAverage());
        tv_has_video.setText(String.valueOf(movieModel.isVideo()));
        tv_title.setText(movieModel.getTitle());
//        tv_popularity.setText(movieModel.getPopularity());
        tv_original_language.setText(movieModel.getOriginalLanguage());
        tv_genre_ids.setText(String.valueOf(movieModel.getGenreIds()));
        tv_adult.setText(String.valueOf(movieModel.isAdultFilm()));
        tv_overview.setText(movieModel.getOverview());
        tv_release_dates.setText(movieModel.getReleaseDate());


    }

}
