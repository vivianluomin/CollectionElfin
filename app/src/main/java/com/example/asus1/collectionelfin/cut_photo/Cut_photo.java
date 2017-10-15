package com.example.asus1.collectionelfin.cut_photo;

import android.annotation.TargetApi;
import android.content.ContentUris;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus1.collectionelfin.R;
import com.example.asus1.collectionelfin.activities.BaseActivity;
import com.example.asus1.collectionelfin.activities.MainActivity;

import java.io.File;

public class Cut_photo extends BaseActivity implements View.OnClickListener{
    private static final int Take_photo = 1;
    private static final int Choose_photo = 2;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_photo);

        Button take_photo = (Button)findViewById(R.id.take_photo);
        take_photo.setOnClickListener(this);
        Button choose_photo_fromalbum = (Button)findViewById(R.id.choose_photo_fromalbum);
        choose_photo_fromalbum.setOnClickListener(this);


        File outputImage = new File(getExternalCacheDir(),"op_image.jpg");
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
                    "com.example.cameraalbumtest.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.take_photo:
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                startActivityForResult(intent,Take_photo);
                break;
            case R.id.choose_photo_fromalbum:
                if(ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.
                                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                break;
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
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否裁剪
        intent.putExtra("crop", "true");
        //设置xy的裁剪比例
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("circleCrop","true");
        //&#x8bbe;&#x7f6e;&#x8f93;&#x51fa;&#x7684;&#x5bbd;&#x9ad8;
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //是否缩放
        intent.putExtra("scale", false);
        //输入图片的Uri，指定以后，可以在这个uri获得图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //是否返回图片数据，可以不用，直接用uri就可以了
        intent.putExtra("return-data", false);
        //设置输入图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //是否关闭面部识别
        intent.putExtra("noFaceDetection", true); // no face detection
        //启动

        startActivityForResult(intent, requestCode);
    }
    /*
     返回数据接收
      *
      */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Take_photo :
                if(resultCode == RESULT_OK){
                    try{
                        if(imageUri == null){
                            Log.d("我的信息","数据为空");
                        }
                        Log.d("这是原来的Uri",imageUri.toString());
                        Log.d("这是转换之后的Uri",Uri.parse(
                                new StringBuilder(imageUri.getPath()).insert(0,"file://")
                                        .toString()).toString());
                        cropImageUri(imageUri,800,800,3);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case Choose_photo:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    try{
                        //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        //            .openInputStream(imageUri));
                        //picture.setImageBitmap(BitmapFactory.
                        //        decodeFile(imageUri.getPath()));
                        Intent i = new Intent();
                        if(imageUri == null){
                            Log.d("来来看看你让我返回给人家数据","喂喂喂，出错了，你的数据是空的");
                        }else{
                            i.putExtra("result",imageUri.getPath());
                        }

                        setResult(RESULT_OK,i);
                        finish();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
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
            imagePath = uri.getPath();
        }
        Uri r = Uri.parse(new StringBuilder(imagePath).insert(0,"file://").toString());
        //displayImage(imagePath);
//         Intent i = new Intent();
//         i.putExtra("result",Uri.parse(imagePath).getPath());
//         setResult(RESULT_OK,i);
//         finish();
        cropImageUri(r,800,800,3);
    }

    /*
          针对不同的手机版本处理从相册返回的Uri数据
           */
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        Log.d("这是4.4以下相册的Uri",uri.toString());
        Uri r = Uri.parse(new StringBuilder(imagePath).insert(0,"file://").toString());
        Log.d("这是我修改之后的Uri",r.toString());
        //displayImage(imagePath);
        //         Intent i = new Intent();
        //         i.putExtra("result",Uri.parse(imagePath).getPath());
        //         setResult(RESULT_OK,i);
        //         finish();
        cropImageUri(r,800,800,3);
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

}