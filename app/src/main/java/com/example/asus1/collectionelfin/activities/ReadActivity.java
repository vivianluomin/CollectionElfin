package com.example.asus1.collectionelfin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.NetworkUtil;
import com.example.asus1.collectionelfin.Views.ErrorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class ReadActivity extends BaseActivity implements ErrorView.reloadingListener {

    private WebView mWebView;
    private Toolbar mToolbar;
    private String mCollectionUrl;
    private ImageView mLoadingView;
    private LinearLayout mLoadingLayout;
    private WebSettings mWebSetting;
    private ErrorView mErrorView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();
        mCollectionUrl = intent.getStringExtra("url");

        init();
        setUpViews();
    }

    private void init(){

        mWebView = (WebView)findViewById(R.id.wv_showArtical);
        mWebSetting = mWebView.getSettings();
        mLoadingView = (ImageView) findViewById(R.id.iv_loading_view);
        mLoadingLayout = (LinearLayout) findViewById(R.id.ll_loading_view);
        mErrorView = (ErrorView)findViewById(R.id.error_view);
        mErrorView.setReloadingListener(this);
        mToolbar = (Toolbar) findViewById(R.id.read_page_tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpViews() {
        AnimationDrawable drawable = (AnimationDrawable)mLoadingView.getDrawable();
        drawable.start();

        mWebSetting.setUseWideViewPort(false);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setLoadsImagesAutomatically(true);
        mWebSetting.setDefaultTextEncodingName("utf-8");
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setDefaultFontSize(18);
        mWebSetting.setDefaultFixedFontSize(18);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setDisplayZoomControls(false);

        mWebView.evaluateJavascript("loadContent()",null);

        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if(url!= null){
                    mLoadingLayout.setVisibility(View.GONE);
                    mErrorView.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }else{
                    mWebView.setVisibility(View.GONE);
                    mLoadingLayout.setVisibility(View.GONE);
                    mErrorView.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mLoadingLayout.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mLoadingLayout.setVisibility(View.GONE);
                mWebView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                mErrorView.bringToFront();


            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                if(error.getPrimaryError() == SslError.SSL_DATE_INVALID
                        || error.getPrimaryError()==SslError.SSL_EXPIRED
                        || error.getPrimaryError() ==SslError.SSL_INVALID
                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED){

                    handler.proceed();

                }else {
                    handler.cancel();
                }


            }
        });

        requestPage();

    }




    private void requestPage(){
        if (NetworkUtil.getNetWorkOk(this)){
            mWebSetting.setCacheMode(WebSettings.LOAD_DEFAULT);

        }else{
           mWebSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mWebView.loadUrl(mCollectionUrl);
        mWebView.onResume();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mWebView.goBack();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void reload() {

        requestPage();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.read_page_menu_note:
                intent = new Intent(ReadActivity.this, NewnoteActivity.class);
                startActivity(intent);
                break;
            case R.id.read_page_menu_remove:
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReadActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("删除将清空对应笔记，确定删除？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除收藏记录。。。
                        finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                break;
            case R.id.read_page_menu_share:
                Toast.makeText(this, "分享框", Toast.LENGTH_SHORT).show();
                break;
            case R.id.read_page_menu_link:
                //返回链接到粘贴板
                Toast.makeText(this, "复制链接成功", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
