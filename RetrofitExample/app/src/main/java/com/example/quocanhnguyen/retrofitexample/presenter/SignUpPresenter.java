package com.example.quocanhnguyen.retrofitexample.presenter;

public interface SignUpPresenter {
    void signUpAccount(String username, String password, String confirm);

    void onDestroy();
}