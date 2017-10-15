package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by asus1 on 2017/10/15.
 */

public interface ProfileService {


    @POST("/Collection_elfin_war_exploded/Modify")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> modifyInfor(@Field("account") String account,
                                           @Field("usernmae") String username,
                                           @Field("password") String password
                                           );

    @Multipart
    @POST("/Collection_elfin_war_exploded/UploadIcon")
    Call<UniApiReuslt<String>> modifyIcon(@Path("account") String account,
                                          @Part("image")RequestBody image);



}
