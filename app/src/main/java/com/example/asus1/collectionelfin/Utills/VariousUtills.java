package com.example.asus1.collectionelfin.Utills;

/**
 * Created by asus1 on 2017/10/12.
 */

public class VariousUtills {

    public static String handleUrl(String url){

        String Url  = "" ;

        Url = url.substring(url.indexOf("http"),url.lastIndexOf(' '));


        return Url;

    }

}
