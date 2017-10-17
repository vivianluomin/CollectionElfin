package com.example.asus1.collectionelfin.Utills;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.NoteModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by asus1 on 2017/10/11.
 */

public class LoginHelper {

    private static final String KEY_USER_INFO = "com.collectionelfin.user.login.info";
    private static final LoginHelper INSTANCE = new LoginHelper();

    private static LoginModle NowLoginUser;


    private LoginHelper() {
        String loginStr = PerferenceUtil.getStirng(KEY_USER_INFO,null,SystemManager.getContext());
        if(loginStr==null||loginStr.length()<=0){
            NowLoginUser = null;
        }else {
            try {
                NowLoginUser = new GsonBuilder().create().fromJson(loginStr,LoginModle.class);

            }catch (Exception e){
                e.printStackTrace();
                NowLoginUser= null;
            }
        }

    }

    public static LoginHelper getInstance(){
        return INSTANCE;
    }

    public  void  setNowLiginUser(LoginModle modle){

        NowLoginUser  = modle;
        PerferenceUtil.putString(KEY_USER_INFO,new GsonBuilder().create().toJson(modle),SystemManager.getContext());

    }


    public LoginModle getNowLoginUser(){

        return  NowLoginUser;
    }

    public void clearUserInfor(){
        NowLoginUser = null;
        PerferenceUtil.remove(KEY_USER_INFO,SystemManager.getContext());
    }





}
