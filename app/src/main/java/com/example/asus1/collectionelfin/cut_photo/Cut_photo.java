package com.example.asus1.collectionelfin.cut_photo;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.Utills.NoteDB;
import com.example.asus1.collectionelfin.Utills.NoteUtil;
import com.example.asus1.collectionelfin.activities.BaseActivity;

import java.io.File;
import java.io.IOException;

public class Cut_photo extends BaseActivity {
    private static final int Take_photo = 1;
    private static final int Choose_photo = 2;
    private Uri imageUri;
    private File outputImage;
    private File outputFile = new File(NoteUtil.NoteFileAdreess+"//"+"icon.jpg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_photo);


      outputImage = new File(getExternalCacheDir(),"op_image.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            //调用内容提供器
            imageUri = FileProvider.getUriForFile(this,
                    "com.example.asus1.collectionelfin.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }

        Intent intent = getIntent();
        String s = intent.getStringExtra("choose");
        if(s.equals("camera")){
            Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
            intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

            startActivityForResult(intent1,Take_photo);
        }else if(s.equals("ablum")){
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.
                            WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }else {
                openAlbum();
            }
        }
    }
    private void openAlbum(){
        Intent i = new Intent();
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,Choose_photo);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openAlbum();
            }else {
                Toast.makeText(this,"你没有同意请求",Toast.LENGTH_SHORT).show();
            }
                break;
        }
    }
    /**
     * 调用系统的裁剪器
     * @param uri
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否裁剪
        intent.putExtra("crop", "true");
        //设置xy的裁剪比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("circleCrop","true");
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        //是否缩放
        intent.putExtra("scale", true);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //输入图片的Uri，指定以后，可以在这个uri获得图片


        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
        //是否返回图片数据，可以不用，直接用uri就可以了

            intent.putExtra("return-data", false);


        //设置输入图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //是否关闭面部识别
        intent.putExtra("noFaceDetection", true); // no face detection
        //启动
        Log.d("我的信息","即将进入裁剪");
        startActivityForResult(intent, requestCode);
    }
    /*
     返回数据接收
      */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent i = new Intent();
        switch (requestCode){
            case Take_photo:
                if(resultCode == RESULT_OK){
                    try{
                        cropImageUri(imageUri,3);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    setResult(RESULT_OK,i);
                    finish();
                }
                break;
            case Choose_photo:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }else {
                    setResult(RESULT_OK,i);
                    finish();
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    try{
                        if(imageUri != null){
                            i.putExtra("result",outputImage.getAbsolutePath());

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                setResult(RESULT_OK,i);
                finish();
                break;
        }
    }
    /*
    针对不同的手机版本处理从相册返回的Uri数据
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.provider.dowmloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath =uri.getPath();
        }

       Uri r = getImageContentUri(imagePath);
        cropImageUri(r,3);
    }

    /*
          针对不同的手机版本处理从相册返回的Uri数据
           */
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath =  getImagePath(uri,null);
        Log.d("这是4.4以下相册的Uri",uri.toString());
        Uri r = Uri.parse(new StringBuilder(imagePath).insert(0,"file://").toString());
        Log.d("这是我修改之后的Uri",r.toString());
        cropImageUri(r,3);
    }
    private String getImagePath(Uri uri,String selection){
        String Path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                Path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return Path;
    }


    public Uri getImageContentUri(String imageFile) {
        String filePath = imageFile;
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (new File(imageFile).exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
