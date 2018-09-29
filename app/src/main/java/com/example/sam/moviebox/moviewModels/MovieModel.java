package com.example.sam.moviebox.moviewModels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.sam.moviebox.classInterfaces.IMovieModel;

import org.json.JSONArray;

import java.io.Serializable;

@Entity (tableName="MovieModel")
public class MovieModel implements IMovieModel, Serializable {

    @PrimaryKey
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

    private boolean favorite = false;

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

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

    public String getGenreNames() {
        return this.genreNames;
    }
    public void setGenreNames(String genres) {
        this.genreNames = genres;
    }


}
