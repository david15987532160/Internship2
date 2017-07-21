package com.example.quocanhnguyen.retrofitexample.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.activity.MainActivity;
import com.example.quocanhnguyen.retrofitexample.presenter.LoginPresenter;
import com.example.quocanhnguyen.retrofitexample.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {
    String USERNAME_KEY = "username";
    String PASSWORD_KEY = "password";

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.username)
    EditText edtUsername;
    @BindView(R.id.password)
    EditText edtPassword;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    private LoginPresenter loginPresenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        sharedPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);
        edtUsername.setText(sharedPreferences.getString(USERNAME_KEY, ""));
        edtPassword.setText(sharedPreferences.getString(PASSWORD_KEY, ""));

        loginPresenter = new LoginPresenterImpl(this);

    }

    @Override
    public void onBackPressed() {
//        System.exit(0);
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (checkBox.isChecked()) {
            editor = sharedPreferences.edit();
            editor.putString(USERNAME_KEY, edtUsername.getText().toString().trim());
            editor.putString(PASSWORD_KEY, edtPassword.getText().toString());
            editor.commit();
        }
        loginPresenter.validateCredential(edtUsername.getText().toString(), edtPassword.getText().toString());
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        edtUsername.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        edtPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void setincorrectUsername() {
        Toast.makeText(this, getString(R.string.incorrect_username_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setincorrectPassword() {
        Toast.makeText(this, getString(R.string.incorrect_username_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toHomeScreen() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
