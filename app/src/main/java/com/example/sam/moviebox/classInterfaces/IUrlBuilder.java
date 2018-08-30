package com.example.sam.moviebox.classInterfaces;

import java.net.MalformedURLException;
import java.net.URL;

public interface IUrlBuilder {

    URL buildURL(String path, String apiKey) throws MalformedURLException;
    URL buildPosterURL(String path, String backDropPath) throws MalformedURLException;
    URL buildGenreUrl(String path, String apiKey) throws MalformedURLException;
    URL buildUrlWithID(String id, String path, String apiKey)
            throws MalformedURLException;
}
