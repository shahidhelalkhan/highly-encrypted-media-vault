package com.shahid.medialocker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.shahid.medialocker.NewUserActivity;

public class SharedPrefUtils {
    private static final String TAG = "SharedPref";
    SharedPreferences sharedPreferences;

    public static void changeFirstTime(NewUserActivity newUser, String pin) {
        SharedPreferences sharedPreferences = newUser.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(Constants.IS_FIRST_TIME,0);
        edit.putString(Constants.PIN,pin);
        edit.commit();
        Log.d(TAG, "changeFirstTime: disabled");
    }

    public static void SaveToPref(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.commit();
        Log.d(TAG, "SaveToPref: ");
    }

    public static String getFromPref(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREF,Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(key,null);
        return result;

    }

    public static void delFromPref(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key).commit();
        Log.d(TAG, "delFromPref: Deleted");

    }

    public boolean isFirstTime(Context ctx){
        sharedPreferences = ctx.getSharedPreferences(Constants.APP_PREF,Context.MODE_PRIVATE);

        if(sharedPreferences.getAll().size()==0){
            Log.d(TAG, "isFirstTime: "+sharedPreferences.getAll().size());
            return true;
        }

        Log.d(TAG, "isFirstTime: "+sharedPreferences.getAll().size());



        return false;
    }
}
