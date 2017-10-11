package com.example.asus1.collectionelfin.Utills;

import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.NoteModel;

/**
 * Created by asus1 on 2017/10/11.
 */

public class LoginHelper {

    private static LoginModle NowLoginUser;

    public  static  void  setNowLiginUser(LoginModle modle){

        NowLoginUser  = modle;

    }


    public static LoginModle getNowLoginUser(){

        return  NowLoginUser;
    }

}
