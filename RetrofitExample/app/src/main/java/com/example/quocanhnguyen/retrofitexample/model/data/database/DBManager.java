package com.example.quocanhnguyen.retrofitexample.model.data.database;

import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;

import java.util.List;

public interface DBManager {

    interface onLoginFinishedListener {

        void onUsernameError();

        void onPasswordError();

        void onIncorrectUsername();

        void onIncorrectPassword();

        void onSuccess();
    }

    interface onFinishedTop_ratedListener {
        
        void onFinishedTop_rated(List<Movie> items);
    }

    interface onFinishedUpcomingListener {
        void onFinishedUpcoming(List<Movie> items);
    }

    interface onFinishedPopularListener {
        void onFinishedPopular(List<Movie> items);
    }

    void Login(String username, String password, onLoginFinishedListener listener);

    void findTop_ratedMovieItems(onFinishedTop_ratedListener listener);

    void findUpcomingMovieItems(onFinishedUpcomingListener listener);

    void findPopularMovieItems(onFinishedPopularListener listener);
}