package com.example.asus1.collectionelfin.Utills;

import android.util.Log;
import android.widget.Toast;

import com.example.asus1.collectionelfin.models.UniApiReuslt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luomin on 2017/9/27.
 */

public class HttpUtils {



    public static <T> void  doRuqest(Call<UniApiReuslt<T>> call, final RequestFinishCallBack<T> callBack){

        call.enqueue(new Callback<UniApiReuslt<T>>() {
            @Override
            public void onResponse(Call<UniApiReuslt<T>> call, Response<UniApiReuslt<T>> response) {
                UniApiReuslt<T> reuslt = response.body();
                Log.d("aaa",String.valueOf(response.isSuccessful()));
                Log.d("aaaaaa",String.valueOf(response.code()));

                if(callBack != null && reuslt != null){
                    callBack.getResult(reuslt);
                    Log.d("bbbbb","bbbbbb");

                }


            }

            @Override
            public void onFailure(Call<UniApiReuslt<T>> call, Throwable t) {

                t.printStackTrace();
                if(call != null){
                    callBack.getResult(null);
                }
            }
        });


    }


    /**
     * 用于网络请求的回调
     * @param <T>
     */
    public interface RequestFinishCallBack<T>{

        void getResult(UniApiReuslt<T> apiReuslt);
    }

}
