package com.example.quocanhnguyen.retrofitexample.presenter;

public interface FavoritePresenter {
    void onLoadFavoriteMovie();

    void onItemClicked(int position);

    void onItemLongClicked(int position);

    void onDestroy();
}
