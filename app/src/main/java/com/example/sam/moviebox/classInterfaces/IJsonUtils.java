package com.example.sam.moviebox.classInterfaces;

import com.example.sam.moviebox.moviewModels.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface IJsonUtils {

    JSONArray genericNetworkJsonParser(String jsonString, String responseLabel)
            throws JSONException;
    MovieModel modelBuilder(JSONObject jsonObject, JSONArray genreArrayNames)
            throws JSONException;
    List<MovieModel> sortMovieRatedData(List<MovieModel> originalMovieData);
    List<MovieModel> sortMoviePopularData(List<MovieModel> originalMovieData);


}
