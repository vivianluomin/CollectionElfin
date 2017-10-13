package com.example.asus1.collectionelfin.Utills;

import android.content.Context;

/**
 * Created by asus1 on 2017/10/13.
 */

public class SystemManager {

    private static Context mContext;

    public static void initContext(Context context){
        mContext = context;
    }

    public static Context getContext(){
        return mContext;
    }
}
