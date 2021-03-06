package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.Adapters.CollectionAdapter;
import com.example.asus1.collectionelfin.Event.CollectionsMessage;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.DialogUtill;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.NoteUtil;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.example.asus1.collectionelfin.Views.ErrorView;
import com.example.asus1.collectionelfin.fragments.CollectionFragment;
import com.example.asus1.collectionelfin.models.CollectionModel;
import com.example.asus1.collectionelfin.models.CollectionSortModel;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.CollectionSerivce;
import com.example.asus1.collectionelfin.service.NoteSerivce;
import com.example.asus1.collectionelfin.service.RegisterSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ArticleActivity extends BaseActivity implements  ErrorView.reloadingListener,DialogUtill.DownloadListener{

    private Toolbar mToolbar;
    private ListView mListView;
    private TextView mToolTitle;
    private List<CollectionModel> mCollections = new ArrayList<>();
    private CollectionAdapter mAdapter;
    private LinearLayout mLoadingLayout;
    private ImageView mLoadingView;
    private ErrorView mErrorView;
    private LoginModle mNowLoginUser;
    private String mSelectSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        mNowLoginUser = LoginHelper.getInstance().getNowLoginUser();

        mSelectSort = (String) getIntent().getStringExtra("sort");
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
        mToolTitle = (TextView)findViewById(R.id.tv_toolbar_title);
        mToolTitle.setText(mSelectSort);
        mListView = (ListView)findViewById(R.id.list_view);
        mCollections = new ArrayList<>();
        mLoadingLayout =(LinearLayout)findViewById(R.id.ll_loading_view);
        mLoadingView =  (ImageView)findViewById(R.id.iv_loading_view);
        mErrorView = (ErrorView)findViewById(R.id.view_error);

        mAdapter = new CollectionAdapter(this,R.layout.view_collection_listview_item,
                mCollections);
        mListView.setDivider(null);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ArticleActivity.this,ReadActivity.class);
                intent.putExtra("url",mCollections.get(position).getUrl());
                startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                DialogUtill.showNomalDialog(ArticleActivity.this,true,
                        "是否删除"+mCollections.get(position).getTitle()
                        ,mCollections.get(position).getTitle(),ArticleActivity.this,position,1);
                return true;
            }

        });
        startLoading();

    }
    public void download(final int position,int flag) {
        switch (flag) {
            case 1:
                String account = LoginHelper.getInstance().getNowLoginUser().getAccount();
                String type = mSelectSort;
                String url = mCollections.get(position).getUrl();

                CollectionSerivce collectionSerivce = RequestFactory.getRetrofit().create(CollectionSerivce.class);
                retrofit2.Call<UniApiReuslt<String>> call = collectionSerivce.deletCollections(account, type, url);
                HttpUtils.doRuqest(call,callBack2);

                mCollections.remove(position);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
    private HttpUtils.RequestFinishCallBack<String>  callBack2 = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {
            int statu = apiReuslt.getmStatus();
            if(apiReuslt!= null){
                if(statu == 0){
                    Toast.makeText(ArticleActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }else if(statu == 1){
                    Toast.makeText(ArticleActivity.this,"登录错误",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(ArticleActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void startLoading(){
        AnimationDrawable drawable = (AnimationDrawable)mLoadingView.getDrawable();
        drawable.start();

        RequestData();

    }

    private void RequestData(){

        if(mNowLoginUser!=null){
            CollectionSerivce collectionSerivce = RequestFactory.
                    getRetrofit().create(CollectionSerivce.class);
            Call<UniApiReuslt<List<CollectionModel>>> call =
                    collectionSerivce.getCollections(mNowLoginUser.getAccount(),mSelectSort);
            HttpUtils.doRuqest(call,callBack);

        }
    }


    @Override
    public void reload() {
        RequestData();
        mLoadingLayout.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }




    HttpUtils.RequestFinishCallBack<List<CollectionModel>> callBack = new HttpUtils.RequestFinishCallBack<List<CollectionModel>>() {
        @Override
        public void getResult(UniApiReuslt<List<CollectionModel>> apiReuslt) {

            if(apiReuslt!=null){

                mCollections.clear();
                mCollections.addAll(apiReuslt.getmData());
                getHead(mCollections);

            }else{
                mErrorView.setVisibility(View.VISIBLE);
                mLoadingLayout.setVisibility(View.GONE);
                mListView.setVisibility(View.GONE);
            }
        }
    };


    private void getHead(final List<CollectionModel> models) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < models.size(); i++) {
                        String url = models.get(i).getUrl();
                        Document document = Jsoup.connect(url).get();
                        String title = document.title();
                        String summary = document.head().getElementsByAttributeValue("meta", "description").text();
                        models.get(i).setTitle(title.substring(0, title.length()/3*2));
                        models.get(i).setContent(summary);
                        Log.d("aaaa",title);

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            mLoadingLayout.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                    mLoadingLayout.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                    mErrorView.setVisibility(View.VISIBLE);
                    mErrorView.bringToFront();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
