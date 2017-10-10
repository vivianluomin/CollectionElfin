package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.asus1.collectionelfin.Adapters.CollectionAdapter;
import com.example.asus1.collectionelfin.Adapters.SpinnerAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.AllContentHelper;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class ReceiveActivity extends BaseActivity {

    private EditText mAddEditext;
    private Spinner mSpinner;
    private SpinnerAdapter mAdapter;

    private List<String> mCollection_sort = new ArrayList<>();
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("URL");
        init();
    }

    private void init(){
        mAddEditext = (EditText)findViewById(R.id.et_add_collection);
        mSpinner = (Spinner)findViewById(R.id.sp_lits);
        //mCollection_sort = AllContentHelper.getCollecton_Sort();
        setData();
        mAddEditext.setText(mUrl);

        mAdapter = new SpinnerAdapter(this,R.layout.view_spinner_item,mCollection_sort);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(mAdapter);
        mSpinner.setDropDownHorizontalOffset(100);
        mSpinner.setDropDownVerticalOffset(200);

//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



    }


    private void setData(){

        for(int i = 0;i<15;i++){
            mCollection_sort.add("my love");
        }
    }
}
