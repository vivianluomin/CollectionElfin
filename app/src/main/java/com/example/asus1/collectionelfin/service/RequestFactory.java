package com.example.asus1.collectionelfin.service;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus1 on 2017/9/27.
 */

public class RequestFactory {

    private static  String MainUrl = "";




    public static  Retrofit  getRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder();

        return builder
                .baseUrl(MainUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

}
