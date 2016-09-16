package com.example.alex.skillintensive.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.skillintensive.R;

public class AuthActivity extends BaseActivity implements View.OnClickListener {
    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin, mPassword;
    private CoordinatorLayout mCoordinatorLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mRememberPassword = (TextView) findViewById(R.id.remember_password_et);
        mLogin = (EditText) findViewById(R.id.login_et);
        mPassword = (EditText) findViewById(R.id.password_et);
        mSignIn = (Button) findViewById(R.id.login_btn);

        mRememberPassword.setOnClickListener(this);
        mSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                loginSuccess();
                break;
            case R.id.remember_password_et:
                rememberPassword();
                break;
        }
    }


    private void showSnackBar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG);
    }
    private void rememberPassword(){
        Intent rememberPasswordIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberPasswordIntent);
    }

    private void loginSuccess(){
        showSnackBar("Вход выполнен");
    }

    /**
     * Бизнес логика нажатия кнопки войти
     */
    private void singIn(){

    }
}
