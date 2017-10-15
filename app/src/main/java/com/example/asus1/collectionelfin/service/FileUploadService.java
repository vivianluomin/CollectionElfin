package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import java.util.List;

import okhttp3.MultipartBody;
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
            @Part("name") String name,
            @Part("time") String time,
            @Part List<MultipartBody.Part> file);


    @POST()
    Call<UniApiReuslt<List<String>>> getFileName(@Field("account") String account);




}
