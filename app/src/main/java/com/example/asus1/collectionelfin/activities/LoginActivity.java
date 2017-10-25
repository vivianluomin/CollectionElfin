package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Event.MessageEvent;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.LoginSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import retrofit2.Call;


/**
 * Created by heshu on 2017/10/3.
 */

public class LoginActivity extends BaseActivity {
    private EditText loginCellNumber;
    private EditText loginPassword;
    private Button loginRegiter;
    private Button loginLogin;
    private  String mUrl = "http://47.95.207.40/Collection_elfin_war_exploded/";
    private Toolbar mToolbar;

    String account;
    String password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SystemManager.initContext(getApplicationContext());

        //初始化界面
        initUI();
        //初始化监听
        initListener();

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onEvent(MessageEvent event){

    }

    private void initUI() {
        loginCellNumber = (EditText)findViewById(R.id.login_cell_number);
        loginPassword= (EditText)findViewById(R.id.login_password);
        loginRegiter =(Button)findViewById(R.id.login_regiter);
        loginLogin=(Button)findViewById(R.id.login_login);
        mToolbar = (Toolbar)findViewById(R.id.login_page_tool_bar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initListener() {

        loginRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "点击登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = loginCellNumber.getText().toString();
                 password = loginPassword.getText().toString();

                if(!account.equals("")&&!account.equals("")){
                    LoginSerivce loginSerivce = RequestFactory.getRetrofit().create(LoginSerivce.class);
                    Call<UniApiReuslt<String>>  call = loginSerivce.Login(account,password);
                    HttpUtils.doRuqest(call,callBack);
                }else {
                    Toast.makeText(LoginActivity.this,"密码或者账户不能为空",Toast.LENGTH_SHORT).show();
                }


            }
        });

}


    private HttpUtils.RequestFinishCallBack<String>  callBack = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {

            if(apiReuslt!= null){


                int statu = apiReuslt.getmStatus();
                if(statu == 0){
                    String username = apiReuslt.getmData();
                    Log.d("username",username);

                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    LoginModle modle = new LoginModle();
                    modle.setAccount(account);
                    modle.setUserName(username);
                    modle.setPassword(password);
                    modle.setIcon(mUrl+account+"/icon.png");

                    LoginHelper loginHelper = LoginHelper.getInstance();
                    loginHelper.setNowLiginUser(modle);

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    EventBus.getDefault().post(new MessageEvent(modle.getUserName()));
                    finish();
                }else if(statu == 1){
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"该用户不存在",Toast.LENGTH_SHORT).show();
                }




            }else {
                Toast.makeText(LoginActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };


}
