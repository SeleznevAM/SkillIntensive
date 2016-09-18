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
import com.example.alex.skillintensive.data.managers.DataManager;
import com.example.alex.skillintensive.data.network.req.UserLoginReq;
import com.example.alex.skillintensive.data.network.resp.UserModelResp;
import com.example.alex.skillintensive.util.NetworkStarusChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends BaseActivity implements View.OnClickListener {
    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin, mPassword;
    private CoordinatorLayout mCoordinatorLayout;
    private DataManager mDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mDataManager = DataManager.getInstance();

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
        switch (v.getId()) {
            case R.id.login_btn:
                singIn();
                break;
            case R.id.remember_password_et:
                rememberPassword();
                break;
        }
    }


    private void showSnackBar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void rememberPassword() {
        Intent rememberPasswordIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberPasswordIntent);
    }

    private void loginSuccess(UserModelResp userModel) {

        mDataManager.getPreferenceManager().saveAuthToken(userModel.getData().getToken());
        mDataManager.getPreferenceManager().saveUserId(userModel.getData().getUser().getId());
        saveUserValues(userModel);

        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    /**
     * Бизнес логика нажатия кнопки войти
     */
    private void singIn() {
        if (NetworkStarusChecker.isNetworkAvalible(this)) {
            Call<UserModelResp> call = mDataManager.loginUser(new UserLoginReq(mLogin.getText().toString(), mPassword.getText().toString()));
            call.enqueue(new Callback<UserModelResp>() {
                @Override
                public void onResponse(Call<UserModelResp> call, Response<UserModelResp> response) {
                    if (response.code() == 200) {
                        loginSuccess(response.body());
                    } else if (response.code() == 404) {
                        showSnackBar("НЕверный логи и пароль");
                    } else {
                        showSnackBar("Все провало!");
                    }
                }

                @Override
                public void onFailure(Call<UserModelResp> call, Throwable t) {
                    // TODO: 16.09.2016 Обработать ошибки

                }

            });

        } else {
            showSnackBar("Соединение с сетью недоступно");
        }
    }

    private void saveUserValues(UserModelResp userModel) {
        int[] userValues = {
                userModel.getData().getUser().getProfileValues().getRait(),
                userModel.getData().getUser().getProfileValues().getLinesCode(),
                userModel.getData().getUser().getProfileValues().getProjects(),
        };

        List<String> userProfileValues = new ArrayList<>();
        userProfileValues.add(userModel.getData().getUser().getContacts().getPhone());
        userProfileValues.add(userModel.getData().getUser().getContacts().getEmail());
        userProfileValues.add(userModel.getData().getUser().getContacts().getVk());
        userProfileValues.add(userModel.getData().getUser().getRepositories().getRepo().get(0).getGit());
        userProfileValues.add(userModel.getData().getUser().getPublicInfo().getBio());

        mDataManager.getPreferenceManager().saveUserPhoto(Uri.parse(userModel.getData().getUser().getPublicInfo().getPhoto()));

        mDataManager.getPreferenceManager().saveUserProfileValues(userValues);
        mDataManager.getPreferenceManager().saveUserProfileData(userProfileValues);
    }
}
