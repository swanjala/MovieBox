package com.example.sam.moviebox.networkUtils;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface INetworkCalls {

    HttpClient httpClient = new DefaultHttpClient();

    URL setURI(String path, String apiKey) throws IOException;

    HttpGet getMoviesObject() throws MalformedURLException;

    JSONArray getNetworkData() throws IOException, JSONException;

    JSONArray dataResults() throws IOException, JSONException;

    HttpGet getMovieGenres() throws MalformedURLException;

    JSONArray genreResults()throws IOException, JSONException;


}
