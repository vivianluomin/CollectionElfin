package com.example.asus1.collectionelfin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.activities.LoginActivity;
import com.example.asus1.collectionelfin.activities.RegisterActivity;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.PersonalService;
import com.example.asus1.collectionelfin.service.RegisterSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.SearchEngine;

/**
 * Created by heshu on 2017/10/17.
 */

public class ModifyPasswordFragment extends Fragment implements View.OnClickListener{
    private EditText mCellNumber;
    private EditText mPassword;
    private EditText mValidate;
    private Button mValidateButten;
    private Button mModifyPassword;

    private HashMap<Character, ArrayList<String[]>> rawData;
    private ArrayList<String> titles;
    private ArrayList<ArrayList<String[]>> countries;
    private SearchEngine sEngine;
    ArrayList<String[]> list;
    private boolean flag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modify_passworld,container,false);
        initUI(view);

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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"验证成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);

                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"验证码已发送",Toast.LENGTH_SHORT).show();
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),des,Toast.LENGTH_SHORT).show();
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
        return view;
    }

    private void initUI(View view) {
        mCellNumber = (EditText)view.findViewById(R.id.register_cell_number);
        mPassword= (EditText)view.findViewById(R.id.register_password);
        mValidate= (EditText)view.findViewById(R.id.register_validate);
        mModifyPassword =(Button)view.findViewById(R.id.modify_password);
        mValidateButten=(Button)view.findViewById(R.id.register_validate_butten);

        mModifyPassword.setOnClickListener(this);
        mValidateButten.setOnClickListener(this);
    }
    public void onClick(View v) {
        String phone = mCellNumber.getText().toString();
        switch (v.getId()){

            case R.id.register_validate_butten:
                //获取验证码
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(getActivity(),"phone can't be null",Toast.LENGTH_SHORT).show();

                SMSSDK.getVerificationCode("86",phone);
                break;

            case R.id.modify_password:
                //提交验证码验证
                flag =false;
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(getActivity(),"phone can't be null",Toast.LENGTH_SHORT).show();

                String number = mValidate.getText().toString();

                if (TextUtils.isEmpty(number))
                    Toast.makeText(getActivity(),"phone can't be null",Toast.LENGTH_SHORT).show();
                Log.i("ssss",phone+","+number);
                SMSSDK.submitVerificationCode("86",phone,number);
                sendData();
                break;

        }
    }
    private void sendData() {     //发送给后台
        String username = LoginHelper.getInstance().getNowLoginUser().getUserName();
        String account =  mCellNumber.getText().toString();
        String password = mPassword.getText().toString();

        Log.d("aaaaaa","456");
        PersonalService personalSerivce = RequestFactory.getRetrofit().create(PersonalService.class);
        retrofit2.Call<UniApiReuslt<String>> call = personalSerivce.modifyInfor(username,account,password);
        HttpUtils.doRuqest(call,callBack);
        Log.d("aaaaaa",callBack.toString());
    }
    private HttpUtils.RequestFinishCallBack<String>  callBack = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {

        }
    };
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
