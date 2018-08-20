package com.example.sam.moviebox.networkUtils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;


import com.example.sam.moviebox.R;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUri implements NetworkInterface {
    private static String BASE_MOVIE_API_V3_URL = null;

    public NetworkUri(Context context){
        BASE_MOVIE_API_V3_URL = context.getString(R.string.base_movie_url_api_v3);
    }

    public URL setURI(String baseUrl, String path, String apiKey) throws MalformedURLException {

    Uri loadMovieDataUri = Uri.parse(BASE_MOVIE_API_V3_URL)
            .buildUpon()
            .appendEncodedPath(path.concat(apiKey))
            .build();
        Log.d("Url", loadMovieDataUri.toString());

    return new URL(loadMovieDataUri.toString());

    }
    
}
