package com.example.asus1.collectionelfin.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Event.MessageEvent;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.example.asus1.collectionelfin.fragments.CollectionFragment;
import com.example.asus1.collectionelfin.fragments.ModifyPasswordFragment;
import com.example.asus1.collectionelfin.fragments.NoteFragment;
import com.example.asus1.collectionelfin.fragments.SeetingFragment;
import com.example.asus1.collectionelfin.models.LoginModle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.smssdk.SMSSDK;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.asus1.collectionelfin.R.id.toobar;

public class MainActivity extends BaseActivity {

    /**
     * 抽屉视图
     */
    private CircleImageView imageLogin;
    /**
     * 侧滑菜单视图
     */
    private NavigationView mMenuNv;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Toolbar mToolbar;
    private View mHeaderView;
    private TextView mUserName;
    private TextView mEditText;
    private Menu mMenu;
    private FloatingActionButton mFab;

    private LoginModle mNowLoginUser;
    private  SeetingFragment mSeetingFragment;

    private DrawerLayout mDrawerLayout;
    private boolean mEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemManager.initContext(getApplicationContext());

            setContentView(R.layout.activity_main);
            mToolbar = (Toolbar) findViewById(toobar);
            setSupportActionBar(mToolbar);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBar actionBar = getSupportActionBar();
            mNowLoginUser = LoginHelper.getInstance().getNowLoginUser();
            if(actionBar!=null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.mipmap.ic_toolbar_left_white);

            }

            //初始化界面
            initUI();
            //初始化监听
            initListener();
            EventBus.getDefault().register(this);
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
        mHeaderView =mMenuNv.getHeaderView(0);
        mEditText = (TextView)findViewById(R.id.tv_edit);
        mFab = (FloatingActionButton) findViewById(R.id.but_fab);
        mUserName = (TextView)mHeaderView.findViewById(R.id.tv_user_name);
        imageLogin = (CircleImageView) mHeaderView.findViewById(R.id.head_login);

        if(mNowLoginUser != null && mUserName!= null){
            mUserName.setText(mNowLoginUser.getUserName());
        }
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        imageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "点击登录", Toast.LENGTH_SHORT).show();
                if(mNowLoginUser == null){
                    Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

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

        mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
                startActivity(intent);
            }

        });

        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSeetingFragment!=null){
                    if(!mEdit){
                        mEditText.setText(R.string.compulish);
                        mSeetingFragment.setEdit(mEdit);
                        mEdit = true;
                    }else {
                        mEditText.setText(R.string.edit);
                        mSeetingFragment.setEdit(mEdit);
                        mEdit = false;
                    }

                }
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
                mEditText.setVisibility(View.GONE);
                mFab.setVisibility(View.VISIBLE);
                mMenu.setGroupVisible(R.id.menu,true);
                break;
            case R.id.menu_tues:
                mToolbar.setTitle(R.string.myBook);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container,new NoteFragment());
                mFragmentTransaction.commit();
                mEditText.setVisibility(View.GONE);
                mFab.setVisibility(View.VISIBLE);
                mMenu.setGroupVisible(R.id.menu,true);
                break;
            case R.id.menu_wed:
                mToolbar.setTitle(R.string.setting);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mSeetingFragment = new SeetingFragment();
                mFragmentTransaction.replace(R.id.fragment_container,mSeetingFragment);
                mFragmentTransaction.commit();
                mEditText.setVisibility(View.VISIBLE);
                mFab.setVisibility(View.GONE);
                mMenu.setGroupVisible(R.id.menu,false);
                break;
            case R.id.menu_thurs:
                mToolbar.setTitle("修改密码");
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container,new ModifyPasswordFragment());
                mFragmentTransaction.commit();
                mEditText.setVisibility(View.GONE);
                mFab.setVisibility(View.GONE);
                mMenu.setGroupVisible(R.id.menu,false);
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
        mMenu =menu;
        return true;
    }


//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //toobar的左侧按钮，打开滑动菜单的
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toobar_search:
                Intent intentd = new Intent(this,SearchActivity.class);
                startActivity(intentd);
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


    @Subscribe
    public void onEvent(MessageEvent messageEvent){

        if(mUserName!=null){
            mUserName.setText(messageEvent.getMessage());
            Log.d("username",messageEvent.getMessage());
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        SMSSDK.unregisterAllEventHandler();
    }



}

