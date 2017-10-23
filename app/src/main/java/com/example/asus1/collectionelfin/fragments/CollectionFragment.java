package com.example.asus1.collectionelfin.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.Adapters.CollectionAdapter;
import com.example.asus1.collectionelfin.Adapters.CollectionSortAdapter;
import com.example.asus1.collectionelfin.Event.CollectionSortMessage;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.AllContentHelper;
import com.example.asus1.collectionelfin.Utills.DialogUtill;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.NoteUtil;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.example.asus1.collectionelfin.Views.ErrorView;
import com.example.asus1.collectionelfin.activities.ArticleActivity;
import com.example.asus1.collectionelfin.activities.LoginActivity;
import com.example.asus1.collectionelfin.activities.NotesActivity;
import com.example.asus1.collectionelfin.models.CollectionModel;
import com.example.asus1.collectionelfin.models.CollectionSortModel;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.CollectionSerivce;
import com.example.asus1.collectionelfin.service.NoteSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by asus1 on 2017/10/3.
 */

public class CollectionFragment extends Fragment implements ErrorView.reloadingListener,DialogUtill.DownloadListener  {

    private ListView mListView;
    private List<String> mCollections;
    private CollectionSortAdapter mAdapter;
    private LinearLayout mLoadingLaout;
    private ImageView mLoadingView;
    private ErrorView mErrorView;

    private LoginModle mNowLoginUser;
    public SwipeRefreshLayout mSwipeRefresh;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        setUpView(view);
        return view;
    }


    private void setUpView(View view){
        mListView = (ListView)view.findViewById(R.id.lv_lists);
        mListView.setDivider(null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),ArticleActivity.class);
                intent.putExtra("sort",(String) mListView.getItemAtPosition(position));
                startActivity(intent);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                DialogUtill.showNomalDialog(getActivity(),true,
                        "是否删除"+(String)mListView.getItemAtPosition(position)
                        ,(String)mListView.getItemAtPosition(position),CollectionFragment.this,position,1);
                return true;
            }

        });


        mSwipeRefresh  = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.color_pink);
        mSwipeRefresh.setColorSchemeResources(R.color.white);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startSwipeRefresh();
            }
        });
        mLoadingLaout = (LinearLayout)view.findViewById(R.id.ll_loading_view);
        mLoadingView = (ImageView)view.findViewById(R.id.iv_loading_view);
        mErrorView = (ErrorView)view.findViewById(R.id.error_view);
        mErrorView.setReloadingListener(this);
        AnimationDrawable animationDrawable = (AnimationDrawable)mLoadingView.getDrawable();
        animationDrawable.start();

        requestData();

    }

    @Override
    public void download(final int position,int flag) {
        switch (flag) {
            case  1:
                mCollections.remove(position);
                mAdapter.notifyDataSetChanged();
                break;
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemManager.initContext(getContext());
        mCollections = new ArrayList<>();

        mAdapter = new CollectionSortAdapter(getContext(),
                R.layout.view_collection_sort_item,mCollections);
        mNowLoginUser = LoginHelper.getInstance().getNowLoginUser();

        EventBus.getDefault().register(this);

    }

    private void requestData(){


        if(mNowLoginUser != null){
            CollectionSerivce serivce = RequestFactory.
                    getRetrofit().create(CollectionSerivce.class);
            Call<UniApiReuslt<List<String>>> call
                    = serivce.getCollectionSort(mNowLoginUser.getAccount());
            HttpUtils.doRuqest(call,callBack);
        }else{
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }
        Log.d("requestData","11111");
    }


    @Override
    public void reload() {
        mLoadingLaout.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        requestData();
    }

    HttpUtils.RequestFinishCallBack<List<String>> callBack  = new HttpUtils.RequestFinishCallBack<List<String>>() {
        @Override
        public void getResult(UniApiReuslt<List<String>> apiReuslt) {

            if(apiReuslt!=null){
                List<String> models = apiReuslt.getmData();
                mCollections.clear();
                mSwipeRefresh.setRefreshing(false);
                if(models!=null){
                    mCollections.addAll(models);
                    AllContentHelper.setCollecton_Sort(mCollections);
                    mAdapter.notifyDataSetChanged();

                    mLoadingLaout.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                }else {
                    mLoadingLaout.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);

                }

            }else {
                mErrorView.setVisibility(View.VISIBLE);
                mLoadingLaout.setVisibility(View.GONE);
                mListView.setVisibility(View.GONE);
            }

        }
    };



    private void startSwipeRefresh() {

            mSwipeRefresh.setRefreshing(true);
            requestData();

    }

    private void stopSwipeRefresh() {
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }


    @Subscribe
    public  void  onEvent(CollectionSortMessage message){

        if(message!=null){
            String sortModel = message.getModel();
            mCollections.add(sortModel);
            mAdapter.notifyDataSetChanged();
           AllContentHelper.setCollecton_Sort(mCollections);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
