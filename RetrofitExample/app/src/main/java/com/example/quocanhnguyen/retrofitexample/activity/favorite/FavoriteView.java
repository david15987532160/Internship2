package com.example.quocanhnguyen.retrofitexample.activity.favorite;

import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;

import java.util.List;

public interface FavoriteView {
    void showProgress();

    void hideProgress();

    void setFavoriteMovieItem(List<MovieDetails> movie);

    void showFavMovieDetail(MovieDetails movie);

    void removeFavMovie(MovieDetails movie, int position);
}
