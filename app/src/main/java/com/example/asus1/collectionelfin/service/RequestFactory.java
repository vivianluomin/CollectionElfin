package com.example.asus1.collectionelfin.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus1 on 2017/9/27.
 */

public class RequestFactory {

    private static  String MainUrl = "http://47.95.207.40";


    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request()
                    .newBuilder()
                    .addHeader("connection", "Keep-Alive")
                    .addHeader("accept", "*/*")
                    .addHeader("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
                    .header("accept-encoding","utf-8")
                    .header("accept-charset","utf-8")
                    .build();



            return chain.proceed(request);
        }
    }).build();


    public static  Retrofit  getRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Gson gson = new GsonBuilder().setLenient().create();


        return builder
                .baseUrl(MainUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }




}
