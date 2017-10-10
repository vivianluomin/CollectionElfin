package com.example.asus1.collectionelfin.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;

/**
 * Created by asus1 on 2017/10/10.
 */

public class SpinnerCollection extends RelativeLayout {

    private Context mContext;
    private TextView mTitleName;

    public SpinnerCollection(Context context) {
        this(context,null);
    }

    public SpinnerCollection(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpinnerCollection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        View.inflate(mContext, R.layout.view_spinner_item,this);
        mTitleName = (TextView)findViewById(R.id.tv_collection_name);
    }

    public void setData(String name){
        mTitleName.setText(name);
    }



}
