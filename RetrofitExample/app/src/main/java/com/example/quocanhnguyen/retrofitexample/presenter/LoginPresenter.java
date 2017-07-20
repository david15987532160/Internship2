package com.example.quocanhnguyen.retrofitexample.presenter;

public interface LoginPresenter {
    void validateCredential(String username, String password);

    void onDestroy();
}
