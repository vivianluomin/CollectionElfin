package com.example.asus1.collectionelfin.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.asus1.collectionelfin.Views.CollectionView;
import com.example.asus1.collectionelfin.models.CollectionModel;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by asus1 on 2017/10/4.
 */

public class CollectionAdapter extends ArrayAdapter<CollectionModel> {

    private Context mContext;
    private int mResource;
    private List<CollectionModel> mCollections;

    public CollectionAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CollectionModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mCollections = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CollectionModel model = getItem(position);
        if(convertView == null){
            convertView = new CollectionView(mContext);
        }

        ((CollectionView)convertView).setData(model);
        return convertView;
    }



    @Override
    public int getCount() {
        return mCollections.size();
    }
}
