package com.example.asus1.collectionelfin.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus1.collectionelfin.Adapters.NoteAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus1 on 2017/10/3.
 */

public class NoteFragment extends Fragment {

    private ListView mListView;
    private NoteAdapter mNoteAdapter;
    private List<NoteModel> mNotes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mListView = (ListView)view.findViewById(R.id.lv_lists);
        mListView.setDivider(null);
        mListView.setAdapter(mNoteAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotes = new ArrayList<>();
        setData();
        mNoteAdapter = new NoteAdapter(getContext(),R.layout.view_note_listview_item,mNotes);

    }

    private void setData(){

        for(int i = 0;i<15;i++){
            mNotes.add(new NoteModel());
        }
    }

}
