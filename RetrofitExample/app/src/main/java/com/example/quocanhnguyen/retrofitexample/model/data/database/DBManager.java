package com.example.quocanhnguyen.retrofitexample.model.data.database;

public interface DBManager {

    interface onLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onIncorrectUsername();

        void onIncorrectPassword();

        void onSuccess();
    }

    void Login(String username, String password, onLoginFinishedListener listener);
}
