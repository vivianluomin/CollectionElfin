package com.example.asus1.collectionelfin.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.asus1.collectionelfin.Event.CollectionSortMessage;
import com.example.asus1.collectionelfin.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NewfileActivity extends  BaseActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfile);
        init();

    }
    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.newfile_tool_bar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
