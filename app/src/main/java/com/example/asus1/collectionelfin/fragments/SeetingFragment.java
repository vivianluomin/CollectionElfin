package com.example.asus1.collectionelfin.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.Event.MessageEvent;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.activities.LoginActivity;
import com.example.asus1.collectionelfin.activities.MainActivity;
import com.example.asus1.collectionelfin.cut_photo.Cut_photo;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.PersonalService;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;

import retrofit2.Call;

public class SeetingFragment extends Fragment implements View.OnClickListener{



    private EditText mUsername;
    private EditText mAccount;
    private LoginModle mNowUser;
    private ImageView mUsericon;
    private TextView mLogout;
    private TextView mUserTitle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        init(view);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view){

        mNowUser = LoginHelper.getInstance().getNowLoginUser();
        mUsername = (EditText)view.findViewById(R.id.et_setting_username);
        mAccount = (EditText)view.findViewById(R.id.et_setting_account);
        mUserTitle = (TextView)view.findViewById(R.id.tv_user_name);
        mLogout = (TextView)view.findViewById(R.id.tv_logout);
        mUsericon = (ImageView)view.findViewById(R.id.iv_user_icon);
        mUsericon.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mUsername.setText(mNowUser.getUserName());
        mUserTitle.setText(mNowUser.getUserName());
        mAccount.setText(mNowUser.getAccount());



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:
                LoginHelper.getInstance().clearUserInfor();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.iv_user_icon:
                startActivity(new Intent(getActivity(), Cut_photo.class));
                break;

        }

    }


    HttpUtils.RequestFinishCallBack<String>  callBack = new HttpUtils.RequestFinishCallBack<String>() {
         @Override
         public void getResult(UniApiReuslt<String> apiReuslt) {

             if(apiReuslt != null){
                 if(apiReuslt.getmStatus()==0){

                     Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                     mUserTitle.setText(mUsername.getText().toString());
                     mNowUser.setUserName(mUsername.getText().toString());
                     LoginHelper.getInstance().clearUserInfor();
                     LoginHelper.getInstance().setNowLiginUser(mNowUser);
                     EventBus.getDefault().post(new MessageEvent(mUsername.getText().toString()));

                 }else{
                     Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                 }

             }else{
                 Toast.makeText(getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
             }

         }
     };


     public  void  setEdit(boolean edit){
         if(!edit){

             mUsername.setCursorVisible(true);
             mUsername.setFocusable(true);
             mUsername.setFocusableInTouchMode(true);
             mUsername.requestFocus();
             mUsername.setSelection(mUsername.getText().toString().length());
         }else{

             mUsername.setCursorVisible(false);
             mUsername.setFocusable(false);
             mUsername.setFocusableInTouchMode(false);
             PersonalService service = RequestFactory.getRetrofit().create(PersonalService.class);
             Call<UniApiReuslt<String>>  call = service.modifyInfor(mUsername.getText().toString(),
                     mNowUser.getAccount(),
                     mNowUser.getPassword());
             HttpUtils.doRuqest(call,callBack);

         }

     }


}
