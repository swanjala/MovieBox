package com.example.sam.moviebox.jsonUtils;

import com.example.sam.moviebox.classInterfaces.IJsonUtils;
import com.example.sam.moviebox.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

                comparator = String.valueOf(t1.getVoteAverage())
                        .compareTo(String.valueOf(movieModel.getVoteAverage()));
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


    public MovieModel modelBuilder(JSONObject jsonObject, JSONArray genreArrayNames)
            throws JSONException {

        String genreNames = "";
        movieModel.setVoteAverage(jsonObject.getString(TOP_RATED));
        movieModel.setId(jsonObject.getInt(ID));
        movieModel.setVideo(jsonObject.getBoolean(VIDEO));
        movieModel.setTitle(jsonObject.getString(TITLE));
        movieModel.setPopularity(jsonObject.getInt(POPULARITY));
        movieModel.setOriginalLanguage(jsonObject.getString(ORIGINAL_LANGUAGE));
        movieModel.setOriginalTitle(jsonObject.getString(ORIGINAL_TITLE));
        movieModel.setGenreIds(jsonObject.getString(GENRE_ID));
        movieModel.setPosterPath(jsonObject.getString(POSTER_PATH));
        movieModel.setBackdropPath(jsonObject.getString(BACK_DROP));
        movieModel.setAdultFilm(jsonObject.getBoolean(ADULT));
        movieModel.setOverview(jsonObject.getString(OVERVIEW).trim());
        movieModel.setReleaseDate(jsonObject.getString(RELEASE_DATE));

        JSONArray genreIds = new JSONArray(movieModel.getGenreIds());

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
