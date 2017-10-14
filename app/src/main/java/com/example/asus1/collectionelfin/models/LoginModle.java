package com.example.asus1.collectionelfin.models;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by asus1 on 2017/10/11.
 */

public class LoginModle {

    @SerializedName("username")
    private  String UserName;

    @SerializedName("account")
    private String Account;

    @SerializedName("password")
    private String Password;

    @SerializedName("icon")
    private String Icon;


    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
