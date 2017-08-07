package com.example.quocanhnguyen.retrofitexample.activity.login;

public interface LoginView {
    void showProgressBar();

    void hideProgressBar();

    void setUsernameError();

    void setPasswordError();

    void setincorrectUsernameorPassword();

    void toHomeScreen();
}
