package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.NetworkUtil;
import com.example.asus1.collectionelfin.Views.ErrorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class ReadActivity extends BaseActivity implements ErrorView.reloadingListener {

    private ImageView mBack;
    private WebView mWebView;
    private Toolbar mToolbar;
    private TextView mBarTitle;
    private String mCollectionUrl;
    private String mCollectionTitle;
    private ImageView mLoadingView;
    private LinearLayout mLoadingLayout;
    private WebSettings mWebSetting;
    private ErrorView mErrorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();
//        mCollectionUrl = intent.getStringExtra("url");
//        mCollectionTitle = intent.getStringExtra("title");
        mCollectionUrl = "https://www.zhihu.com/question/65768588/answer/240203625?utm_source=qq&utm_medium=social ";
//
        init();
        setUpViews();
    }

    private void init(){
        mBack = (ImageView)findViewById(R.id.iv_read_page_back_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView = (WebView)findViewById(R.id.wv_showArtical);
        mWebSetting = mWebView.getSettings();
        mBarTitle = (TextView)findViewById(R.id.tv_read_page_title);
        mLoadingView = (ImageView) findViewById(R.id.iv_loading_view);
        mLoadingLayout = (LinearLayout) findViewById(R.id.ll_loading_view);
        mErrorView = (ErrorView)findViewById(R.id.error_view);
        mErrorView.setReloadingListener(this);

    }

    private void setUpViews() {
        AnimationDrawable drawable = (AnimationDrawable)mLoadingView.getDrawable();
        drawable.start();

        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setLoadsImagesAutomatically(true);
        mWebSetting.setDefaultTextEncodingName("utf-8");
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setDefaultFontSize(18);
        mWebSetting.setDefaultFixedFontSize(18);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setDisplayZoomControls(false);

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
}
