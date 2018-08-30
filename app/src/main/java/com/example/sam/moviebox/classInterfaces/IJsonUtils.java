package com.example.sam.moviebox.classInterfaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IJsonUtils {

    JSONArray genericNetworkJsonParser(String jsonString, String responseLabel)
            throws JSONException;
    IMovieModel modelBuilder(JSONObject jsonObject, JSONArray genreArrayNames)
            throws JSONException;
    JSONArray sortMovieRatedData(JSONArray originalMovieData) throws JSONException;
    JSONArray sortMoviePopularData(JSONArray originalMovieData) throws JSONException;

}
