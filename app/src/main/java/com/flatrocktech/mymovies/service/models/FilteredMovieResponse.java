package com.flatrocktech.mymovies.service.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilteredMovieResponse {
    @SerializedName("page")
    private String page;
    @SerializedName("results")
    private List<Movie> items = null;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Movie> getItems() {
        return items;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
    }
}

