package com.example.sam.moviebox.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class JSONConverter {

    @TypeConverter
    public String toString(JSONArray jsonArray){
        if (jsonArray == null){
            return (null);
        }

        return (jsonArray.toString());

    }
}
