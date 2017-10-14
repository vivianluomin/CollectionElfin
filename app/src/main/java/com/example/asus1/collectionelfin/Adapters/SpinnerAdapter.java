package com.example.asus1.collectionelfin.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.asus1.collectionelfin.Views.SpinnerCollection;
import com.example.asus1.collectionelfin.models.CollectionSortModel;

import java.util.List;

/**
 * Created by asus1 on 2017/10/10.
 */

public class SpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private int mResource;
    private List<String> mLists;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {

        mContext = context;
        mResource = resource;
        mLists = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = new SpinnerCollection(mContext);
        }

        ((SpinnerCollection)convertView).setData(mLists.get(position));

        return  convertView;
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }
}
