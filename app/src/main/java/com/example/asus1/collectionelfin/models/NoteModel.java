package com.example.asus1.collectionelfin.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus1 on 2017/10/4.
 */

public class NoteModel {

    @SerializedName("date")
    private String mDate;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("type")
    private String mType;

    public String getDate() {
        return mDate;
    }

    public void setDate(String Date) {
        this.mDate = Date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }
}
