package com.example.quocanhnguyen.retrofitexample.presenter;

public interface MainPresenter {

    void onLoadList();

    void onItemClicked(int position);

    void onItemLongClicked(int position);

    void onDestroy();
}
