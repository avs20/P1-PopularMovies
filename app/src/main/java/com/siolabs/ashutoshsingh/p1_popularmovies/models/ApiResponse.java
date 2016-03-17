package com.siolabs.ashutoshsingh.p1_popularmovies.models;

import java.util.List;

/**
 * Created by ashutoshsingh on 16-03-2016.
 */
public class ApiResponse {
    int page;
    private List<MovieResponse> results;

    public int getId() {
        return page;
    }

    public void setId(int id) {
        this.page = id;
    }

    public List<MovieResponse> getResults() {
        return results;
    }

    public void setResults(List<MovieResponse> results) {
        this.results = results;
    }
}
