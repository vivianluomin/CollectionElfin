package com.example.asus1.collectionelfin.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus1 on 2017/10/4.
 */

public class CollectionModel {

    @SerializedName("date")
    private String mDate;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("content")
    private String mContent;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("notes")
    private List<NoteModel> mNotes;

    public CollectionModel(String mUrl) {
        this.mUrl = mUrl;
    }

    public CollectionModel() {

    }


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

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public List<NoteModel> getNotes() {
        return mNotes;
    }

    public void setNotes(List<NoteModel> Notes) {
        this.mNotes = Notes;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String Content) {
        this.mContent = Content;
    }
}
