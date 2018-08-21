package com.example.sam.moviebox.networkUtils;

import android.util.Log;

import com.example.sam.moviebox.moviewModels.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.TypeInfo;

import java.lang.reflect.Array;

public class JsonUtils implements IJsonUtils {

    public String jsonString;
    private JSONArray dataJsonArray;
    private JSONObject dataJsonObject;
    MovieModel movieModel = new MovieModel();

    public JsonUtils(String json){

        this.jsonString = json;

    }
    public JsonUtils(){

    }

    public JSONArray dataJSONArray() throws JSONException {

        JSONObject jsonObject = new JSONObject(this.jsonString);

        JSONArray results= jsonObject.getJSONArray("results");

        Log.d("results", String.valueOf(results.length()));

        this.dataJsonObject = jsonObject;

        this.dataJsonArray = results;
        return  results;

    }

    private JSONArray dataParser(String jsonKeyString) throws JSONException {

        if (this.dataJsonObject.get(jsonKeyString) instanceof Array){

        }
        return dataJsonArray;
    }

}
