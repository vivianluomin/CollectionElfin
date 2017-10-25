package com.example.asus1.collectionelfin.service;

import com.example.asus1.collectionelfin.Utills.NetworkUtil;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
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
    private static  int MAX_CACHE_SIZE = 200 * 1024 *1024;



    private static OkHttpClient getOkHttpClient(){
        File cacheFile = new File(SystemManager.getContext().getCacheDir().getAbsolutePath());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(getIntercepter())
                .connectTimeout(60,TimeUnit.SECONDS)
                .cache(new Cache(cacheFile,MAX_CACHE_SIZE))
                .build();

        return  client;

    }




    public static  Retrofit  getRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Gson gson = new GsonBuilder().setLenient().create();


        return builder
                .baseUrl(MainUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttpClient())
                .build();

    }


    private static Interceptor  getIntercepter(){

        CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(0, TimeUnit.SECONDS);
        builder.maxStale(7,TimeUnit.DAYS);
        CacheControl cacheControl = builder.build();

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestbuilder = chain.request()
                        .newBuilder()
                        .addHeader("connection", "Keep-Alive")
                        .addHeader("accept", "*/*")
                        .addHeader("user-agent",
                                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
                        .header("accept-encoding","utf-8")
                        .header("accept-charset","utf-8");
                Request request;

                if(NetworkUtil.getNetWorkOk(SystemManager.getContext())){

                    int maxAge = 0;
                    requestbuilder.header("Cache-Control","public,max-age="+maxAge);
                    request = requestbuilder.build();
                }else {
                    int maxStale = 60*60*24*28;
                    requestbuilder.header("Cache-Control","public,only-if-cached,max-stale="+maxStale);
                    request = requestbuilder.build();
                }


                return chain.proceed(request);
            }
        };
    }


}
