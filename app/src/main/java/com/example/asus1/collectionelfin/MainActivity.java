package com.example.asus1.collectionelfin;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.asus1.collectionelfin.Utills.Floating;
import com.example.asus1.collectionelfin.activities.ReadActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //借我一个按钮
        ImageView button = (ImageView) findViewById(R.id.iv_openDrawer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                startActivity(intent);
            }
        });
        //悬浮按钮的
        FloatingActionButton fag = (FloatingActionButton) findViewById(R.id.but_fab);
        fag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Floating.class);
                startActivity(intent);
            }
        });

        //上面的menu的
        ImageView meanu = (ImageView) findViewById(R.id.iv_switch);
        meanu.setOnClickListener(new ImageButton.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //onKeyDown(KeyEvent.KEYCODE_MENU, null);
                openOptionsMenu();
            }
        });
    }
}
