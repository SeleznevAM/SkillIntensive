package com.example.alex.skillintensive.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.skillintensive.R;
import com.example.alex.skillintensive.util.ConstantManager;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = ConstantManager.LOG_PREFIX + " Base Activity";
    protected ProgressDialog mProgressDialog;

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    public void showError(String message, Exception error){
        showToast(message);
        Log.e(TAG, String.valueOf(error));
    }

    public void showProgress(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.dialog_splash);
        }
    }

    public void hideProgress(){

    }
}
