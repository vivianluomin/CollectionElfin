package com.example.asus1.collectionelfin.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.models.CollectionModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by asus1 on 2017/10/7.
 */

public class CollectionView extends RelativeLayout {

    private Context mContext;
    private TextView mTitle;
    private TextView mSummary;
    private TextView mTime;

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
        mTime = (TextView)findViewById(R.id.tv_art_time);

    }

    public void setData(CollectionModel model){

        mTitle.setText(model.getTitle());
        mSummary.setText(model.getContent());
        mTime.setText(model.getDate());


    }








}
