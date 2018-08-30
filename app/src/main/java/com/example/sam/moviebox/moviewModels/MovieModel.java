package com.example.sam.moviebox.moviewModels;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieModel implements IMovieModel {

    private int id;
    private boolean video;
    private String voteAverage;
    private String title;
    private int popularity;
    private String posterPath;
    private boolean adultFilm;
    private String originalLanguage;
    private String originalTitle;
    private JSONArray genreIds;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private String genreNames;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public JSONArray getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(JSONArray genreIds) {
        this.genreIds = genreIds;
    }


    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }


    public boolean isAdultFilm() {
        return adultFilm;
    }

    public void setAdultFilm(boolean adultFilm) {
        this.adultFilm = adultFilm;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenreNames(String genres){
        this.genreNames = genres;
    }
    public String getGenreNames(){
        return this.genreNames;
    }


}
