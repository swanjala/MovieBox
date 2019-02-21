package com.example.sam.moviebox.repository.api;

import com.example.sam.moviebox.model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    private Integer page;
    private List<MovieModel> results = new ArrayList<>();
    @SerializedName(value = "total_results")
    private Integer totalResults;
    @SerializedName(value = "total_pages")
    private Integer totalPages;

    public Integer getPage(){
        return page;
    }
    public void setPage(Integer page){
        this.page = page;
    }
    public List<MovieModel> getResults()
    {
        return results;
    }

    public void setResults(List<MovieModel> results){
        this.results =results;
    }
    public Integer getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(Integer totalResuls){
        this.totalResults = totalResuls;
    }
    public Integer getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
