package com.example.asus1.collectionelfin.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.models.CollectionModel;

/**
 * Created by asus1 on 2017/10/7.
 */

public class CollectionView extends RelativeLayout {

    private Context mContext;
    private TextView mTitle;
    private TextView mSummary;

    public CollectionView(Context context) {
        this(context,null);
    }

    public CollectionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CollectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setUpViews();

    }

    private void  setUpViews(){

        View.inflate(mContext, R.layout.view_collection_listview_item,this);
        mTitle = (TextView)findViewById(R.id.tv_collection_title);
        mSummary = (TextView)findViewById(R.id.tv_summary);

    }

    public void setData(CollectionModel model){

        if(model.getTitle() != null){
            mTitle.setText(model.getTitle());
            mSummary.setText(model.getContent());
        }


    }
}
