package com.example.asus1.collectionelfin.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.asus1.collectionelfin.Event.IconMessage;
import com.example.asus1.collectionelfin.Event.IconUrlMessage;
import com.example.asus1.collectionelfin.Event.MessageEvent;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.CompressBitmap;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.NoteDB;
import com.example.asus1.collectionelfin.Utills.NoteUtil;
import com.example.asus1.collectionelfin.activities.LoginActivity;
import com.example.asus1.collectionelfin.activities.MainActivity;
import com.example.asus1.collectionelfin.cut_photo.Cut_photo;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.PersonalService;
import com.example.asus1.collectionelfin.service.RequestFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Connection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class SeetingFragment extends Fragment implements View.OnClickListener{


    private final int CUT_PHOTO = 1;
    private EditText mUsername;
    private EditText mAccount;
    private LoginModle mNowUser;
    private ImageView mUsericon;
    private TextView mLogout;
    private TextView mUserTitle;
    private Bitmap mIcon;
    private String mIconUrl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        init(view);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void init(View view){

        mNowUser = LoginHelper.getInstance().getNowLoginUser();
        mUsername = (EditText)view.findViewById(R.id.et_setting_username);
        mAccount = (EditText)view.findViewById(R.id.et_setting_account);
        mUserTitle = (TextView)view.findViewById(R.id.tv_user_name);
        mLogout = (TextView)view.findViewById(R.id.tv_logout);
        mUsericon = (ImageView)view.findViewById(R.id.iv_user_icon);

        Glide.with(getActivity())
                .load(mNowUser.getIcon())
                .asBitmap()
                .placeholder(R.mipmap.ic_logo)
                .error(R.mipmap.defualt_image)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new BitmapImageViewTarget(mUsericon){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable =
                        RoundedBitmapDrawableFactory.create(getResources(),resource);
                drawable.setCircular(true);
                mUsericon.setImageDrawable(drawable);
            }
        });

        mLogout.setOnClickListener(this);
        mUsername.setText(mNowUser.getUserName());
        mUserTitle.setText(mNowUser.getUserName());
        mAccount.setText(mNowUser.getAccount());


        mUsericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "点击登录", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                final Intent intent = new Intent(getContext(),Cut_photo.class);
                dialog.setTitle("修改头像");
                dialog.setNeutralButton("照相", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("choose","camera");
                        startActivityForResult(intent,CUT_PHOTO);
                    }
                });
                dialog.setNegativeButton("选择相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("choose","ablum");
                        startActivityForResult(intent,CUT_PHOTO);
                    }
                });
                dialog.show();
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:
                LoginHelper.getInstance().clearUserInfor();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
                break;

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                String s = data.getStringExtra("result");
                if(s != null){
                    Bitmap bitmap = CompressBitmap.getSmallBitmap(s);

                    mIcon = bitmap;
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    //bitmap = Bitmap.createScaledBitmap(bitmap,50,50,true);

                    bitmap.compress(Bitmap.CompressFormat.PNG,90,out);
                    try {
                        File file = new File(NoteUtil.NoteFileAdreess + "//icon.png");
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        byte[] bytes = out.toByteArray();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(bytes,0,bytes.length);
                        fileOutputStream.flush();
                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        RequestBody body = RequestBody.
                                create(MediaType.parse("multipart/form-data"),file);
                        builder.addFormDataPart("icon","icon.png",body);
                        PersonalService service = RequestFactory.getRetrofit().create(PersonalService.class);
                        Call<UniApiReuslt<String>> call = service.postIcon(mNowUser.getAccount(),builder.build());
                        HttpUtils.doRuqest(call,IconCallBack);

                    }catch (IOException e){
                        e.printStackTrace();
                    }


                }
                break;
        }
    }

    HttpUtils.RequestFinishCallBack<String> IconCallBack = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt<String> apiReuslt) {
            if(apiReuslt!=null&&apiReuslt.getmStatus()==0){
                mUsericon.setImageBitmap(mIcon);
                EventBus.getDefault().post(new IconMessage(mIcon));

            }else{
                Toast.makeText(getContext(),"头像上传失败",Toast.LENGTH_SHORT).show();
            }
        }
    };

    HttpUtils.RequestFinishCallBack<String>  callBack = new HttpUtils.RequestFinishCallBack<String>() {
         @Override
         public void getResult(UniApiReuslt<String> apiReuslt) {

             if(apiReuslt != null){
                 if(apiReuslt.getmStatus()==0){

                     Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                     mUserTitle.setText(mUsername.getText().toString());
                     mNowUser.setUserName(mUsername.getText().toString());
                     LoginHelper.getInstance().clearUserInfor();
                     LoginHelper.getInstance().setNowLiginUser(mNowUser);
                     EventBus.getDefault().post(new MessageEvent(mUsername.getText().toString()));

                 }else{
                     Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                 }

             }else{
                 Toast.makeText(getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
             }

         }
     };


     public  void  setEdit(boolean edit){
         if(!edit){

             mUsername.setCursorVisible(true);
             mUsername.setFocusable(true);
             mUsername.setFocusableInTouchMode(true);
             mUsername.requestFocus();
             mUsername.setSelection(mUsername.getText().toString().length());
         }else{

             mUsername.setCursorVisible(false);
             mUsername.setFocusable(false);
             mUsername.setFocusableInTouchMode(false);
             PersonalService service = RequestFactory.getRetrofit().create(PersonalService.class);
             Call<UniApiReuslt<String>>  call = service.modifyInfor(mUsername.getText().toString(),
                     mNowUser.getAccount(),
                     mNowUser.getPassword());
             HttpUtils.doRuqest(call,callBack);

         }

     }


}
