package com.example.asus1.collectionelfin.activities;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus1.collectionelfin.Adapters.SpinnerAdapter;
import com.example.asus1.collectionelfin.PictureAndTextEditorView.PictureAndTextEditorView;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.AllContentHelper;
import com.example.asus1.collectionelfin.Utills.HttpUtils;
import com.example.asus1.collectionelfin.Utills.LoginHelper;
import com.example.asus1.collectionelfin.Utills.NoteDB;
import com.example.asus1.collectionelfin.Utills.NoteUtil;
import com.example.asus1.collectionelfin.models.LoginModle;
import com.example.asus1.collectionelfin.models.UniApiReuslt;
import com.example.asus1.collectionelfin.service.NoteSerivce;
import com.example.asus1.collectionelfin.service.RequestFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class NewnoteActivity extends BaseActivity {
    private PictureAndTextEditorView mEditText;
    private String mFileAdress;
    private Uri imageUri;
    private Toolbar mToolbar;
    private ImageView mFinishNote;
    private EditText mTitle;
    private EditText mNoteSort;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private LoginModle mNowUser;
    private File mFile;
    private List<String> mNote_sorts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        mNowUser = LoginHelper.getInstance().getNowLoginUser();
        mFileAdress = getIntent().getStringExtra("file");
        init();

    }



    private void  init(){
        mEditText = (PictureAndTextEditorView) findViewById(R.id.edit_PAE);
        List<String> sorts = AllContentHelper.getNote_Sort();
        if(sorts!=null){
            mNote_sorts.clear();
            mNote_sorts.addAll(sorts);
        }

       // Log.d("nnn",mNote_sorts.get(0));
        mNoteSort = (EditText)findViewById(R.id.et_add_collection);
        mSpinner = (Spinner)findViewById(R.id.sp_lits);
        mSpinner.setDropDownHorizontalOffset(-50);
        mSpinner.setDropDownVerticalOffset(150);
        mSpinnerAdapter = new SpinnerAdapter(this,R.layout.view_spinner_item,mNote_sorts);
        mSpinner.setAdapter(mSpinnerAdapter);
       mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mNoteSort.setText(mNote_sorts.get(position));
               mNoteSort.setSelection(mNote_sorts.get(position).length());
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        mToolbar = (Toolbar) findViewById(R.id.tool_newnote);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle = (EditText)findViewById(R.id.et_title);

        mFinishNote = (ImageView)findViewById(R.id.iv_finish_write);
        mFinishNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEditText.getText().toString();
                String title = mTitle.getText().toString();
                String type = mNoteSort.getText().toString();
                if(!title.equals("")&&!type.equals("")){
                     mFile = new File(NoteUtil.NoteFileAdreess+"//"+title+".html");
                    try{
                        mFile.createNewFile();
                        SpannedString spanned = new SpannedString(content);
                        String c = spanned.toString();
                        FileWriter writer = new FileWriter(mFile);
                        writer.write(c,0,c.length());
                        writer.flush();

                        NoteSerivce serivce = RequestFactory.getRetrofit().create(NoteSerivce.class);
                        RequestBody body = RequestBody.
                                create(MediaType.parse("multipart/form-data"),mFile);
                        MultipartBody multipartBody = new MultipartBody.Builder()
                                                        .addFormDataPart("note",title+".text",body)
                                                        .build();


                        Call<UniApiReuslt<String>> call =
                                serivce.uploadNotes(mNowUser.getAccount(),type,multipartBody);

                        HttpUtils.doRuqest(call,callBack);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(NewnoteActivity.this,"文件名或者收藏夹不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });



        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("EditActivity", mEditText.getmContentList().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(mFileAdress!=null){
            showFile();
        }
    }

    private void showFile(){
        File file = new File(mFileAdress);
        if(file.exists()){
            try {
                BufferedReader reader = new
                        BufferedReader(new InputStreamReader(new FileInputStream(file)));
                StringBuilder builder = new StringBuilder();
                String s = "";
                while ((s=reader.readLine())!=null){
                    builder.append(s);

                }

                mEditText.setText(builder.toString());
                mTitle.setVisibility(View.GONE);
                mNoteSort.setVisibility(View.GONE);
                mSpinner.setVisibility(View.GONE);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 888:
                    if (data != null) {

                         handleImageOnKitKat(data);
                    }
                default:
                    break;

            }
        }
    }
    private String getImageAbsolutePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void selectPicFromLocal() {
        Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
        intent1.setType("image/*");
        startActivityForResult(intent1,888);
    }
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImageAbsolutePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.provider.dowmloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImageAbsolutePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImageAbsolutePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if(bitmap.getHeight()>3000||bitmap.getWidth()>3000){
            Matrix matrix = new Matrix();
            matrix.setScale(0.2f,0.2f);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }else if(bitmap.getHeight()>100 || bitmap.getWidth()>100){
            Matrix matrix = new Matrix();
            matrix.setScale(0.5f,0.5f);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }

        ImageSpan span = new ImageSpan(NewnoteActivity.this,bitmap);
        String s = imagePath;
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(span,0,s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mEditText.append(spannableString);

    }


    HttpUtils.RequestFinishCallBack<String> callBack = new HttpUtils.RequestFinishCallBack<String>() {
        @Override
        public void getResult(UniApiReuslt apiReuslt) {
            if(apiReuslt!=null&&apiReuslt.getmStatus()==0){
                String type = mNoteSort.getText().toString();
                String title = mTitle.getText().toString();
                NoteUtil.saveFile(type,title,NoteUtil.NoteFileAdreess+"//"+title+".html");
                Toast.makeText(NewnoteActivity.this,"笔记保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(NewnoteActivity.this,"笔记保存失败",Toast.LENGTH_SHORT).show();
                if(mFile!=null){
                    mFile.delete();
                }
            }
        }
    };



    }



