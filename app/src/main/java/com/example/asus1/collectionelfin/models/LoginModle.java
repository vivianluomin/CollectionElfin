package com.example.asus1.collectionelfin.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus1 on 2017/10/11.
 */

public class LoginModle {

    @SerializedName("username")
    private  String mUserName;

    @SerializedName("account")
    private String mAccount;

    @SerializedName("password")
    private String mPassword;


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String UserName) {
        this.mUserName = UserName;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String Account) {
        this.mAccount = Account;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String Password) {
        this.mPassword = mPassword;
    }
}
