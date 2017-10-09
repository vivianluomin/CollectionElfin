package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by asus1 on 2017/10/8.
 */

public interface LoginSerivce {


    @POST("/Collection_elfin_war_exploded/Login")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> Login (@Field("account") String account,
                                     @Field("password") String password
                        );


}
