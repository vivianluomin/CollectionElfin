package com.example.asus1.collectionelfin.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.models.NoteModel;

/**
 * Created by asus1 on 2017/10/7.
 */

public class NoteView extends RelativeLayout {

    private Context mContext;
    private TextView mTitle;
    private TextView mDate;

    public NoteView(Context context) {
        this(context,null);
    }

    public NoteView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setUpView();
    }

    private void  setUpView(){
        View.inflate(mContext, R.layout.view_note_listview_item,this);
        mTitle = (TextView)findViewById(R.id.tv_note_title);
        mDate = (TextView)findViewById(R.id.tv_note_time);
    }

    public void  setData(NoteModel model){
        mTitle.setText(model.getTitle());
        mDate.setText(model.getDate());

    }
}
