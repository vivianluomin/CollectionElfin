package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    @POST("/Collection_elfin_war_exploded/UploadIcon")
    @Multipart
    Call<UniApiReuslt<String>> postIcon(@Part("account") String account,
                                        @Part("icon") RequestBody file
                                        );

    @POST("/Collection_elfin_war_exploded/SendIcon")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> getIcon(@Field("account") String account);

}
