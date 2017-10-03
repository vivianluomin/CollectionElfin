package com.example.asus1.collectionelfin.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;

/**
 * Created by asus1 on 2017/10/2.
 */

public class ErrorView extends RelativeLayout {

    private Context mContext;
    private TextView mReload;
    private reloadingListener mReloadingListener;

    public  interface  reloadingListener{
        void reload();
    }

    public ErrorView(Context context) {
        this(context,null);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setUpViews();
    }

    private void setUpViews(){
        View.inflate(mContext,R.layout.view_error_layout,this);

        mReload = (TextView)findViewById(R.id.tv_reloading);
        mReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReloadingListener!= null){
                    mReloadingListener.reload();
                }
            }
        });
    }


    public void setReloadingListener(reloadingListener reloadingListener){
        mReloadingListener = reloadingListener;
    }


}
