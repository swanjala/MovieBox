package com.example.sam.moviebox.networkUtils;

import android.content.Context;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface INetworkCalls {

    HttpClient httpClient = new DefaultHttpClient();

    URL setURI(String path, String apiKey) throws IOException;

    HttpGet getMoviesObject() throws MalformedURLException;

    String getNetworkData() throws IOException;


}
