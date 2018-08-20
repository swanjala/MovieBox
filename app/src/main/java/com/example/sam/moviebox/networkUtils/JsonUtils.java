package com.example.sam.moviebox.networkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.TypeInfo;

import java.lang.reflect.Array;

public class JsonUtils {

    private String jsonString;
    private JSONArray dataJsonArray;
    private JSONObject dataJsonObject;

    public JsonUtils(String json){

        this.jsonString = json;

    }

    private void dataJSONArray() throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        this.dataJsonObject = jsonObject;

    }

    private JSONArray dataParser(String jsonKeyString) throws JSONException {

        if (this.dataJsonObject.get(jsonKeyString) instanceof Array){

        }
        return dataJsonArray;
    }

}
