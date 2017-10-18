package com.example.asus1.collectionelfin.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.asus1.collectionelfin.Views.NoteSortView;
import com.example.asus1.collectionelfin.models.NoteSortModle;

import java.util.List;

/**
 * Created by asus1 on 2017/10/13.
 */

public class NoteSortAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mNoteSortModles;

    public NoteSortAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mNoteSortModles = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = new NoteSortView(mContext);
        }

        ((NoteSortView)convertView).setData(mNoteSortModles.get(position));

        return convertView;
    }


    @Override
    public int getCount() {
        return mNoteSortModles.size();
    }
}
