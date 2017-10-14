package com.example.asus1.collectionelfin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.SystemManager;
import com.example.asus1.collectionelfin.models.LoginModle;

public class LauchActivity extends BaseActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private String mUrl="";
    private  String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lauch);
        Intent intent = getIntent();
       action = intent.getAction();
        if(action!=null && action.equals(Intent.ACTION_SEND)){
           mUrl = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.d("aaaa",mUrl);
        }

        init();


    }

    private void init(){
        mImageView = (ImageView)findViewById(R.id.iv_launcher);
        mTextView = (TextView)findViewById(R.id.tv_launcher);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(4000);
        ScaleAnimation scaleAnimation = new
                ScaleAnimation(0,1f,0,1, Animation.RELATIVE_TO_SELF,0.5f
                ,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(4000);

        AnimationSet set = new AnimationSet(false);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {

                SystemManager.initContext(getApplicationContext());
                LoginModle modle = LoginHelper.getInstance().getNowLoginUser();
                if(modle!=null){

                    if(!mUrl.equals("")){
                        Intent intent1 = new Intent(LauchActivity.this,ReceiveActivity.class);
                        intent1.putExtra("URL",mUrl);
                        Log.d("url",mUrl);
                        startActivity(intent1);

                    }else {
                        Intent intent = new Intent(LauchActivity.this,MainActivity.class);
                        startActivity(intent);
                    }


                }else {
                    startActivity(new Intent(LauchActivity.this,LoginActivity.class));
                }

                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        mImageView.startAnimation(set);
        mTextView.startAnimation(set);

    }



}
