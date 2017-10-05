package com.example.asus1.collectionelfin.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.models.NoteModel;

import java.util.List;

/**
 * Created by asus1 on 2017/10/4.
 */

public class NoteAdapter extends ArrayAdapter<NoteModel> {

    private Context mContext;
    private int mResource;
    private List<NoteModel> mNotes;

    public NoteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<NoteModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mNotes = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHold viewhold;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(mResource,parent,false);
            viewhold = new ViewHold();
            viewhold.mDate = (TextView) convertView.findViewById(R.id.tv_note_time);
            viewhold.mTitle = (TextView)convertView.findViewById(R.id.tv_note_title);
            convertView.setTag(viewhold);

        }



//        viewhold = (ViewHold)convertView.getTag();
//        viewhold.mTitle.setText("aaaaaaa");
//        viewhold.mDate.setText("2017-9-10");



        return convertView;
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    class ViewHold{

        TextView mTitle;
        TextView mDate;
    }
}
