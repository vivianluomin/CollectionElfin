package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.asus1.collectionelfin.R;

/**
 * Created by heshu on 2017/10/5.
 */

public class RegisterActivity  extends BaseActivity {
    private EditText registerCellNumber;
    private EditText registerPassword;
    private EditText registerValidate;
    private Button registerValidateButten;
    private Button regiterRegiter;
    private ImageView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化界面nnn
        initUI();
        //初始化监听
        initListener();
    }
    private void initUI() {
        registerCellNumber = (EditText)findViewById(R.id.register_cell_number);
        registerPassword= (EditText)findViewById(R.id.register_password);
        registerValidate= (EditText)findViewById(R.id.register_validate);

        regiterRegiter =(Button)findViewById(R.id.regiter_regiter);
        registerValidateButten=(Button)findViewById(R.id.register_validate_butten);
        back =(ImageView)findViewById(R.id.register_page_back_button);
    }
    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
