package com.example.asus1.collectionelfin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.asus1.collectionelfin.R;

public class NewfileActivity extends  BaseActivity {
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfile);
        mBack = (ImageView)findViewById(R.id.iv_newfile_back_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}