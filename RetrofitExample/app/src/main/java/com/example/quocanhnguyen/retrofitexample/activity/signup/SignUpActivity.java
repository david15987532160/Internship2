package com.example.quocanhnguyen.retrofitexample.activity.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.activity.login.LoginActivity;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManagerImpl;
import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.presenter.SignUpPresenter;
import com.example.quocanhnguyen.retrofitexample.presenter.SignUpPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpView {
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtPasswordConfirm)
    EditText edtPasswordConfirm;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.link_to_login)
    TextView txtvLogin;
    @BindView(R.id.progressSignUp)
    ProgressBar progressBar;
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        presenter = new SignUpPresenterImpl(this, new DBManagerImpl());

        clickEvent();
    }

    private void clickEvent() {
        txtvLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.link_to_login:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;

            case R.id.btnSignUp:
                presenter.signUpAccount(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim(), edtPasswordConfirm.getText().toString().trim());
                break;

            default:
                break;
        }
    }


    // design MVP pattern

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
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
    public void setConfirmError() {
        edtPasswordConfirm.setError(getString(R.string.confirm_password_error));
    }

    @Override
    public void setConfirmNotMatch() {
        edtPasswordConfirm.setError(getString(R.string.password_not_match));
    }

    @Override
    public void navigateToLogin() {
        SharedPrefs.USERNAME_SIGNUP = edtUsername.getText().toString().trim();
        SharedPrefs.PASSWORD_SIGNUP = edtPassword.getText().toString().trim();
        Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }
}