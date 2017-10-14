package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.Adapters.SpinnerAdapter;
import com.example.asus1.collectionelfin.Event.CollectionsMessage;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.AllContentHelper;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.VariousUtills;
import com.example.asus1.collectionelfin.Views.SpinnerCollection;
import com.example.asus1.collectionelfin.models.CollectionModel;
import com.example.asus1.collectionelfin.models.CollectionSortModel;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.CollectionSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ReceiveActivity extends BaseActivity {

    private EditText mAddEditext;
    private Spinner mSpinner;
    private SpinnerAdapter mAdapter;
    private TextView mConfirm;
    private int mPosition = 0;
    private LoginModle mNowLoginUser;

    private List<String> mCollection_sort = new ArrayList<>();
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        Log.d("uuu",url);
        if(url!=null){
            mUrl = VariousUtills.handleUrl(url);
        }

        init();

    }

    private void init(){
        mAddEditext = (EditText)findViewById(R.id.et_add_collection);
        mSpinner = (Spinner)findViewById(R.id.sp_lits);
        mConfirm = (TextView)findViewById(R.id.tv_confirm);
        mNowLoginUser = LoginHelper.getInstance().getNowLoginUser();
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mUrl.equals("") && mNowLoginUser!=null){
                    String model = (String) mSpinner.getItemAtPosition(mPosition);

                    CollectionSerivce collectionSerivce =
                            RequestFactory.getRetrofit().create(CollectionSerivce.class);
                    Call<UniApiReuslt<String>> call =
                            collectionSerivce.postLink(mNowLoginUser.getAccount(), model,mAddEditext.getText().toString());
                    HttpUtils.doRuqest(call,callBack);

                }
            }
        });
        mCollection_sort = AllContentHelper.getCollecton_Sort();
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





    HttpUtils.RequestFinishCallBack<String> callBack  = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {

            if(apiReuslt!=null){
                Toast.makeText(ReceiveActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();

                CollectionModel model = new CollectionModel();
                model.setUrl(mAddEditext.getText().toString());
                EventBus.getDefault().post(new CollectionsMessage(model));
                finish();
            }else{
                Toast.makeText(ReceiveActivity.this,"添加失败,请重新尝试",Toast.LENGTH_SHORT).show();
            }

        }
    };



}
