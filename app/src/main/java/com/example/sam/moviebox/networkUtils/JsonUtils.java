package com.example.sam.moviebox.networkUtils;

import android.util.Log;

import com.example.sam.moviebox.moviewModels.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.TypeInfo;

import java.lang.reflect.Array;

public class JsonUtils {

    private String jsonString;
    private JSONArray dataJsonArray;
    private JSONObject dataJsonObject;
    MovieModel movieModel = new MovieModel();

    public JsonUtils(String json){

        this.jsonString = json;

    }
    public JsonUtils(){

    }

    public void dataJSONArray(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        JSONArray results= jsonObject.getJSONArray("results");

        JSONObject dataAtZero;

        for (int i = 0; i < results.length() ; i++) {

            dataAtZero = results.getJSONObject(i);
            movieModel.setTitle(dataAtZero.getString("title"));
        }

        Log.d("results", String.valueOf(results.length()));

        this.dataJsonObject = jsonObject;

    }

    private JSONArray dataParser(String jsonKeyString) throws JSONException {

        if (this.dataJsonObject.get(jsonKeyString) instanceof Array){

        }
        return dataJsonArray;
    }

}
