package com.example.asus1.collectionelfin.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;

/**
 * Created by asus1 on 2017/10/13.
 */

public class CollectionSortView extends RelativeLayout {

    private Context mContext;
    private TextView mName;

    public CollectionSortView(Context context) {
        this(context,null);
    }

    public CollectionSortView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CollectionSortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {

        View.inflate(mContext, R.layout.view_collection_sort_item, this);
        mName = (TextView) findViewById(R.id.tv_collection_sort_name);
    }

    public void setData(String name){
        mName.setText(name);

    }
}
