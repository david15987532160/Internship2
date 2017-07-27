package com.example.quocanhnguyen.retrofitexample.activity;

import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;

import java.util.List;

public interface MainView {
    void showProgress();

    void hideProgress();

    void setMovieItems(List<Movie> items);
}
