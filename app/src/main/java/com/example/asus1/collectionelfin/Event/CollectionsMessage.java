package com.example.asus1.collectionelfin.Event;

import com.example.asus1.collectionelfin.models.CollectionModel;
import com.example.asus1.collectionelfin.models.CollectionSortModel;

/**
 * Created by asus1 on 2017/10/12.
 */

public class CollectionsMessage {

    private CollectionModel model;


    public CollectionsMessage(CollectionModel model) {
        this.model = model;
    }

    public CollectionModel getModel() {
        return model;
    }

    public void setModel(CollectionModel model) {
        this.model = model;
    }


}
