package com.example.quocanhnguyen.retrofitexample.activity.signup;

public interface SignUpView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void setConfirmError();

    void setConfirmNotMatch();

    void navigateToLogin();
}