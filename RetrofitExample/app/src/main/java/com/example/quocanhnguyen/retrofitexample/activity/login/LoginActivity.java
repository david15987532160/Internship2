package com.example.quocanhnguyen.retrofitexample.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.activity.MainActivity;
import com.example.quocanhnguyen.retrofitexample.activity.signup.SignUpActivity;
import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.presenter.LoginPresenter;
import com.example.quocanhnguyen.retrofitexample.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.username)
    EditText edtUsername;
    @BindView(R.id.password)
    EditText edtPassword;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.signUp_link).setOnClickListener(this);
        SharedPrefs.Init(getApplicationContext());
        edtUsername.setText(SharedPrefs.get(SharedPrefs.USERNAME_KEY, ""));
//        edtUsername.setText(SharedPrefs.USERNAME_KEY);
        edtPassword.setText(SharedPrefs.get(SharedPrefs.PASSWORD_KEY, ""));
//        edtPassword.setText(SharedPrefs.PASSWORD_KEY);
        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onBackPressed() {
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
        switch (v.getId()) {
            case R.id.buttonLogin:
                if (checkBox.isChecked()) {
                    SharedPrefs.put(SharedPrefs.USERNAME_KEY, edtUsername.getText().toString().trim());
                    SharedPrefs.put(SharedPrefs.PASSWORD_KEY, edtPassword.getText().toString());
                }
                loginPresenter.validateCredential(edtUsername.getText().toString(), edtPassword.getText().toString());
                break;

            case R.id.signUp_link:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
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
    public void setincorrectUsernameorPassword() {
        Toast.makeText(this, getString(R.string.incorrect_username_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toHomeScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}