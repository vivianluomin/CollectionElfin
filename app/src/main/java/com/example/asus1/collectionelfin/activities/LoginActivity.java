package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.LoginSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.json.JSONObject;

import retrofit2.Call;


/**
 * Created by heshu on 2017/10/3.
 */

public class LoginActivity extends BaseActivity {
    private EditText loginCellNumber;
    private EditText loginPassword;
    private Button loginRegiter;
    private Button loginLogin;
    private ImageView back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化界面
        initUI();
        //初始化监听
        initListener();
    }
    private void initUI() {
        loginCellNumber = (EditText)findViewById(R.id.login_cell_number);
        loginPassword= (EditText)findViewById(R.id.login_password);
        loginRegiter =(Button)findViewById(R.id.login_regiter);
        loginLogin=(Button)findViewById(R.id.login_login);
        back =(ImageView)findViewById(R.id.login_page_back_button);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = loginCellNumber.getText().toString();
                String password = loginPassword.getText().toString();

                if(!account.equals("") && !password.equals("")){
                   LoginSerivce loginSerivce = RequestFactory.getRetrofit().create(LoginSerivce.class);
                   Call<UniApiReuslt<String>>  call = loginSerivce.Login(account,password);

                    HttpUtils.doRuqest(call,callBack);

                    //JSONObject object = new JSONObject()
                }

            }
        });

}


    private HttpUtils.RequestFinishCallBack<String>  callBack = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {



        }
    };


}
