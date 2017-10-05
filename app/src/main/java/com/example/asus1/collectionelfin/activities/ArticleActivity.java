package com.example.asus1.collectionelfin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.asus1.collectionelfin.R;

public class ArticleActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
    }

    private void init(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar_art);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mListView = (ListView)findViewById(R.id.list_view);

    }
}
