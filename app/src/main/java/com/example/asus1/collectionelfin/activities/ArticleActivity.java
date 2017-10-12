package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.asus1.collectionelfin.Adapters.CollectionAdapter;
import com.example.asus1.collectionelfin.Event.CollectionsMessage;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.NetworkUtil;
import com.example.asus1.collectionelfin.Views.ErrorView;
import com.example.asus1.collectionelfin.models.CollectionModel;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.AddCollectionSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ArticleActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ListView mListView;
    private List<CollectionModel> mCollections;
    private CollectionAdapter mAdapter;
    private LinearLayout mLoadingLayout;
    private ImageView mLoadingView;
    private ErrorView mErrorView;

    private String title,summary;

    private String url = "http://blog.csdn.net/wcl1179851200/article/details/51331572";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        init();
        EventBus.getDefault().register(this);
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
        mLoadingLayout =(LinearLayout)findViewById(R.id.ll_loading_view);
        mLoadingView =  (ImageView)findViewById(R.id.iv_loading_view);
        mErrorView = (ErrorView)findViewById(R.id.view_error);

        startLoading();

    }

    private void startLoading(){
        AnimationDrawable drawable = (AnimationDrawable)mLoadingView.getDrawable();
        drawable.start();

        getHead();

    }

    private void getData(){




    }

    private void setData(){


        CollectionModel model = new CollectionModel();
        model.setTitle(title);
        model.setContent(summary);

        for(int i = 0;i<15;i++){
            mCollections.add(model);
        }


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

        mLoadingLayout.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    private void getHead(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).get();
                    title = document.title();
                    summary = document.head().getElementsByAttributeValue("meta","description").text();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData();
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                    mLoadingLayout.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                    mErrorView.setVisibility(View.VISIBLE);
                }
            }
        }).start();


    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(CollectionsMessage message){

        if(message!=null){
            CollectionModel model = message.getModel();

        }

    }


    HttpUtils.RequestFinishCallBack<List<CollectionModel>> callBack = new HttpUtils.RequestFinishCallBack<List<CollectionModel>>() {
        @Override
        public void getResult(UniApiReuslt<List<CollectionModel>> apiReuslt) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
