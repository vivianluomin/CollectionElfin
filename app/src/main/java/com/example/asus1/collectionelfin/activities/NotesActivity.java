package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus1.collectionelfin.Adapters.NoteAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Views.ErrorView;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.NoteModel;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.NoteSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class NotesActivity extends BaseActivity implements ErrorView.reloadingListener {


    private LinearLayout mLoadingLayout;
    private ImageView mLoadingView;
    private Toolbar mToolbar;
    private ListView mListView;
    private TextView mToolbarTitle;
    private String mType;
    private NoteAdapter mAdapter;
    private LoginModle mNowUser;
    private ErrorView mErrorView;
    private List<NoteModel> mNotes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
        mNowUser = LoginHelper.getInstance().getNowLoginUser();
        init();
    }

    private void init(){
        mErrorView = (ErrorView)findViewById(R.id.error_view);
        mErrorView.setReloadingListener(this);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_note);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbarTitle = (TextView)findViewById(R.id.tv_toolbar_title);
        mToolbarTitle.setText(mType);
        mLoadingLayout = (LinearLayout)findViewById(R.id.ll_loading_view);
        mLoadingView = (ImageView)findViewById(R.id.iv_loading_view);
        mListView = (ListView)findViewById(R.id.list_view);
        mListView.setDivider(null);
        mAdapter = new NoteAdapter(this,R.layout.view_note_listview_item,mNotes);
        mListView.setAdapter(mAdapter);

        startLoading();
    }

    private void  startLoading(){

        AnimationDrawable drawable = (AnimationDrawable)mLoadingView.getDrawable();
        drawable.start();
        requestData();
    }

    private void requestData(){

        NoteSerivce serivce = RequestFactory.getRetrofit().create(NoteSerivce.class);
        Call<UniApiReuslt<List<String>>> call = serivce.getNotes(mNowUser.getAccount(),mType);
        HttpUtils.doRuqest(call,callBack);

    }

    HttpUtils.RequestFinishCallBack<List<String>> callBack  = new HttpUtils.RequestFinishCallBack<List<String>>() {
        @Override
        public void getResult(UniApiReuslt<List<String>> apiReuslt) {
            if(apiReuslt!=null){
                List<String> notes = apiReuslt.getmData();
                mNotes.clear();
                if(notes!=null){
                    for(int i =0;i<notes.size();i++){
                        NoteModel model = new NoteModel();
                        model.setType(mType);
                        model.setTitle(notes.get(i));
                        mNotes.add(model);
                    }

                    mAdapter.notifyDataSetChanged();
                    mListView.setVisibility(View.VISIBLE);
                    mLoadingLayout.setVisibility(View.GONE);
                }
            }else{
                mErrorView.setVisibility(View.VISIBLE);
                mLoadingLayout.setVisibility(View.GONE);
                mListView.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void reload() {
        requestData();
        mLoadingLayout.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }
}
