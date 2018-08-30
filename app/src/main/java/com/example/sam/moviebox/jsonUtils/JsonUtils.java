package com.example.sam.moviebox.jsonUtils;

import android.content.Context;
import android.util.Log;

import com.example.sam.moviebox.moviewModels.IMovieModel;
import com.example.sam.moviebox.moviewModels.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JsonUtils implements IJsonUtils{

    private final IMovieModel movieModel = new MovieModel();

    public JSONArray genericNetworkJsonParser(String jsonString, String responseLabel)
            throws JSONException {
        JSONObject genericData = new JSONObject(jsonString);
        return genericData.getJSONArray(responseLabel);

    }

    public JSONArray sortMovieData(JSONArray originalMovieData) throws JSONException{

        JSONObject dataObject;
        List<JSONObject> objectList = new ArrayList<>();

        for (int index = 0; index < originalMovieData.length(); index++) {

            dataObject = originalMovieData.getJSONObject(index);
            objectList.add(dataObject);

        }

        Collections.sort(objectList, new Comparator<JSONObject>() {
            int comparator;

            @Override
            public int compare(JSONObject firstDataObject, JSONObject secondDataObject ) {
                try {

                    comparator = secondDataObject.getString("vote_average")
                            .compareTo( firstDataObject.getString("vote_average"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return comparator;
            }
        });


        return new JSONArray(objectList);

    }

    public IMovieModel modelBuilder(JSONObject jsonObject, JSONArray genreArrayNames)
            throws JSONException {

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

        return  movieModel;

    }


}
