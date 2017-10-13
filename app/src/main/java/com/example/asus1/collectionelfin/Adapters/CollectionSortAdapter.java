package com.example.asus1.collectionelfin.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.asus1.collectionelfin.Views.CollectionSortView;
import com.example.asus1.collectionelfin.models.CollectionSortModel;

import java.util.List;

/**
 * Created by asus1 on 2017/10/13.
 */

public class CollectionSortAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int mResource;
    private List<String> mSortModels;

    public CollectionSortAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mSortModels = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = new CollectionSortView(mContext);
        }

        ((CollectionSortView)convertView).setData(mSortModels.get(position));


        return convertView;
    }

    @Override
    public int getCount() {
        return mSortModels.size();
    }
}
