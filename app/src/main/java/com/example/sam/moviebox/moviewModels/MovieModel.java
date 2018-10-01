package com.example.sam.moviebox.moviewModels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.sam.moviebox.classInterfaces.IMovieModel;

import org.json.JSONArray;

import java.io.Serializable;

@Entity (tableName="movieData")
public class MovieModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int dbId;
    private int id;
    private boolean video;
    private String voteAverage;
    private String title;
    private int popularity;
    private String posterPath;
    private boolean adultFilm;
    private String originalLanguage;
    private String originalTitle;
    @Ignore
    private JSONArray genreIds;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private String genreNames;

    private String favorite;

    public MovieModel(){

    }
    @Ignore
    public MovieModel(String title, int popularity,String posterPath,
                        String originalLanguage,
                        String backdropPath,String overview, String releaseDates,
                        String voteAverage, int id, String genreNames, String favorite){

        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDates;
        this.voteAverage = voteAverage;
        this.id = id;
        this.genreNames = genreNames;
        this.favorite = favorite;

    }
    public MovieModel(int dbId, String title, int popularity,String posterPath,
                      String originalLanguage,
                      String backdropPath,String overview, String releaseDates,
                      String voteAverage, int id, String genreNames, String favorite){
        this.dbId = dbId;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDates;
        this.voteAverage = voteAverage;
        this.id = id;
        this.genreNames = genreNames;
        this.favorite = favorite;

    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
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
    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }


    public String getGenreNames() {
        return this.genreNames;
    }
    public void setGenreNames(String genres) {
        this.genreNames = genres;
    }


}
