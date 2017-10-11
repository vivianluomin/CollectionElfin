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
import android.widget.TextView;

import com.example.asus1.collectionelfin.Adapters.CollectionAdapter;
import com.example.asus1.collectionelfin.Adapters.SpinnerAdapter;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.AllContentHelper;
import com.example.asus1.collectionelfin.models.CollectionModel;
import com.example.asus1.collectionelfin.models.CollectionSortModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okhttp3.internal.Util;

public class ReceiveActivity extends BaseActivity {

    private EditText mAddEditext;
    private Spinner mSpinner;
    private SpinnerAdapter mAdapter;
    private TextView mConfirm;
    private int mPosition = 0;

    private List<CollectionSortModel> mCollection_sort = new ArrayList<>();
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
        mConfirm = (TextView)findViewById(R.id.tv_confirm);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mUrl.equals("")){
                    CollectionSortModel model = (CollectionSortModel)mSpinner.getItemAtPosition(mPosition);
                    CollectionModel collectionModel = new CollectionModel();
                    collectionModel.setContent(mUrl);
                    model.addCollection(collectionModel);
                }
            }
        });
        //mCollection_sort = AllContentHelper.getCollecton_Sort();
        setData();
        mAddEditext.setText(mUrl);
        mAddEditext.setSelection(mUrl.length());

        mAdapter = new SpinnerAdapter(this,R.layout.view_spinner_item,mCollection_sort);



        mSpinner.setAdapter(mAdapter);
        mSpinner.setDropDownHorizontalOffset(-50);
        mSpinner.setDropDownVerticalOffset(150);

      mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              mPosition = position;

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });


    }


    private void setData(){

        for(int i = 0;i<15;i++){
            mCollection_sort.add(new CollectionSortModel());
        }
    }
}
