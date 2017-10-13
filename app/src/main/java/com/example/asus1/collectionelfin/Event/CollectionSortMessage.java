package com.example.asus1.collectionelfin.Event;

import com.example.asus1.collectionelfin.models.CollectionSortModel;

/**
 * Created by asus1 on 2017/10/13.
 */

public class CollectionSortMessage {

    private String model;

    public CollectionSortMessage(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
