package com.example.asus1.collectionelfin.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.asus1.collectionelfin.R;

public class NewnoteActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        ImageButton button = (ImageButton)findViewById(R.id.but_finish_writenote);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                
            }
        });
        init();

    }
    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.newnote_tool_bar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
