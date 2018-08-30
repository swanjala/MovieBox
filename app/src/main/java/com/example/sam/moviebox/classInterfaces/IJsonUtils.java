package com.example.sam.moviebox.classInterfaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IJsonUtils {

    JSONArray genericNetworkJsonParser(String jsonString, String responseLabel)
            throws JSONException;
    JSONArray sortMovieData(JSONArray originalMovieData) throws JSONException;

    IMovieModel modelBuilder(JSONObject jsonObject, JSONArray genreArrayNames)
            throws JSONException;

}