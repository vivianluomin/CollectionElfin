package com.example.asus1.collectionelfin.Utills;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by asus1 on 2017/10/25.
 */

public class CompressBitmap {

    public  static Bitmap getSmallBitmap(String path){
        int width;
        int hight;
        int inSampleSize=0;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        width = options.outWidth;
        hight = options.outHeight;

        if (hight > 100 || width > 100) {
            final int heightRatio = Math.round((float) hight/ (float) 100);
            final int widthRatio = Math.round((float) width / (float) 100);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        if(inSampleSize<=0){
            inSampleSize = 1;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

      Bitmap  bitmap = BitmapFactory.decodeFile(path,options);


        return bitmap;
    }
}
