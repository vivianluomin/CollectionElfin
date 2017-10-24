package com.example.asus1.collectionelfin.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.asus1.collectionelfin.Adapters.NoteAdapter;
import com.example.asus1.collectionelfin.Adapters.NoteSortAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.AllContentHelper;
import com.example.asus1.collectionelfin.Utills.DialogUtill;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.example.asus1.collectionelfin.Views.ErrorView;
import com.example.asus1.collectionelfin.activities.NewnoteActivity;
import com.example.asus1.collectionelfin.activities.NotesActivity;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.NoteModel;
import com.example.asus1.collectionelfin.models.NoteSortModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.NoteSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by asus1 on 2017/10/3.
 */

public class NoteFragment extends Fragment implements ErrorView.reloadingListener,DialogUtill.DownloadListener {

    private ListView mListView;
    private LinearLayout mLoadingLayout;
    private ImageView mLoadingView;
    private ErrorView mErrorView;
    private NoteSortAdapter mNoteAdapter;
    private List<String> mNotes;
    private LoginModle mNowUser;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mListView = (ListView)view.findViewById(R.id.lv_lists);
        mListView.setDivider(null);
        mListView.setAdapter(mNoteAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NotesActivity.class);
                intent.putExtra("type",mNotes.get(position));
                startActivity(intent);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                DialogUtill.showNomalDialog(getActivity(),true,
                        "是否删除"+(String)mListView.getItemAtPosition(position)
                        ,(String)mListView.getItemAtPosition(position),NoteFragment.this,position,1);
                return true;
            }

        });
        mLoadingLayout = (LinearLayout)view.findViewById(R.id.ll_loading_view);
        mLoadingView = (ImageView)view.findViewById(R.id.iv_loading_view);
        mErrorView = (ErrorView)view.findViewById(R.id.error_view);
        mErrorView.setReloadingListener(this);

        startLoading();
        return view;
    }
    @Override
    public void download(final int position,int flag) {
        switch (flag) {
            case  1:
                //mNotes.remove(position);
                //mNoteAdapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemManager.initContext(getContext());
        mNowUser = LoginHelper.getInstance().getNowLoginUser();
        mNotes = new ArrayList<>();
        mNoteAdapter = new NoteSortAdapter(getContext(),R.layout.view_note_sort_litem,mNotes);

    }

    private void  startLoading() {

        AnimationDrawable drawable = (AnimationDrawable) mLoadingView.getDrawable();
        drawable.start();
        requestData();
    }

    private void  requestData(){
        NoteSerivce serivce = RequestFactory.getRetrofit().create(NoteSerivce.class);
        Call<UniApiReuslt<List<String>>> call = serivce.getNoteSorts(mNowUser.getAccount());
        HttpUtils.doRuqest(call,callback);
    }


    HttpUtils.RequestFinishCallBack<List<String>> callback = new HttpUtils.RequestFinishCallBack<List<String>>() {
        @Override
        public void getResult(UniApiReuslt<List<String>> apiReuslt) {
            if(apiReuslt!=null){
                List<String> noteSorts = apiReuslt.getmData();
                if(noteSorts!=null){
                    mNotes.clear();
                    mNotes.addAll(noteSorts);
                    AllContentHelper.setNote_Sort(mNotes);
                    mNoteAdapter.notifyDataSetChanged();
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
        mErrorView.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        requestData();
    }
}
