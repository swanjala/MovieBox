package com.example.sam.moviebox.networkUtils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;


import com.example.sam.moviebox.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkCalls implements INetworkCalls {
    private static String BASE_MOVIE_API_V3_URL = null;
    private Context context;
    public JSONArray dataStringResult;

    public NetworkCalls(Context context) {
        BASE_MOVIE_API_V3_URL = context.getString(R.string.base_movie_url_api_v3);
        this.context = context;
    }

    public URL setURI(String path, String apiKey) throws MalformedURLException {

        Uri loadMovieDataUri = Uri.parse(BASE_MOVIE_API_V3_URL)
                .buildUpon()
                .appendEncodedPath(path.concat(apiKey))
                .build();
        Log.d("Url", loadMovieDataUri.toString());

        Log.d("setup ret",String.valueOf(new URL(loadMovieDataUri.toString())));

        return new URL(loadMovieDataUri.toString());

    }

    @Override
    public HttpGet getMoviesObject() throws MalformedURLException {

        HttpGet httpGetObject = new HttpGet(String.valueOf(setURI(
                context.getString(R.string.popular_url_path, context),
                context.getString(R.string.api_key))));
        return httpGetObject;
    }

    public JSONArray getNetworkData() throws IOException, JSONException {

        HttpResponse dataResponse = httpClient.execute(getMoviesObject());
        String dataString = EntityUtils.toString(dataResponse.getEntity());

        JSONObject jsonObject = new JSONObject(dataString);

        return this.dataStringResult = jsonObject.getJSONArray("results");

    }

    public JSONArray dataResults() throws IOException, JSONException {
        return getNetworkData();
    }


}
