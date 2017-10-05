package com.example.asus1.collectionelfin.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.fragments.CollectionFragment;
import com.example.asus1.collectionelfin.fragments.NoteFragment;

import static com.example.asus1.collectionelfin.R.id.toobar;

public class MainActivity extends BaseActivity {

    /**
     * 抽屉视图
     */
    private DrawerLayout mDrawerLayout;
    private ImageButton imageLogin;
    /**
     * 侧滑菜单视图
     */
    private NavigationView mMenuNv;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Toolbar mToolbar;
    private View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(toobar);
        setSupportActionBar(toolbar);
        //初始化界面
        initUI();
        //初始化监听
        initListener();
        //Floaating的
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.but_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
                startActivity(intent);
            }

        });
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setFitsSystemWindows(false);
        mMenuNv = (NavigationView) findViewById(R.id.nv_layout);
        mToolbar = (Toolbar)findViewById(toobar);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container,new CollectionFragment());
        mFragmentTransaction.commit();
        mHeaderView =mMenuNv.getHeaderView(0);;
        imageLogin = (ImageButton) mHeaderView.findViewById(R.id.head_login);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        imageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "点击登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // 设置侧滑菜单点击事件监听
        mMenuNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectItem(item.getItemId());
                // 关闭侧滑菜单
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * 响应item点击事件
     *
     * @param itemid
     */
    private void selectItem(int itemid) {
        switch (itemid) {
            case R.id.menu_mon:
                mToolbar.setTitle(R.string.myCollection);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container,new CollectionFragment());
                mFragmentTransaction.commit();
                break;
            case R.id.menu_tues:
                mToolbar.setTitle(R.string.myBook);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container,new NoteFragment());
                mFragmentTransaction.commit();
                break;
            case R.id.menu_wed:
                Toast.makeText(MainActivity.this, "点击Wed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_thurs:
                Toast.makeText(MainActivity.this, "点击Thurs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_fri:
                Toast.makeText(MainActivity.this, "点击Fri", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    //Toobar的尴尬
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toobar_search:
                break;
            case R.id.toobar_delete:
                break;
            case R.id.toobar_choose:
                break;
            case R.id.toobar_shot:
                break;
            case R.id.toobar_set:
                break;
            default:
        }
        return true;
    }

}

