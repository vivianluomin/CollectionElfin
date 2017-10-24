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

import com.example.asus1.collectionelfin.Adapters.SpinnerAdapter;
import com.example.asus1.collectionelfin.PictureAndTextEditorView.PictureAndTextEditorView;
import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.AllContentHelper;
import com.example.asus1.collectionelfin.Utills.NoteDB;
import com.example.asus1.collectionelfin.Utills.NoteUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewnoteActivity extends BaseActivity {
    private PictureAndTextEditorView mEditText;
    private FloatingActionButton fab;
    private Uri imageUri;
    private Toolbar mToolbar;
    private ImageView mFinishNote;
    private EditText mTitle;
    private EditText mNoteSort;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private List<String> mNote_sorts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        init();

    }



    private void  init(){
        mEditText = (PictureAndTextEditorView) findViewById(R.id.edit_PAE);
        mNote_sorts = AllContentHelper.getNote_Sort();
        Log.d("nnn",mNote_sorts.get(0));
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
        fab = (FloatingActionButton) findViewById(R.id.but_add_picture);
        mToolbar = (Toolbar) findViewById(R.id.tool_newnote);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle = (EditText)findViewById(R.id.et_title);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(NewnoteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(NewnoteActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    selectPicFromLocal();
                }

            }
        });

        mFinishNote = (ImageView)findViewById(R.id.iv_finish_write);
        mFinishNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEditText.getText().toString();
                String title = mTitle.getText().toString();
                if(!title.equals("")){
//                    File file = new File(NoteUtil.NoteFileAdreess+"//"+title+".html");
//                    try{
//                        file.createNewFile();
//                        SpannedString spanned = new SpannedString(content);
//                        String c =Html.toHtml(spanned,Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE);
//                        FileWriter writer = new FileWriter(file);
//                        writer.write(c,0,c.length());
//                        writer.flush();
//
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }



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
        SpannableString spannableString = new SpannableString(imagePath);
        spannableString.setSpan(span,0,imagePath.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mEditText.append(spannableString);

    }






    }



