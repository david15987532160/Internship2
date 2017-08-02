package com.example.quocanhnguyen.retrofitexample.presenter;

public interface MainPresenter {

    void onLoadTop_rated();

    void onLoadUpcoming();

    void onLoadPopular();

    void onItemClicked(int position);

    void onItemLongClicked(int position);

    void onDestroy();
}