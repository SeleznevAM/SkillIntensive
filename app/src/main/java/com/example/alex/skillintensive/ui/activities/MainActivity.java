package com.example.alex.skillintensive.ui.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.alex.skillintensive.R;
import com.example.alex.skillintensive.util.ConstantManager;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConstantManager.LOG_PREFIX +  " mainActivity";
    private ImageView mPhoneImage;
    private Toolbar mToolbar;
    private DrawerLayout mNAvigationDrawer;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        mPhoneImage = (ImageView) findViewById(R.id.phone_img);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNAvigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_container);
        mPhoneImage.setOnClickListener(this);

        setupToolbar();
        setupDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.phone_img:
                showProgress();
                break;
        }
    }

    private void showSnackbar(String message){
        Snackbar.make(mCoordinatorLayout, message,Snackbar.LENGTH_LONG).show();
    }

    private void setupToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar  = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp); //Устновить кнопку
            actionBar.setDisplayHomeAsUpEnabled(true);  //ПРоказывать кнопку
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Отрабатывает клик по элементам меню Toolbar
        if(item.getItemId()== android.R.id.home){ //Если элемент меню - это кнопка Home то
            mNAvigationDrawer.openDrawer(GravityCompat.START); //показать боковое меню слева
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawer(){
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mNAvigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
}
