package com.example.quocanhnguyen.retrofitexample.presenter;

import com.example.quocanhnguyen.retrofitexample.activity.favorite.FavoriteView;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManager;
import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;

import java.util.List;

public class FavoritePresenterImpl implements FavoritePresenter, DBManager.onFinishedFavoriteListener {
    private FavoriteView favoriteView;
    private DBManager dbManager;
    private List<MovieDetails> movieList;

    public FavoritePresenterImpl(FavoriteView favoriteView, DBManager dbManager) {
        this.favoriteView = favoriteView;
        this.dbManager = dbManager;
    }

    @Override
    public void onLoadFavoriteMovie() {
        if (favoriteView != null) {
            favoriteView.showProgress();
        }

        dbManager.findFavoriteMovieItems(this);
    }

    @Override
    public void onItemClicked(int position) {
        if (favoriteView != null) {
            favoriteView.showFavMovieDetail(movieList.get(position));
        }
    }

    @Override
    public void onItemLongClicked(int position) {
        if (favoriteView != null) {
            favoriteView.removeFavMovie(movieList.get(position), position);
        }
    }

    @Override
    public void onDestroy() {
        favoriteView = null;
    }

    @Override
    public void onFinishedFavorite(List<MovieDetails> items) {
        if (favoriteView != null) {
            favoriteView.setFavoriteMovieItem(items);
            movieList = items;
            favoriteView.hideProgress();
        }
    }
}
