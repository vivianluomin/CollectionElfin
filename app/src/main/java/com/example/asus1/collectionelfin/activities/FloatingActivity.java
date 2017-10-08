package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.asus1.collectionelfin.R;

public class FloatingActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton file;
    private ImageButton write;
    RelativeLayout llayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        llayout = (RelativeLayout)findViewById(R.id.lrelative);
        llayout.setOnClickListener(this);


        Button files = (Button) findViewById(R.id.but_fab_file);
        files.setOnClickListener(this);

        Button writes = (Button) findViewById(R.id.but_fab_write);
        writes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.image_ivfile:
            case R.id.but_fab_file:
                Intent intent = new Intent(FloatingActivity.this,NewfileActivity.class);
                startActivity(intent);
                break;
            case R.id.image_ivwrite:
            case R.id.but_fab_write:
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
