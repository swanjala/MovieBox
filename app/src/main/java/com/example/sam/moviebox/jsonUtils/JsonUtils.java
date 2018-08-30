package com.example.sam.moviebox.jsonUtils;

import com.example.sam.moviebox.classInterfaces.IJsonUtils;
import com.example.sam.moviebox.classInterfaces.IMovieModel;
import com.example.sam.moviebox.moviewModels.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JsonUtils implements IJsonUtils {

    private final IMovieModel movieModel = new MovieModel();
    private static final String
            VOTE_AVERAGE = "vote_average",
            ID = "id",
            NAME = "name",
            VIDEO= "video",
            TITLE= "title",
            POPULARITY= "popularity",
            ORIGINAL_LANGUAGE= "original_language",
            ORIGINAL_TITLE ="original_title",
            GENRE_ID="genre_ids",
            POSTER_PATH= "poster_path",
            BACK_DROP="backdrop_path",
            ADULT="adult",
            OVERVIEW= "overview",
            RELEASE_DATE="release_date";

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

                    comparator = secondDataObject.getString(VOTE_AVERAGE)
                            .compareTo( firstDataObject.getString(VOTE_AVERAGE));

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

        movieModel.setVoteAverage(jsonObject.getString(VOTE_AVERAGE));
        movieModel.setId(jsonObject.getInt(ID));
        movieModel.setVideo(jsonObject.getBoolean(VIDEO));
        movieModel.setTitle(jsonObject.getString(TITLE));
        movieModel.setPopularity(jsonObject.getInt(POPULARITY));
        movieModel.setOriginalLanguage(jsonObject.getString(ORIGINAL_LANGUAGE));
        movieModel.setOriginalTitle(jsonObject.getString(ORIGINAL_TITLE));
        movieModel.setGenreIds(jsonObject.getJSONArray(GENRE_ID));
        movieModel.setPosterPath(jsonObject.getString(POSTER_PATH));
        movieModel.setBackdropPath(jsonObject.getString(BACK_DROP));
        movieModel.setAdultFilm(jsonObject.getBoolean(ADULT));
        movieModel.setOverview(jsonObject.getString(OVERVIEW).trim());
        movieModel.setReleaseDate(jsonObject.getString(RELEASE_DATE));

        JSONArray genreIds = movieModel.getGenreIds();

        for (int index = 0; index < genreIds.length(); index++) {
            int genreid = genreIds.getInt(index);

            for (int counter = 0; counter < genreArrayNames.length(); counter++) {
                int id = genreArrayNames.getJSONObject(counter).getInt(ID);

                if (id == genreid) {
                    String genre = genreArrayNames.getJSONObject(index).getString(NAME);
                    genreNames = genreNames.concat(genre).concat(" . ");
                }
            }
        }
        movieModel.setGenreNames(genreNames);

        return  movieModel;

    }


}
