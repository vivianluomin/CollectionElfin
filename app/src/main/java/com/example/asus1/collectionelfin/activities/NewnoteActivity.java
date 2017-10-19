package com.example.asus1.collectionelfin.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.support.v7.widget.Toolbar;

import com.example.asus1.collectionelfin.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewnoteActivity extends BaseActivity {

    private File mFile;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        Intent intent = getIntent();
        String url = intent.getStringExtra("file");
        if(url!=null&&!url.equals("")){
            mFile =  new File(url);
        }

        Toolbar toolBar=(Toolbar)findViewById(R.id.tool_newnote);
        CollapsingToolbarLayout collapsing=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_newnote);
        setSupportActionBar(toolBar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        mEdit = (EditText)findViewById(R.id.et_edittext);
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
        }
        collapsing.setTitle("我的笔记");
        if(mFile !=null){
            setData();
        }
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

    private void setData(){
        try {
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(new FileInputStream(mFile)));

            String s;
            StringBuilder builder = new StringBuilder();
            while((s = reader.readLine())!=null){

                builder.append(s);
            }

            mEdit.setText(builder.toString());

        }catch (IOException e){

        }

    }

}

