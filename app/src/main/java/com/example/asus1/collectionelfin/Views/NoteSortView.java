package com.example.asus1.collectionelfin.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;

/**
 * Created by asus1 on 2017/10/13.
 */

public class NoteSortView extends RelativeLayout {

    private Context mContext;
    private TextView mName;

    public NoteSortView(Context context) {
        this(context,null);
    }

    public NoteSortView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoteSortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        View.inflate(mContext, R.layout.view_note_sort_litem,this);
        mName = (TextView)findViewById(R.id.tv_note_sort_name);
    }

    public void setData(String name){
        mName.setText(name);
    }
}
