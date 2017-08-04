package com.example.quocanhnguyen.retrofitexample.activity.favorite;

import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;

import java.util.List;

public interface FavoriteView {
    void showProgress();

    void hideProgress();

    void setFavoriteMovieItem(List<MovieDetails> detailsList);

    void showFavMovieDetail(MovieDetails movieDetails);

    void removeFavMovie(List<MovieDetails> detailsList, MovieDetails movieDetails, int position);
}
