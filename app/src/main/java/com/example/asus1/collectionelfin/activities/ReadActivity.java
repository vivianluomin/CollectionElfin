package com.example.asus1.collectionelfin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;

public class ReadActivity extends AppCompatActivity {

    private ImageView mBack;
    private WebView mWebView;
    private Toolbar mToolbar;
    private TextView mBarTitle;
    private String mCollectionUrl;
    private String mCollectionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = new Intent();
        mCollectionUrl = intent.getStringExtra("url");
        mCollectionTitle = intent.getStringExtra("title");

        setUpViews();
    }

    private void setUpViews() {

    }





}
