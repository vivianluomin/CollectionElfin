package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by asus1 on 2017/10/18.
 */

public interface NoteSerivce {


    @POST("/Collection_elfin_war_exploded/SendAllNoteType")
    @FormUrlEncoded
    Call<UniApiReuslt<List<String>>>  getNoteSorts(@Field("account") String account);


    @POST("/Collection_elfin_war_exploded/SnedTypeNoteAll")
    @FormUrlEncoded
    Call<UniApiReuslt<List<String>>>  getNotes(@Field("account") String account,
                                               @Field("type")  String type
                                               );

    @POST("/Collection_elfin_war_exploded/UploadNote")
    @Multipart
    Call<UniApiReuslt<String>> uploadNotes(@Field("account") String account,
                                            @Field("type") String type,
                                            @Part("note")MultipartBody file
                                            );

    @POST("/Collection_elfin_war_exploded/DeleteNote ")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> deletNotes(@Field("account") String account,
                                          @Field("type") String type,
                                          @Field("note") String noteName
                                          );


    @POST("/Collection_elfin_war_exploded/SendNote")
    @FormUrlEncoded
    Call<RequestBody>  downloadNotes(@Field("account") String account,
                                     @Field("type") String type,
                                     @Field("note") String noteName
                                     );

}
