package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.cut_photo.Cut_photo;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.PersonalService;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.jsoup.Connection;

import retrofit2.Call;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mEditText;
    private EditText mUsername;
    private EditText mAccount;
    private LoginModle mNowUser;
    private ImageView mUsericon;
    private TextView mLogout;
    private TextView mUserTitle;
    private boolean mEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init(){
        mToolbar = (Toolbar)findViewById(R.id.tool_setting);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNowUser = LoginHelper.getInstance().getNowLoginUser();
        mUsername = (EditText)findViewById(R.id.et_setting_username);
        mAccount = (EditText)findViewById(R.id.et_setting_account);
        mUserTitle = (TextView)findViewById(R.id.tv_user_name);
        mEditText = (TextView)findViewById(R.id.tv_edit);
        mLogout = (TextView)findViewById(R.id.tv_logout);

        mUsericon = (ImageView)findViewById(R.id.iv_user_icon);
        mUsericon.setOnClickListener(this);

        mLogout.setOnClickListener(this);

        mUsername.setText(mNowUser.getUserName());
        mAccount.setText(mNowUser.getAccount());
        mEditText.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:
                LoginHelper.getInstance().clearUserInfor();
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.tv_edit:
                if(!mEdit){
                    mEditText.setText(R.string.compulish);
                    mUsername.setCursorVisible(true);
                    mUsername.setFocusable(true);
                    mUsername.setFocusableInTouchMode(true);
                    mUsername.requestFocus();
                    mUsername.setSelection(mUsername.getText().toString().length());
                    mEdit = true;

                }else{
                    mEditText.setText(R.string.edit);
                    mUsername.setCursorVisible(false);
                    mUsername.setFocusable(false);
                    mUsername.setFocusableInTouchMode(false);
                    mEdit = false;
                    PersonalService service = RequestFactory.getRetrofit().create(PersonalService.class);
                    Call<UniApiReuslt<String>>  call = service.modifyInfor(mUsername.getText().toString(),
                            mNowUser.getAccount(),
                            mNowUser.getPassword());
                    HttpUtils.doRuqest(call,callBack);

                }

                break;
            case R.id.iv_user_icon:
                startActivity(new Intent(SettingActivity.this, Cut_photo.class));
                break;

        }

    }

    HttpUtils.RequestFinishCallBack<String>  callBack = new HttpUtils.RequestFinishCallBack<String>() {
         @Override
         public void getResult(UniApiReuslt<String> apiReuslt) {

             if(apiReuslt != null){
                 if(apiReuslt.getmStatus()==0){

                     Toast.makeText(SettingActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                     mUserTitle.setText(mUsername.getText().toString());
                     mNowUser.setUserName(mUsername.getText().toString());
                     LoginHelper.getInstance().clearUserInfor();
                     LoginHelper.getInstance().setNowLiginUser(mNowUser);

                 }else{
                     Toast.makeText(SettingActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                 }

             }else{
                 Toast.makeText(SettingActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
             }

         }
     };


}
