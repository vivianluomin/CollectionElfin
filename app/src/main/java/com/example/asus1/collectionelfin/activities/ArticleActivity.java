package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus1.collectionelfin.Adapters.BaseAdapter;
import com.example.asus1.collectionelfin.Adapters.CollectionAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.models.CollectionModel;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ListView mListView;
    private List<CollectionModel> mCollections;
    private CollectionAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        init();
    }

    private void init(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar_art);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = (ListView)findViewById(R.id.list_view);
        mCollections = new ArrayList<>();
        setData();
        mAdapter = new CollectionAdapter(this,R.layout.view_collection_listview_item,
                mCollections);
        mListView.setDivider(null);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ArticleActivity.this,ReadActivity.class));
            }
        });

    }

    private void setData(){

        for(int i = 0;i<15;i++){
            mCollections.add(new CollectionModel());
        }
    }
}
