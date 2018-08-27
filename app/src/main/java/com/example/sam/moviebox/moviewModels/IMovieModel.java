package com.example.sam.moviebox.moviewModels;

import org.json.JSONArray;

public interface IMovieModel {

    String getTitle();
    void setTitle(String title);

    int getPopularity();

    void setPopularity(int popularity);

    String getPosterPath();

    void setPosterPath(String posterPath);

    String getOriginalLanguage();

    void setOriginalLanguage(String originalLanguage);

    public String getOriginalTitle();

    void setOriginalTitle(String originalTitle);

    JSONArray getGenreIds();

    void setGenreIds(JSONArray genreIds);

    String getBackdropPath();

    void setBackdropPath(String backdropPath);

    boolean isAdultFilm();

    void setAdultFilm(boolean adultFilm);

    String getOverview() ;

    void setOverview(String overview);

    String getReleaseDate();

    void setReleaseDate(String releaseDate);

    String getVoteAverage();

    void setVoteAverage(String voteAverage);

    boolean isVideo();

    void setVideo(boolean video);

    int getId();

    void setId(int id);
    void setGenreNames(String genres);
    String getGenreNames();


}

