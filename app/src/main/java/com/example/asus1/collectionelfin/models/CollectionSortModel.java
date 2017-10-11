package com.example.asus1.collectionelfin.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus1 on 2017/10/11.
 */

public class CollectionSortModel {

    @SerializedName("title")
    private String mTiltle;

    @SerializedName("collections")
    private List<CollectionModel> mCollections;


    public void addCollection(CollectionModel model){
        mCollections.add(model);

    }

    public String getTiltle() {
        return mTiltle;
    }

    public void setTiltle(String Tiltle) {
        this.mTiltle = Tiltle;
    }

    public List<CollectionModel> getCollections() {
        return mCollections;
    }

    public void setmCollections(List<CollectionModel> Collections) {
        this.mCollections = Collections;
    }
}
