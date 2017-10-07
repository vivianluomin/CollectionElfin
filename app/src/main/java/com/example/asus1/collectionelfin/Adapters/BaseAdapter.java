package com.example.asus1.collectionelfin.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by asus1 on 2017/10/7.
 */

public class BaseAdapter<T> extends ArrayAdapter<T> {

    private Context mContext;
    private int mResource;
    private List<T> mLits;

    public BaseAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mLits = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(mResource,parent,false);
        }


        return convertView;
    }

    @Override
    public int getCount() {
        return mLits.size();
    }
}
