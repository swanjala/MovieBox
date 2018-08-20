package com.example.sam.moviebox.networkUtils;

import java.net.MalformedURLException;
import java.net.URL;

public interface NetworkInterface {
//    final  = null;

    URL setURI(String BASE_MOVIE_API_V3_URL, String path, String apiKey) throws MalformedURLException;
}
