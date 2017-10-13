package com.example.asus1.collectionelfin.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus1 on 2017/10/13.
 */

public class NoteSortModle {

    @SerializedName("name")
    private String mTitle;

    private List<NoteModel> mNoteModelList;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public List<NoteModel> getNoteModelList() {
        return mNoteModelList;
    }

    public void setNoteModelList(List<NoteModel> NoteModelList) {
        this.mNoteModelList = NoteModelList;
    }
}
