package com.example.asus1.collectionelfin.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.SearchEngine;

import com.example.asus1.collectionelfin.R;

import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by heshu on 2017/10/5.
 */

public class RegisterActivity  extends BaseActivity implements View.OnClickListener{
    private EditText registerUsername;
    private EditText registerCellNumber;
    private EditText registerPassword;
    private EditText registerValidate;
    private Button registerValidateButten;
    private Button regiterRegiter;
    private ImageView back;
    private boolean flag; //标记是否验证成功

    private HashMap<Character, ArrayList<String[]>> rawData;
    private ArrayList<String> titles;
    private ArrayList<ArrayList<String[]>> countries;
    private SearchEngine sEngine;
    ArrayList<String[]> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化界面nnn
        initUI();
        MobSDK.init(this, "217d149b969aa", "3c51826883bc6ded6492bc985965adb2");
        SMSSDK.setAskPermisionOnReadContact(false);
        rawData = SMSSDK.getGroupedCountryList();

        EventHandler handler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        flag = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){

                        initSearchEngine();
                        search((String)null);

                        for (ArrayList<String[]> test: countries){
                            for (int j=0;j<test.size();j++){
                                Log.i("ssss",test.get(j)[0]);//国名
                                Log.i("ssss",test.get(j)[1]);//区号
                            }
                        }

                        Log.i("test",data.toString());
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,des,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        SMSSDK.registerEventHandler(handler);

    }
    private void initUI() {
        registerCellNumber = (EditText)findViewById(R.id.register_cell_number);
        registerPassword= (EditText)findViewById(R.id.register_password);
        registerValidate= (EditText)findViewById(R.id.register_validate);
        registerUsername =(EditText)findViewById(R.id.register_username);
        regiterRegiter =(Button)findViewById(R.id.regiter_regiter);
        registerValidateButten=(Button)findViewById(R.id.register_validate_butten);
        back =(ImageView)findViewById(R.id.register_page_back_button);

        regiterRegiter.setOnClickListener(this);
        registerValidateButten.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String phone = registerCellNumber.getText().toString();
        switch (v.getId()){

            case R.id.register_validate_butten:
                //获取验证码
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(this,"phone can't be null",Toast.LENGTH_SHORT).show();

                SMSSDK.getVerificationCode("86",phone);
                break;

            case R.id.regiter_regiter:
                //提交验证码验证
                flag =false;
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(this,"phone can't be null",Toast.LENGTH_SHORT).show();

                String number = registerValidate.getText().toString();

                if (TextUtils.isEmpty(number))
                    Toast.makeText(this,"phone can't be null",Toast.LENGTH_SHORT).show();
                Log.i("ssss",phone+","+number);
                SMSSDK.submitVerificationCode("86",phone,number);
                if(flag){

                }sendData();
                break;
            case  R.id.read_page_tool_bar:
                finish();
                break;
        }
    }

    private void sendData() {     //发送给后台
        String username = registerUsername.getText().toString();
        String account =  registerCellNumber.getText().toString();
        String password = registerPassword.getText().toString();

        Log.d("aaaaaa","456");
//       RegisterSerivce registerSerivce = RequestFactory.getRetrofit().create(RegisterSerivce.class);
//       retrofit2.Call<UniApiReuslt<String>> call = registerSerivce.Login(username,account,password);
//       HttpUtils.doRuqest(call,callBack);
        Log.d("aaaaaa",callBack.toString());
    }
    private HttpUtils.RequestFinishCallBack<String>  callBack = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private void initSearchEngine() {
        this.sEngine = new SearchEngine();
        ArrayList countries = new ArrayList();
        Iterator var2 = this.rawData.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry ent = (Map.Entry)var2.next();
            ArrayList cl = (ArrayList)ent.getValue();
            Iterator var5 = cl.iterator();

            while(var5.hasNext()) {
                String[] paire = (String[])var5.next();
                countries.add(paire[0]);
            }
        }

        this.sEngine.setIndex(countries);
    }

    public void search(String token) {
        ArrayList res = this.sEngine.match(token);
        boolean isEmptyToken = false;
        if(res == null || res.size() <= 0) {
            res = new ArrayList();
            isEmptyToken = true;
        }

        HashMap resMap = new HashMap();
        Iterator var5 = res.iterator();

        while(var5.hasNext()) {
            String ent = (String)var5.next();
            resMap.put(ent, ent);
        }

        this.titles = new ArrayList();
        this.countries = new ArrayList();
        var5 = this.rawData.entrySet().iterator();

        label37:
        while(var5.hasNext()) {
            Map.Entry ent1 = (Map.Entry)var5.next();
            ArrayList cl = (ArrayList)ent1.getValue();
            list = new ArrayList();
            Iterator var9 = cl.iterator();

            while(true) {
                String[] paire;
                do {
                    if(!var9.hasNext()) {
                        if(list.size() > 0) {
                            this.titles.add(String.valueOf(ent1.getKey()));
                            this.countries.add(list);
                        }
                        continue label37;
                    }

                    paire = (String[])var9.next();
                } while(!isEmptyToken && !resMap.containsKey(paire[0]));

                list.add(paire);

            }

        }

    }
}