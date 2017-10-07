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

    @SerializedName("content")
    private String mContent;

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
