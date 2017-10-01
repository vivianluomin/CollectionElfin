package com.example.asus1.collectionelfin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;

public class ReadActivity extends AppCompatActivity {

    private ImageView mBack;
    private WebView mWebView;
    private Toolbar mToolbar;
    private TextView mBarTitle;
    private String mCollectionUrl;
    private String mCollectionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = new Intent();
        mCollectionUrl = intent.getStringExtra("url");
        mCollectionTitle = intent.getStringExtra("title");

        setUpViews();
    }

    private void setUpViews() {
        mToolbar = (Toolbar) findViewById(R.id.read_page_tool_bar);
        setSupportActionBar(mToolbar);

        mBarTitle = (TextView) findViewById(R.id.read_page_title);
//        setTitle(mCollectionTitle);
        mBarTitle.setText("大概的这样吗？");

        mBack = (ImageView) findViewById(R.id.read_page_back_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.wv_showArtical);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.loadUrl(mCollectionUrl);

        mWebView.loadUrl("https://github.com/nyakokishi/WeChatRadioBar");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.read_page_menu_note:
                Toast.makeText(this, "跳转添加笔记页面", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "想弹出分享框", Toast.LENGTH_SHORT).show();
                break;
            case R.id.read_page_menu_link:
                //返回链接到粘贴板
                Toast.makeText(this, "复制链接成功", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
