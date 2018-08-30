package com.example.sam.moviebox.networkUtils;

import android.content.Context;
import android.os.Build;


import com.example.sam.moviebox.BuildConfig;
import com.example.sam.moviebox.R;
import com.example.sam.moviebox.classInterfaces.IJsonUtils;
import com.example.sam.moviebox.classInterfaces.INetworkCalls;
import com.example.sam.moviebox.classInterfaces.IUrlBuilder;
import com.example.sam.moviebox.jsonUtils.JsonUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

public class NetworkCalls implements INetworkCalls {

    private static final String RESULTS = "results";
    private static final String GENRES = "genres";

    private Context context;

    IJsonUtils jsonUtils = new JsonUtils();
    IUrlBuilder urlBuilder;


    public NetworkCalls(Context context){
        this.context = context;
        this.urlBuilder = new UrlBuilder(context);
    }

    public JSONArray getNetworkData() throws IOException, JSONException {

        return jsonUtils.genericNetworkJsonParser(networkHelper(getMoviesObject()),
                RESULTS);

    }
    public JSONArray dataResults() throws IOException, JSONException {
        return getNetworkData();
    }

    public JSONArray genreResults()throws IOException, JSONException{
        return getGenresData();
    }


    public JSONArray getGenresData() throws IOException, JSONException{

        return jsonUtils.genericNetworkJsonParser(networkHelper(getMovieGenres()),
                GENRES);
    }

    public JSONArray getTrailers(String id) throws IOException, JSONException {

        return jsonUtils.genericNetworkJsonParser(networkHelper(getMovieTrailers(id)),
                RESULTS);
    }

    private String networkHelper(HttpGet getMethod) throws IOException {

        HttpResponse networkResponse = httpClient.execute(getMethod);
        return  EntityUtils.toString(networkResponse.getEntity());
    }

    @Override
    public HttpGet getMoviesObject() throws MalformedURLException {

        HttpGet httpGetObject = new HttpGet(String.valueOf(urlBuilder.buildURL(
                context.getString(R.string.popular_url_path), BuildConfig.ApiKey)));
        return httpGetObject;
    }

    @Override
    public HttpGet getMovieGenres() throws MalformedURLException {
        HttpGet httpGetGenres =
                new HttpGet(String.valueOf(urlBuilder
                        .buildGenreUrl(context.getString(R.string.list_path_url), BuildConfig.ApiKey)));
         return httpGetGenres;
    }

    @Override
    public HttpGet getMovieTrailers(String id) throws MalformedURLException {
        HttpGet httpGetTrailer =
                new HttpGet( String.valueOf(urlBuilder.buildUrlWithID(id,
                        context.getString(R.string.videos_path_url),
                        BuildConfig.ApiKey)));
        return httpGetTrailer;
    }
}
