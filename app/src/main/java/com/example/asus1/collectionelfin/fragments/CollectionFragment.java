package com.example.asus1.collectionelfin.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus1.collectionelfin.Adapters.CollectionAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.models.CollectionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus1 on 2017/10/3.
 */

public class CollectionFragment extends Fragment {

    private ListView mListView;
    private List<CollectionModel> mCollections;
    private CollectionAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mListView = (ListView)view.findViewById(R.id.lv_lists);
        mListView.setDivider(null);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCollections = new ArrayList<>();
        setData();
        mAdapter = new CollectionAdapter(getContext(),
                R.layout.view_collection_listview_item,mCollections);
    }

    private void setData(){

        for(int i = 0;i<15;i++){
            mCollections.add(new CollectionModel());
        }
    }
}
