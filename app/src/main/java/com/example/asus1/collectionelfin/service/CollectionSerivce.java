package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.models.CollectionModel;
import com.example.asus1.collectionelfin.models.CollectionSortModel;
import com.example.asus1.collectionelfin.models.UniApiReuslt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by asus1 on 2017/10/12.
 */

public interface CollectionSerivce {

    @POST("/Collection_elfin_war_exploded/UploadURL")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> postLink(@Field("account") String account,
                                        @Field("type") String sort,
                                        @Field("url") String url
                                        );


    @POST("/Collection_elfin_war_exploded/SendAllType")
    @FormUrlEncoded
    Call<UniApiReuslt<List<String>>> getCollectionSort(@Field("account") String account);


    @POST("/Collection_elfin_war_exploded/SendTypeAll")
    @FormUrlEncoded
    Call<UniApiReuslt<List<CollectionModel>>> getCollections(@Field("account") String account,
                                                             @Field("type") String type
                                                                );


    @POST("/Collection_elfin_war_exploded/DeleteUrl")
    @FormUrlEncoded
    Call<UniApiReuslt<String>> deletCollections(@Field("account") String account,
                                                @Field("type") String type,
                                                @Field("url") String urlName
    );
}
