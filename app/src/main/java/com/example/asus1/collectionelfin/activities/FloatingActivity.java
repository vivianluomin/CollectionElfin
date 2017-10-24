package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.asus1.collectionelfin.R;

public class FloatingActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton file;
    private ImageView mImage;
    private String mType;
    RelativeLayout llayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        mType = getIntent().getStringExtra("type");
        llayout = (RelativeLayout)findViewById(R.id.lrelative);
        llayout.setOnClickListener(this);


        Button files = (Button) findViewById(R.id.but_fab_file);
        mImage = (ImageView)findViewById(R.id.image_iv_file) ;
        if(mType.equals("collection")){
            files.setText("新建收藏");
            mImage.setImageResource(R.mipmap.ic_file_white);
        }else{
            files.setText("新建笔记");
            mImage.setImageResource(R.mipmap.ic_fab_write_white);
        }
        mImage.setOnClickListener(this);
        files.setOnClickListener(this);



    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.image_iv_file:
            case R.id.but_fab_file:
                if(mType.equals("collection")){
                    Intent intent = new Intent(FloatingActivity.this,ReceiveActivity.class);
                    startActivity(intent);
                }else {
                    Intent intents = new Intent(FloatingActivity.this,NewnoteActivity.class);
                    startActivity(intents);
                }

                break;
            default:
                if(llayout.getVisibility()==View.VISIBLE){
                    llayout.setVisibility(View.GONE);
                    finish();
                }else{
                    llayout.setVisibility(View.VISIBLE);
                }

        }
    }
}
