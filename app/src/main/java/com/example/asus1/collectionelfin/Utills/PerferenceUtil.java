package com.example.asus1.collectionelfin.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by asus1 on 2017/10/14.
 */

//配置文件
public class PerferenceUtil {

    private PerferenceUtil(){

    }

    public static SharedPreferences getPerference(Context context){

        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getStirng(String key, String defaultValue,Context context){
        return getPerference(context).getString(key,defaultValue);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPerference(context).edit();
    }

    public static  void putString(String key,String value,Context context){
        SharedPreferences.Editor editor= getEditor(context);
       editor .putString(key,value);
        editor.commit();
    }

    public static  void remove(String key,Context context){
        getEditor(context).remove(key);
    }

    public static void clear(Context context){
        getEditor(context).clear().apply();
    }

}
