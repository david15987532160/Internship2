package com.example.quocanhnguyen.retrofitexample.presenter;

import com.example.quocanhnguyen.retrofitexample.activity.MainView;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManager;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;

import java.util.List;

public class MainPresenterImpl implements MainPresenter, DBManager.onFinishedTop_ratedListener, DBManager.onFinishedUpcomingListener, DBManager.onFinishedPopularListener {
    private MainView mainView;
    private DBManager dbManager;
    private List<Movie> movieList;

    public MainPresenterImpl(MainView mainView, DBManager dbManager) {
        this.mainView = mainView;
        this.dbManager = dbManager;
    }

    @Override
    public void onLoadTop_rated() {
        if (mainView != null) {
            mainView.showProgress();
        }
        dbManager.findTop_ratedMovieItems(this);
    }

    @Override
    public void onLoadUpcoming() {
        if (mainView != null) {
            mainView.showProgress();
        }
        dbManager.findUpcomingMovieItems(this);
    }

    @Override
    public void onLoadPopular() {
        if (mainView != null) {
            mainView.showProgress();
        }
        dbManager.findPopularMovieItems(this);
    }

    @Override
    public void onItemClicked(int position) {
        if (mainView != null) {
            mainView.showFragMovieDetail(movieList.get(position));
        }
    }

    @Override
    public void onItemLongClicked(int position) {
        if (mainView != null) {
            mainView.showMovieDetail(movieList.get(position));
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinishedTop_rated(List<Movie> items) {
        if (mainView != null) {
            mainView.setMovieItems(items);
            movieList = items;
            mainView.hideProgress();
        }
    }

    @Override
    public void onFinishedUpcoming(List<Movie> items) {
        if (mainView != null) {
            mainView.setMovieItems(items);
            movieList = items;
            mainView.hideProgress();
        }
    }

    @Override
    public void onFinishedPopular(List<Movie> items) {
        if (mainView != null) {
            mainView.setMovieItems(items);
            movieList = items;
            mainView.hideProgress();
        }
    }

//    public MainView getMainView() {
//        return mainView;
//    }
}