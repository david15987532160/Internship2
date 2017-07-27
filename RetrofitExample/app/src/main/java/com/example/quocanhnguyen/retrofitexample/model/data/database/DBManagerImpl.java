package com.example.quocanhnguyen.retrofitexample.model.data.database;

import android.os.Handler;
import android.text.TextUtils;

public class DBManagerImpl implements DBManager {

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
    public void findMovieItems(final onFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                listener.onFinished();
            }
        }, 2000);
    }
}
