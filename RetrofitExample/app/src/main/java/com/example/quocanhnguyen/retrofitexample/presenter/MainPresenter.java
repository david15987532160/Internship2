package com.example.quocanhnguyen.retrofitexample.presenter;

public interface MainPresenter {

    void onResume();

    void loadMovieList();

    void onItemClicked(int position);

    void onDestroy();
}
