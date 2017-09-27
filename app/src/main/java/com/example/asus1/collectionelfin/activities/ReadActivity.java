package com.example.asus1.collectionelfin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.asus1.collectionelfin.R;

public class ReadActivity extends AppCompatActivity {

    private ImageView mBack;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        setUpViews();
    }

    private void setUpViews(){
        mBack = (ImageView)findViewById(R.id.iv_back);
        mWebView = (WebView)findViewById(R.id.wv_showArtical);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


}
