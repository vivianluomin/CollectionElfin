package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.example.asus1.collectionelfin.R;

public class FileActivity extends BaseActivity {

    private NavigationView mMenuNv;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fab;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.fragment_container,new CollectionFragment());
//        mFragmentTransaction.commit();
        //初始化界面
        //initUI();

        //Floating的
        fab = (FloatingActionButton) findViewById(R.id.but_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FileActivity.this, NewfileActivity.class);
                startActivity(intent);
            }

        });


    }


//    private void initUI() {
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerLayout.setFitsSystemWindows(false);
//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.fragment_container,new CollectionFragment());
//        mFragmentTransaction.commit();
//
//    }

}
