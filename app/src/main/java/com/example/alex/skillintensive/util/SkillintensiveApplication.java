package com.example.alex.skillintensive.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class
SkillintensiveApplication extends Application {
    private static SharedPreferences sSharedPreferences;
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);   //Получаем SharedPreferences
    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }
}
