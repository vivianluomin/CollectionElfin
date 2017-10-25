package com.example.asus1.collectionelfin.Event;

import android.graphics.Bitmap;

/**
 * Created by asus1 on 2017/10/25.
 */

public class IconMessage {

    private Bitmap bitmap;


    public IconMessage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
