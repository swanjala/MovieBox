package com.example.sam.moviebox.classInterfaces;


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

    HttpGet getMoviesObject() throws MalformedURLException;

    JSONArray getNetworkData() throws IOException, JSONException;

    JSONArray dataResults() throws IOException, JSONException;

    HttpGet getMovieGenres() throws MalformedURLException;

    JSONArray genreResults()throws IOException, JSONException;

    JSONArray getTrailers(String id) throws IOException, JSONException;

    HttpGet getMovieTrailers(String id) throws MalformedURLException;

    HttpGet getMovieReviews(String id)throws MalformedURLException;

    JSONArray getMovieReviewEntries(String id) throws IOException, JSONException;


}
