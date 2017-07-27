package com.example.quocanhnguyen.retrofitexample.presenter;

import com.example.quocanhnguyen.retrofitexample.activity.MainView;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManager;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.model.movie.MoviesResponse;

import java.util.List;

import retrofit2.Call;

public class MainPresenterImpl implements MainPresenter, DBManager.onFinishedListener {
    private MainView mainView;
    private DBManager dbManager;

    public MainPresenterImpl(MainView mainView, DBManager dbManager) {
        this.mainView = mainView;
        this.dbManager = dbManager;
    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        dbManager.findMovieItems(this);
    }

    @Override
    public void loadMovieList(ApiInterface apiInterface, Call<MoviesResponse> call) {

    }

    @Override
    public void onItemClicked(int position) {
        if (mainView != null) {

        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<Movie> items) {
        if (mainView != null) {
            mainView.setMovieItems(items);
            mainView.hideProgress();
        }
    }

    public MainView getMainView() {
        return mainView;
    }
}
