package com.example.sam.moviebox.repository.api;

import com.example.sam.moviebox.model.GenreModel;

import java.util.List;

public class GenreResponse {

    private List<GenreModel> genres;
    public List<GenreModel> getGenres(){
        return genres;
    }
    public void setGenres(List<GenreModel> genres){
        this.genres = genres;
    }
    @Override
    public String toString(){
        return "ClassPojo [genres = "+ genres+ "]";
    }
}
