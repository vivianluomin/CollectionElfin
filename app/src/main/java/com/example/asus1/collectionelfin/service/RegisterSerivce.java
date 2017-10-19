package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by heshu on 2017/10/12.
 */
//
public interface RegisterSerivce {


    @POST("/Collection_elfin_war_exploded/Register")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> Login(@Field("username") String username,
                                     @Field("account") String account,
                                     @Field("password") String password
    );


}