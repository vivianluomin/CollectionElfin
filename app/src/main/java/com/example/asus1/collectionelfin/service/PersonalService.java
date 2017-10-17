package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by asus1 on 2017/10/17.
 */

public interface PersonalService {

    @POST("/Collection_elfin_war_exploded/Modify")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> modifyInfor(@Field("username") String uername,
                                           @Field("account") String account,
                                           @Field("password") String password
                                           );


}
