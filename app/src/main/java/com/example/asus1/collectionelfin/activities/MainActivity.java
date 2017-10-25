package com.example.asus1.collectionelfin.activities;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.asus1.collectionelfin.Event.IconMessage;
import com.example.asus1.collectionelfin.Event.IconUrlMessage;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Event.MessageEvent;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.example.asus1.collectionelfin.cut_photo.Cut_photo;
import com.example.asus1.collectionelfin.fragments.CollectionFragment;
import com.example.asus1.collectionelfin.fragments.ModifyPasswordFragment;
import com.example.asus1.collectionelfin.fragments.NoteFragment;
import com.example.asus1.collectionelfin.fragments.SeetingFragment;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.PersonalService;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import cn.smssdk.SMSSDK;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

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
    private FloatingActionButton mFab;

    private String mType = "collection";
    private LoginModle mNowLoginUser;
    private  SeetingFragment mSeetingFragment;

    private DrawerLayout mDrawerLayout;
    private boolean mEdit = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemManager.initContext(getApplicationContext());

        setContentView(R.layout.activity_main);

            mToolbar = (Toolbar) findViewById(toobar);
            mToolbar.setNavigationIcon(R.mipmap.ic_opendrawer);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(mMenuNv);
                }
            });
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            mNowLoginUser = LoginHelper.getInstance().getNowLoginUser();


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
            Glide.with(this)
                    .load(mNowLoginUser.getIcon())
                    .asBitmap()
                    .placeholder(R.mipmap.ic_logo)
                    .error(R.mipmap.defualt_image)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new BitmapImageViewTarget(imageLogin){
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable drawable =
                                    RoundedBitmapDrawableFactory.create(getResources(),resource);
                            drawable.setCircular(true);
                            imageLogin.setImageDrawable(drawable);
                        }
                    });
            Log.d("url",mNowLoginUser.getIcon());
        }
    }

    /**
     * 初始化监听
     */
    private void initListener() {

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

        ColorStateList colorStateList = (ColorStateList)getResources().getColorStateList(R.color.menunv_color_list);
        mMenuNv.setItemIconTintList(colorStateList);
        mMenuNv.setItemTextColor(colorStateList);
        mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
                intent.putExtra("type",mType);
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
                mType = "collection";
                break;
            case R.id.menu_tues:
                mToolbar.setTitle(R.string.myBook);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container,new NoteFragment());
                mFragmentTransaction.commit();
                mEditText.setVisibility(View.GONE);
                mFab.setVisibility(View.VISIBLE);
                mType = "note";
                break;
            case R.id.menu_wed:
                mToolbar.setTitle(R.string.setting);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mSeetingFragment = new SeetingFragment();
                mFragmentTransaction.replace(R.id.fragment_container,mSeetingFragment);
                mFragmentTransaction.commit();
                mEditText.setVisibility(View.VISIBLE);
                mFab.setVisibility(View.GONE);
                break;
            case R.id.menu_thurs:
                mToolbar.setTitle("修改密码");
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container,new ModifyPasswordFragment());
                mFragmentTransaction.commit();
                mEditText.setVisibility(View.GONE);
                mFab.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(IconMessage message){

        if(message!=null){
            Bitmap bitmap = message.getBitmap();
            Log.d("aaaa",bitmap.toString());
            if(bitmap!=null){
                imageLogin.setImageBitmap(bitmap);
            }
        }

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

