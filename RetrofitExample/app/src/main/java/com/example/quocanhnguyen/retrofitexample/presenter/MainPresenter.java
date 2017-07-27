package com.example.quocanhnguyen.retrofitexample.presenter;

import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.movie.MoviesResponse;

import retrofit2.Call;

public interface MainPresenter {

    void onResume();

    void loadMovieList(ApiInterface apiInterface, Call<MoviesResponse> call);

    void onItemClicked(int position);

    void onDestroy();
}
