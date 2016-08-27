package com.example.alex.skillintensive.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alex.skillintensive.R;
import com.example.alex.skillintensive.data.managers.DataManager;
import com.example.alex.skillintensive.util.ConstantManager;
import com.example.alex.skillintensive.util.RoundedAvatarDrawable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConstantManager.LOG_PREFIX +  " mainActivity";
    private ImageView mPhoneImage;
    private Toolbar mToolbar;
    private DrawerLayout mNAvigationDrawer;
    private CoordinatorLayout mCoordinatorLayout;
    private FloatingActionButton mFab;
    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUSerBio;
    private int mCurrentMode =0; //Переключатель  для опеределения режима редактирования
    private DataManager mDataManager;
    private ImageView mUserAvatar;
    private List<EditText> mUserInfoViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        mPhoneImage = (ImageView) findViewById(R.id.phone_img);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNAvigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_container);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mUserPhone = (EditText) findViewById(R.id.user_phone_et);
        mUserMail = (EditText) findViewById(R.id.user_mail_et);
        mUserVk = (EditText) findViewById(R.id.user_vk_et);
        mUserGit = (EditText) findViewById(R.id.user_git_et);
        mUSerBio = (EditText) findViewById(R.id.user_bio_et);
        mUserAvatar = (ImageView) findViewById(R.id.user_avatar_img);
        mDataManager = DataManager.getInstance();

        /**
         * Заполняем массив Вьюх пользовательских данных! Приоретено использовать библиотеку Butter_knife
         */
        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUSerBio);


        mFab.setOnClickListener(this);
        mPhoneImage.setOnClickListener(this);
        setupToolbar();
        setupDrawer();
        loadUserInfoValue();



        List<String> test = mDataManager.getPreferenceManager().loadUserProfileData();


        if(savedInstanceState==null){

        }else{
            mCurrentMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentMode);
        }
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
        saveUserInfoValue();
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
        outState.putInt(ConstantManager.EDIT_MODE_KEY,mCurrentMode);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.phone_img:
                showProgress();
                break;
            case R.id.fab:
                if(mCurrentMode == 0){
                    changeEditMode(1);
                    showSnackbar("Режим редактирования");
                    mCurrentMode = 1;
                }else{
                    changeEditMode(0);
                    mCurrentMode =0;
                }

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

    /**
     * Переключает режим редактирования
     * @param mode ксли 1 - режим редактирования если 0 режим просмотра
     */
    private void changeEditMode(int mode) {
        if (mode == 1) {
            mFab.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_edit_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setFocusable(false);
                userValue.setEnabled(false);

            }
        }
    }

    /**
     * Загрузка пользовательских данных
     */
    private void loadUserInfoValue(){
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i = 0; i <userData.size() ; i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }
    /**
     * Сохранение пользовательских данных
     */
    private void saveUserInfoValue(){
        List<String> userData  = new ArrayList<>();
        for (EditText userFieldView  : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);
    }
}
