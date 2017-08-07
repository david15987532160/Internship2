package com.example.quocanhnguyen.retrofitexample.presenter;

import com.example.quocanhnguyen.retrofitexample.activity.signup.SignUpView;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManager;

public class SignUpPresenterImpl implements SignUpPresenter, DBManager.onSignUpFinished {
    private SignUpView signUpView;
    private DBManager dbManager;

    public SignUpPresenterImpl(SignUpView signUpView, DBManager dbManager) {
        this.signUpView = signUpView;
        this.dbManager = dbManager;
    }

    @Override
    public void signUpAccount(String username, String password, String confirm) {
        if (signUpView != null) {
            signUpView.showProgress();
        }

        dbManager.SignUp(username, password, confirm, this);
    }

    @Override
    public void onDestroy() {
        signUpView = null;
    }

    @Override
    public void onUsernameError() {
        if (signUpView != null) {
            signUpView.setUsernameError();
            signUpView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (signUpView != null) {
            signUpView.setPasswordError();
            signUpView.hideProgress();
        }
    }

    @Override
    public void onConfirmError() {
        if (signUpView != null) {
            signUpView.setConfirmError();
            signUpView.hideProgress();
        }
    }

    @Override
    public void onConfirmNotMatch() {
        if (signUpView != null) {
            signUpView.setConfirmNotMatch();
            signUpView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (signUpView != null) {
            signUpView.navigateToLogin();
        }
    }
}