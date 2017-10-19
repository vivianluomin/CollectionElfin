package com.example.asus1.collectionelfin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.Adapters.NoteAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.DialogUtill;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.NoteDB;
import com.example.asus1.collectionelfin.Utills.NoteUtil;
import com.example.asus1.collectionelfin.Views.ErrorView;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.NoteModel;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.NoteSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesActivity extends BaseActivity implements ErrorView.reloadingListener,DialogUtill.DownloadListener {


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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String file= NoteUtil.getNoteAddress(mNotes.get(position).getType(),mNotes.get(position).getTitle());
                if(file == null || file.equals("")){
                    DialogUtill.showNomalDialog(NotesActivity.this,
                            true,String.valueOf(R.string.notedialogcontet),
                            mNotes.get(position).getTitle(),NotesActivity.this,position);
                }else{
                    Intent intent = new Intent(NotesActivity.this,NewnoteActivity.class);
                    intent.putExtra("file",file);
                    startActivity(intent);
                }
            }
        });

        startLoading();
    }

    private void  startLoading(){

        AnimationDrawable drawable = (AnimationDrawable)mLoadingView.getDrawable();
        drawable.start();
        requestData();
    }

    private void requestData(){

        NoteSerivce serivce = RequestFactory.getRetrofit().create(NoteSerivce.class);
        Call<UniApiReuslt<List<NoteModel>>> call = serivce.getNotes(mNowUser.getAccount(),mType);
        HttpUtils.doRuqest(call,callBack);

    }

    HttpUtils.RequestFinishCallBack<List<NoteModel>> callBack  = new HttpUtils.RequestFinishCallBack<List<NoteModel>>() {
        @Override
        public void getResult(UniApiReuslt<List<NoteModel>> apiReuslt) {
            if(apiReuslt!=null){
                mNotes.clear();
                mNotes.addAll(apiReuslt.getmData());

                    for(int i =0;i<mNotes.size();i++) {
                       mNotes.get(i).setType(mType);
                    }

                    mAdapter.notifyDataSetChanged();
                    mListView.setVisibility(View.VISIBLE);
                    mLoadingLayout.setVisibility(View.GONE);

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

    @Override
    public void download(final int position) {
        mListView.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        NoteSerivce serivce = RequestFactory.getRetrofit().create(NoteSerivce.class);
        Call<ResponseBody> call = serivce.downloadNotes(mNowUser.getAccount(),mNotes.get(position).getType(),
                                                        mNotes.get(position).getTitle());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                if(body!=null){
                    try {
                        BufferedReader reader = new BufferedReader(body.charStream());
                        File file = new File(NoteUtil.NoteFileAdreess
                                +mNotes.get(position).getTitle());
                       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                        String s= "";
                        while((s = reader.readLine())!=null){
                            writer.write(s,0,s.length());
                            writer.flush();
                        }
                        mListView.setVisibility(View.VISIBLE);
                        mLoadingLayout.setVisibility(View.GONE);
                        Toast.makeText(NotesActivity.this,"下载完成",Toast.LENGTH_SHORT).show();

                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
