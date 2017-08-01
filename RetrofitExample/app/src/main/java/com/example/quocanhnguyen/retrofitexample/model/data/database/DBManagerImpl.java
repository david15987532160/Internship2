package com.example.quocanhnguyen.retrofitexample.model.data.database;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiClient;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.model.movie.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBManagerImpl implements DBManager {

    private List<Movie> movies;

    @Override
    public void Login(final String username, final String password, final onLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(username)) {
                    listener.onUsernameError();
                    error = true;
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    error = true;
                    return;
                }
                if (username.equals("quocanh")) {
                    listener.onSuccess();
                } else {
                    listener.onIncorrectUsername();
                    error = true;
                    return;
                }
                if (password.equals("123")) {
                    listener.onSuccess();
                } else {
                    listener.onIncorrectPassword();
                    error = true;
                    return;
                }
                if (!error)
                    listener.onSuccess();
            }
        }, 2000);
    }

    @Override
    public void findMovieItems(final onFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(SharedPrefs.API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        movies = response.body().getResults();
                        listener.onFinished(movies);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e("Error!!!", t.toString());
                    }
                });
            }
        }, 2000);
    }
}