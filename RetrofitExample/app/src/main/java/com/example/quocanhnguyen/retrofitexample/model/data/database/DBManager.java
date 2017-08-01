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

    interface onFinishedListener {
        
        void onFinished(List<Movie> items);
    }

    void Login(String username, String password, onLoginFinishedListener listener);

    void findMovieItems(onFinishedListener listener);
}
