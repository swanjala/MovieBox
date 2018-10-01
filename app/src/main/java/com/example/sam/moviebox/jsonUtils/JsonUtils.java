package com.example.sam.moviebox.jsonUtils;

import android.util.Log;

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

    private final MovieModel movieModel = new MovieModel();
    private static final String
            TOP_RATED = "vote_average",
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
    private static final String LOG_TAG = "Data Error";

    public JSONArray genericNetworkJsonParser(String jsonString, String responseLabel)
            throws JSONException {
        JSONObject genericData = new JSONObject(jsonString);
        return genericData.getJSONArray(responseLabel);

    }

    public List<MovieModel> sortMovieRatedData(List<MovieModel> movies) {

        Collections.sort(movies, new Comparator<MovieModel>() {
            int comparator;
            @Override
            public int compare(MovieModel movieModel, MovieModel t1) {

                comparator = String.valueOf(t1.getPopularity())
                        .compareTo(String.valueOf(movieModel.getPopularity()));
                return comparator;
            }
        });

        return  movies;
    }
    public List<MovieModel> sortMoviePopularData(List<MovieModel> movies) {

        Collections.sort(movies, new Comparator<MovieModel>() {
            int comparator;
            @Override
            public int compare(MovieModel movieModel, MovieModel t1) {

                comparator = String.valueOf(t1.getPopularity())
                        .compareTo(String.valueOf(movieModel.getPopularity()));
                return comparator;
            }
        });

        return  movies;
    }
    public JSONArray sortMoviePopularData(JSONArray originalMovieData) throws JSONException{

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

                    comparator = secondDataObject.getString(POPULARITY)
                            .compareTo( firstDataObject.getString(POPULARITY));

                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(),e);
                }
                return comparator;
            }
        });

        return new JSONArray(objectList);
    }



    public MovieModel modelBuilder(JSONObject jsonObject, JSONArray genreArrayNames)
            throws JSONException {

        String genreNames = "";
        Log.d("data",String.valueOf(jsonObject));

        movieModel.setVoteAverage(jsonObject.getString(TOP_RATED));
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
