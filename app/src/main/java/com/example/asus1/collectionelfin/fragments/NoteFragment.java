package com.example.asus1.collectionelfin.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
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
import com.example.asus1.collectionelfin.service.CollectionSerivce;
import com.example.asus1.collectionelfin.service.NoteSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by asus1 on 2017/10/3.
 */

public class NoteFragment extends Fragment implements ErrorView.reloadingListener,DialogUtill.DownloadListener{

    private ListView mListView;
    private LinearLayout mLoadingLayout;
    private ImageView mLoadingView;
    private ErrorView mErrorView;
    private NoteSortAdapter mNoteAdapter;
    private List<String> mNotes;
    private LoginModle mNowUser;


    private SwipeRefreshLayout mSwipeView;
    private AbsListView.OnScrollListener mOnScrollListener;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mListView = (ListView)view.findViewById(R.id.lv_lists);
        mSwipeView=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mListView.setDivider(null);
        mListView.setAdapter(mNoteAdapter);
        mSwipeView.setEnabled(true);
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeView.setRefreshing(true);
                requestData();
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(mListView!=null&&mListView.getChildCount()>0){
                    boolean firstItemVisible = mListView.getFirstVisiblePosition()==0;
                    boolean topOfFirstItemVisible =mListView.getChildAt(0).getTop() ==0;
                    enable = firstItemVisible&&topOfFirstItemVisible;
                }
                mSwipeView.setEnabled(enable);
            }
        });
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
                String account = LoginHelper.getInstance().getNowLoginUser().getAccount();
                String type = (String)mListView.getItemAtPosition(position);

                NoteSerivce noteSerivce = RequestFactory.getRetrofit().create(NoteSerivce.class);
                retrofit2.Call<UniApiReuslt<String>> call = noteSerivce.deleNotesType(account,type);
                HttpUtils.doRuqest(call, callBack2);


                mNotes.remove(position);
                mNoteAdapter.notifyDataSetChanged();
                break;
        }

    }
    private HttpUtils.RequestFinishCallBack<String> callBack2 = new HttpUtils.RequestFinishCallBack<String>(){
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {
            int statu = apiReuslt.getmStatus();
            if (apiReuslt != null) {
                if(statu==0){
                    Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public boolean isListViewReachTopEdge(final ListView listView) {
        boolean result=false;
        if(listView.getFirstVisiblePosition()==0){
            final View topChildView = listView.getChildAt(0);
            result=topChildView.getTop()==0;
            Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
        }
        return result ;
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
        retrofit2.Call<UniApiReuslt<List<String>>> call = serivce.getNoteSorts(mNowUser.getAccount());
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
                    mSwipeView.setRefreshing(false);
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
