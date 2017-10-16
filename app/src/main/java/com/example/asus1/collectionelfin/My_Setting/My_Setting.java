package com.example.asus1.collectionelfin.My_Setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;

public class My_Setting extends AppCompatActivity implements View.OnClickListener{

    private static int flag = 1;

    private LinearLayout The_Information_FirstView;
    private LinearLayout OldPassage_Changepassage;
    private LinearLayout phoneNumber_changepassage;
    private EditText oldpassage;
    private EditText newpassage;
    private EditText again_newpassage;
    private EditText view3_newpassage;
    private EditText view3_again_newpassage;
    private EditText phoneNunber;
    private ImageView back_fromMy_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__setting);
        flag = 1;
        The_Information_FirstView.findViewById(R.id.The_Information_FirstView);
        The_Information_FirstView.setVisibility(View.VISIBLE);
        OldPassage_Changepassage.findViewById(R.id.OldPassage_Changepassage);
        OldPassage_Changepassage.setVisibility(View.INVISIBLE);
        phoneNumber_changepassage.findViewById(R.id.phoneNumber_changepassage);
        phoneNumber_changepassage.setVisibility(View.INVISIBLE);
        Button change_passage = (Button)findViewById(R.id.change_passage);
        change_passage.setOnClickListener(this);
        back_fromMy_setting = (ImageView)findViewById(R.id.back_fromMy_setting);
        back_fromMy_setting.setOnClickListener(this);



        Button sure_newpassage = (Button)findViewById(R.id.sure_newpassage);
        sure_newpassage.setOnClickListener(this);
        Button cancel_ThePassage = (Button)findViewById(R.id.cancel_ThePassage);
        cancel_ThePassage.setOnClickListener(this);
        Button forget_OldPassage = (Button)findViewById(R.id.forget_OldPassage);
        forget_OldPassage.setOnClickListener(this);
        oldpassage = (EditText)findViewById(R.id.oldpassage);
        newpassage = (EditText)findViewById(R.id.newpassage);
        again_newpassage = (EditText)findViewById(R.id.again_newpassage);
        phoneNunber = (EditText)findViewById(R.id.phoneNumber);

        Button phone_yanzhengma = (Button)findViewById(R.id.phone_yanzhengma);
        phone_yanzhengma.setOnClickListener(this);
        Button chongzhi_passage = (Button)findViewById(R.id.chongzhi_passage);
        chongzhi_passage.setOnClickListener(this);
        view3_newpassage = (EditText)findViewById(R.id.view3_newpassage);
        view3_again_newpassage = (EditText)findViewById(R.id.view3_again_newpassage);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_fromMy_setting:
                switch (flag){
                    case 1:
                        finish();
                        break;
                    case 2:
                        flag = 1;
                        The_Information_FirstView.setVisibility(View.VISIBLE);
                        OldPassage_Changepassage.setVisibility(View.INVISIBLE);
                        phoneNumber_changepassage.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        flag = 2;
                        The_Information_FirstView.setVisibility(View.INVISIBLE);
                        OldPassage_Changepassage.setVisibility(View.VISIBLE);
                        phoneNumber_changepassage.setVisibility(View.INVISIBLE);
                        break;
                }
                break;
            case R.id.change_passage:
                /*
                 *修改密码按键
                 */
                flag = 2;
                The_Information_FirstView.setVisibility(View.INVISIBLE);
                OldPassage_Changepassage.setVisibility(View.VISIBLE);
                phoneNumber_changepassage.setVisibility(View.INVISIBLE);
                break;
            case R.id.sure_newpassage:
                /*
                确认新密码按键
                 */
                //先比较与原密码相同
                if(oldpassage.getText().toString().equals("")
                        && newpassage.getText().toString().
                        equals(again_newpassage.getText().toString())){
                    /*
                        这里写保存密码的逻辑
                        这里可以写强制重新登录
                    */
                }else{
                    Toast.makeText(this,"你的原密码错误或新密码不统一",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cancel_ThePassage:
                /*
                取消修改的新密码
                 */
                flag = 1;
                The_Information_FirstView.setVisibility(View.VISIBLE);
                OldPassage_Changepassage.setVisibility(View.INVISIBLE);
                phoneNumber_changepassage.setVisibility(View.INVISIBLE);
                break;
            case R.id.forget_OldPassage:
                /*
                忘记密码操作
                 */
                flag = 3;
                The_Information_FirstView.setVisibility(View.INVISIBLE);
                OldPassage_Changepassage.setVisibility(View.INVISIBLE);
                phoneNumber_changepassage.setVisibility(View.VISIBLE);
                break;
            case R.id.phone_yanzhengma:
                /*
                发送验证码
                 */
                String s = phoneNunber.getText().toString();
                break;
            case R.id.chongzhi_passage:
                /*
                重置密码操作
                 */
                if(view3_newpassage.getText().toString().equals(
                        view3_again_newpassage.getText().toString())){
                    /*
                    保存密码操作
                     */
                }
                /*
                这里可以写强制重新登录
                 */
                break;
        }

    }
}
