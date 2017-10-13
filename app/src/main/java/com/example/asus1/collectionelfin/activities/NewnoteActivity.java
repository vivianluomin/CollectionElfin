package com.example.asus1.collectionelfin.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.support.v7.widget.Toolbar;

import com.example.asus1.collectionelfin.R;

public class NewnoteActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        Toolbar toolBar=(Toolbar)findViewById(R.id.tool_newnote);
        CollapsingToolbarLayout collapsing=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_newnote);
        setSupportActionBar(toolBar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
        }
        collapsing.setTitle("我的笔记");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

