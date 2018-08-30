package com.example.sam.moviebox.networkUtils;

import android.content.Context;
import android.net.Uri;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.classInterfaces.IUrlBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder implements IUrlBuilder {

    private static String
            BASE_MOVIE_API_V3_URL,
            BASE_POSTER_URL,
            BASE_GENRE_URL = null;

    private Context context;

    public UrlBuilder(Context context){
        BASE_MOVIE_API_V3_URL = context.getString(R.string.base_movie_url_api_v3);
        BASE_GENRE_URL = context.getString(R.string.base_genre_url_api_v3);
        this.context = context;
    }

    public UrlBuilder(Context context, String basePosterUrl){
        BASE_POSTER_URL = context.getString(R.string.base_poster_url);
        this.context = context;
    }

    public URL buildURL(String path, String apiKey) throws MalformedURLException {

        Uri loadMovieDataUri = Uri.parse(BASE_MOVIE_API_V3_URL)
                .buildUpon()
                .appendEncodedPath(path.concat(apiKey))
                .build();
        return new URL(loadMovieDataUri.toString());

    }
    public URL buildPosterURL(String path, String backDropPath) throws MalformedURLException {

        Uri loadMovieDataUri = Uri.parse(BASE_POSTER_URL)
                .buildUpon()
                .appendEncodedPath(path.concat(backDropPath))
                .build();

        return new URL(loadMovieDataUri.toString());

    }
    public URL buildGenreUrl(String path, String apiKey) throws MalformedURLException{
        Uri loadMovieDataUri = Uri.parse(BASE_GENRE_URL)
                .buildUpon()
                .appendEncodedPath(path.concat(apiKey))
                .build();
        return new URL(loadMovieDataUri.toString());
    }

    public URL buildUrlWithID(String id, String path, String apiKey)
            throws MalformedURLException{
        Uri loadDataWithIdUri = Uri.parse(BASE_MOVIE_API_V3_URL)
                .buildUpon()
                .appendEncodedPath(path.concat(id).concat(apiKey))
                .build();
        return new URL((loadDataWithIdUri.toString()));
    }
}
