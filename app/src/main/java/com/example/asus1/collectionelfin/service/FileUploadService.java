package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by asus1 on 2017/10/15.
 */

public interface FileUploadService {


    @Multipart
    @POST()
    Call<UniApiReuslt<String>> upLoadFile(
            @Field("account") String account,
            @Field("name") String name,
            @Field("time") String time,
            @Part List<MultipartBody.Part> file);


    @POST()
    Call<UniApiReuslt<List<String>>> getFileName(@Field("account") String account);

    @Multipart
    @POST("/Collection_elfin_war_exploded/UploadIcon")
    Call<UniApiReuslt<String>> upLoadIcon(@Field("account") String account,
                                          @Part RequestBody body
                                          );






}
