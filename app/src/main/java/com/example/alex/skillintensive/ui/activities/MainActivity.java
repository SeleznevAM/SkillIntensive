package com.example.alex.skillintensive.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.alex.skillintensive.R;
import com.example.alex.skillintensive.data.managers.DataManager;
import com.example.alex.skillintensive.util.ConstantManager;
import com.example.alex.skillintensive.util.RoundedAvatarDrawable;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConstantManager.LOG_PREFIX + " mainActivity";
    private ImageView mPhoneImage;
    private Toolbar mToolbar;
    private DrawerLayout mNAvigationDrawer;
    private CoordinatorLayout mCoordinatorLayout;
    private FloatingActionButton mFab;
    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUSerBio;
    private int mCurrentMode = 0; //Переключатель  для опеределения режима редактирования
    private DataManager mDataManager;
    private ImageView mUserAvatar, mPlaceholderPhoto, mProfileImage;
    private AppBarLayout mAppBarLayout;
    private RelativeLayout mProfilePlaceholder;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private List<EditText> mUserInfoViews;
    private File mPhotoFile = null;
    private AppBarLayout.LayoutParams mAppBarParams = null;
    private Uri mSelectedImage = null;

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
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mPlaceholderPhoto = (ImageView) findViewById(R.id.placeholder_photo_img);
        mProfileImage = (ImageView) findViewById(R.id.user_photo_img);
        mDataManager = DataManager.getInstance();
        mProfilePlaceholder = (RelativeLayout) findViewById(R.id.profile_placeholder);

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
        mPlaceholderPhoto.setOnClickListener(this);
        setupToolbar();
        setupDrawer();
        loadUserInfoValue();
        Picasso.with(this).load(mDataManager.getPreferenceManager().loadUserPhoto()).placeholder(R.drawable.avatar).into(mProfileImage);


        List<String> test = mDataManager.getPreferenceManager().loadUserProfileData();


        if (savedInstanceState == null) {

        } else {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConstantManager.REQUEST_GALLERY_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    mSelectedImage = data.getData();
                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile != null) {
                    mSelectedImage = Uri.fromFile(mPhotoFile);
                    insertProfileImage(mSelectedImage);
                }
                break;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentMode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_img:
                showProgress();
                break;
            case R.id.fab:
                if (mCurrentMode == 0) {
                    changeEditMode(1);
                    showSnackbar("Режим редактирования");
                    mCurrentMode = 1;
                } else {
                    changeEditMode(0);
                    mCurrentMode = 0;
                }

                break;
            case R.id.placeholder_photo_img: {
                // TODO: 30.08.2016  Реализовать выбор инструмента откуда загружить фото
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                showSnackbar("Hf,jnftn");
                break;
            }
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        mAppBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams(); //Получение параметров КолапсингТулбара
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp); //Устновить кнопку
            actionBar.setDisplayHomeAsUpEnabled(true);  //ПРоказывать кнопку
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Отрабатывает клик по элементам меню Toolbar
        if (item.getItemId() == android.R.id.home) { //Если элемент меню - это кнопка Home то
            mNAvigationDrawer.openDrawer(GravityCompat.START); //показать боковое меню слева
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawer() {
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
     *
     * @param mode ксли 1 - режим редактирования если 0 режим просмотра
     */
    private void changeEditMode(int mode) {
        if (mode == 1) {
            mFab.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                showProfilePlaceholder();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT); // сделать цвет текста прозрачным
                lockToolbar();
            }
        } else {
            mFab.setImageResource(R.drawable.ic_edit_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setFocusable(false);
                userValue.setEnabled(false);
                hideProfilePlaceholder();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
                unlockToolbar();
            }
        }
    }

    /**
     * Загрузка пользовательских данных
     */
    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    /**
     * Сохранение пользовательских данных
     */
    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);
    }

    private void hideProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.GONE);
    }

    private void showProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.VISIBLE);
    }

    private void lockToolbar() {
        mAppBarLayout.setExpanded(true, true);  //Программное указание в каком положени должен находится Аппбар true - раскрыт, афдыу - схлопнут
        mAppBarParams.setScrollFlags(0);  //Убрать все скрол флаги
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    private void unlockToolbar() {
        mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }


    /**
     * Метод для создания контекстного меню
     *
     * @param id константа идентифицирующая какое меню надо вызывать
     * @return
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Log.d(TAG, "onCreateDialog");
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String selectItems[] = {getString(R.string.user_profile_dialog_gallery), getString(R.string.user_profile_dialog_camera), getString(R.string.user_profile_dialog_cancel)}; //массив пунктов меню
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.user_profile_dialog_title);
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiseItem) {
                        switch (choiseItem) {
                            case 0:
                                // TODO: 30.08.2016  Загрузить из галереи
                                loadPhotoFromGallery();
                                showSnackbar("Загрузить из галереи");
                                break;
                            case 1:
                                // TODO: 30.08.2016 Загрузить из камеры
                                loadPhotoFromCamera();
                                showSnackbar("Загрузить фото из камеры");
                                break;
                            case 2:
                                // TODO: 30.08.2016 Оменить
                                dialog.cancel();
                                showSnackbar("Отмена");
                                break;
                        }
                    }
                });
                return builder.create();
            default:
                return null;
        }
    }

    private void loadPhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {//Проверка разрешений для андроил выше версии 5
            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                mPhotoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: 31.08.2016 Обработка ошибок
            }

            if (mPhotoFile != null) {
                // TODO: 31.08.2016 Передать файл в интент
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
            }
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, ConstantManager.CAMERA_REQUEST_PERMISSION_CODE);
            Snackbar.make(mCoordinatorLayout, "Для корректной работы необходимо дать требуемые разрешения", Snackbar.LENGTH_LONG)
                    .setAction("Разрешить", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openApplicationSettings();
                        }
                    }).show();
        }

    }

    /*
    Метод для обработки разрешений от пользователя
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == ConstantManager.CAMERA_REQUEST_PERMISSION_CODE && grantResults.length == 2){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                // TODO: 03.09.2016 тут обрабатываем разрешение (разрешение получено)
            }
            if(grantResults[1]==PackageManager.PERMISSION_GRANTED){
                // TODO: 03.09.2016 тут обрабатываем разрешение (разрешение получено)
            }
        }
    }

    private void loadPhotoFromGallery() {

        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent, getString(R.string.user_profile_chooser_message)), ConstantManager.REQUEST_GALLERY_PICTURE);

    }

    /*
    Метод для создания файла в который будет запсана фотография с камеры
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //штамп времени для уникальности названия файла
        String imageFileName = "JPEG_" + timeStamp + "_"; // Создали название файла
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); // Задаем URI где будет находиться файл
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());
        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return image;
    }

    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(this).load(selectedImage).into(mProfileImage);
        mDataManager.getPreferenceManager().saveUserPhoto(selectedImage);
    }

    /*
    Вспомогательный метод для получения настроек RunTimePermission
     */
    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));

        startActivityForResult(appSettingsIntent, ConstantManager.PERMISSION_REQUEST_SETTING_CODE);
    }
}
