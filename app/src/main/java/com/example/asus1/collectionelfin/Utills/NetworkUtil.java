package com.example.asus1.collectionelfin.Utills;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by asus1 on 2017/10/7.
 */

public class NetworkUtil {

    public  static  boolean getNetWorkOk(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager != null){
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info!= null && info.isConnected()){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return  true;
                }
            }
        }

        return false;
    }
}
