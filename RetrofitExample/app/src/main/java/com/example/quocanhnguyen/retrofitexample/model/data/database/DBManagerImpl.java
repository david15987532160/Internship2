package com.example.quocanhnguyen.retrofitexample.model.data.database;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiClient;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.model.movie.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBManagerImpl implements DBManager {

    private List<Movie> movies;
    private MovieDetails movieDetails;
    private List<MovieDetails> list = new ArrayList<>();

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
    public void findTop_ratedMovieItems(final onFinishedTop_ratedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(SharedPrefs.API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        movies = response.body().getResults();
                        listener.onFinishedTop_rated(movies);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e("Error!!!", t.toString());
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void findUpcomingMovieItems(final onFinishedUpcomingListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getUpcomingMovies(SharedPrefs.API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        movies = response.body().getResults();
                        listener.onFinishedUpcoming(movies);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e("Error!!!", t.toString());
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void findPopularMovieItems(final onFinishedPopularListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getPopularMovies(SharedPrefs.API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        movies = response.body().getResults();
                        listener.onFinishedPopular(movies);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e("Error!!!", t.toString());
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void findFavoriteMovieItems(final onFinishedFavoriteListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                for (int i = 0; i < SharedPrefs.ID.size(); ++i) {
                    Call<MovieDetails> detailsCall = apiInterface.getMovie_Details(Integer.parseInt(SharedPrefs.ID.get(i)), SharedPrefs.API_KEY);
                    detailsCall.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            movieDetails = response.body();
                            list.add(movieDetails);
                            listener.onFinishedFavorite(list);
                        }

                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {
                            Log.e("Error!", t.toString());
                        }
                    });
                }
            }
        }, 2000);
    }
}