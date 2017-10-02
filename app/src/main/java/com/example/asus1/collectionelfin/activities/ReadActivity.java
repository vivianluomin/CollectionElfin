package com.example.asus1.collectionelfin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.http.SslError;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.Base.BaseActivity;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Views.ErrorView;

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
        mCollectionUrl = "http://www.baidu.com";

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

        mWebView.loadUrl(mCollectionUrl);
        mWebView.onResume();
        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setLoadsImagesAutomatically(true);
        mWebSetting.setDefaultTextEncodingName("utf-8");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                mLoadingLayout.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mLoadingLayout.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mWebView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);

                super.onReceivedError(view, request, error);
            }


        });

    }

    private void requestPage(){

        mWebView.reload();
    }

    @Override
    public void reload() {
        requestPage();
    }
}
