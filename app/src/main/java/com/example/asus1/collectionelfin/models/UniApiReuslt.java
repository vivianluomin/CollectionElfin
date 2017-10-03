package com.example.asus1.collectionelfin.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus1 on 2017/9/27.
 */

public class UniApiReuslt<T>{

    //数据


    @SerializedName("status")
    protected int mStatus;


    @SerializedName("msg")
    protected String mMessage;

    @SerializedName("data")
    protected T mData;

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public T getmData() {
        return mData;
    }

    public void setmData(T mData) {
        this.mData = mData;
    }

}
