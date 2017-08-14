package com.example.quocanhnguyen.retrofitexample.presenter;

import android.content.Context;

import com.example.quocanhnguyen.retrofitexample.activity.login.LoginView;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManager;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManagerImpl;

public class LoginPresenterImpl implements LoginPresenter, DBManager.onLoginFinishedListener {

    private LoginView loginView;
    private DBManager dbManager;
    private Context context;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.dbManager = new DBManagerImpl();
    }

    @Override
    public void validateCredential(String username, String password) {
        if (loginView != null) {
            loginView.showProgressBar();
        }
        dbManager.Login(username, password, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgressBar();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgressBar();
        }
    }

    @Override
    public void onIncorrectUsernameorPassword() {
        if (loginView != null) {
            loginView.setincorrectUsernameorPassword();
            loginView.hideProgressBar();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
//            if (!dbManager.readFromFile(context).isEmpty())
//                SharedPrefs.ID.add(new String(dbManager.readFromFile(context)));
            loginView.toHomeScreen();
        }
    }
}
