package com.example.sam.moviebox.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity (tableName="movieData")
public class MovieModel implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private int dbId;
    public int id;
    private boolean video;
    private String voteAverage;
    private String title;
    private int popularity;
    private String posterPath;
    private boolean adultFilm;
    private String originalLanguage;
    private String originalTitle;
    private String genreIds;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private String genreNames;

    private boolean favorite;

    public MovieModel(){

    }
    @Ignore
    public MovieModel(String title, int popularity,String posterPath,
                        String originalLanguage,
                        String backdropPath,String overview, String releaseDates,
                        String voteAverage, int id, String genreNames, boolean favorite){

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
                      String voteAverage, int id, String genreIds, String genreNames, boolean favorite){
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
        this.genreIds = genreIds;

    }

    protected MovieModel(Parcel in) {
        //dbId = in.readInt();
        id = in.readInt();
        voteAverage = in.readString();
        title = in.readString();
        popularity = in.readInt();
        posterPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        genreIds = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        favorite = in.readByte() != 0;
        genreNames = in.readString();

    }


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

    public String getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(voteAverage);
        parcel.writeString(title);
        parcel.writeInt(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeString(genreIds);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeValue(favorite);
        parcel.writeString(genreNames);



    }

    public static final Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
