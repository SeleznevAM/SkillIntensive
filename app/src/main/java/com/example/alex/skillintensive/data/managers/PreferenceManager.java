package com.example.alex.skillintensive.data.managers;


import android.content.SharedPreferences;
import android.net.Uri;

import com.example.alex.skillintensive.util.ConstantManager;
import com.example.alex.skillintensive.util.SkillintensiveApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для сохранения и получения SharedPreferences
 */
public class PreferenceManager {
    private SharedPreferences mSharedPreferences;
    private static final String[] USER_FIELDS={ConstantManager.USER_PHONE_KEY,
                                               ConstantManager.USER_MAIL_KEY,
                                               ConstantManager.USER_VK_KEY,
                                               ConstantManager.USER_GIT_KEY,
                                               ConstantManager.USER_BIO_KEY};
    public PreferenceManager() {
        this.mSharedPreferences = SkillintensiveApplication.getSharedPreferences();
    }

    public void saveUserProfileData(List<String> userFields){
        SharedPreferences.Editor editor = mSharedPreferences.edit(); //Локальный эдитор
        for (int i = 0; i <USER_FIELDS.length ; i++) {
            editor.putString(USER_FIELDS[i], userFields.get(i));
        }
        editor.apply();
    }

    public List<String> loadUserProfileData(){
        List<String> userFields = new ArrayList<>();
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_PHONE_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_MAIL_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_VK_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_GIT_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_BIO_KEY, "null"));
        return userFields;
    }

    public void saveUserPhoto (Uri uri){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY, uri.toString());
        editor.apply();
    }

    public Uri loadUserPhoto(){
        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY, "android.resource://com.example.alex.skillintensive/drawable/avatar"));
    }


}
