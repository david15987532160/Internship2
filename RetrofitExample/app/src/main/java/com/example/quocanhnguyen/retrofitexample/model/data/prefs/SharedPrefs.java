package com.example.quocanhnguyen.retrofitexample.model.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefs {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static final String PREFS_NAME = "my_shared_preferences";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final List<String> ID = new ArrayList<>();
    public static final String API_KEY = "1cc34413d9db3cca9838cf168604cc36";
//    public static final String ID = "";

    public SharedPrefs() {
    }

    // Init SharedPreferences
    public static void Init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }

    // put func()
    public static void put(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void put(String key, boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void put(String key, Integer value) {
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void put(String key, Float value) {
        editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void put(String key, Long value) {
        editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }


    // get func()
    public static String get(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public static boolean get(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static Integer get(String key, Integer defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public static Float get(String key, Float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public static Long get(String key, Long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

}
