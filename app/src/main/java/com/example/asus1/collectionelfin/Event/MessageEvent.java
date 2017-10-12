package com.example.asus1.collectionelfin.Event;

import android.view.MotionEvent;

/**
 * Created by asus1 on 2017/10/12.
 */

public class MessageEvent {

    private String mMessage="";

    public MessageEvent(String message){
        mMessage = message;
    }

    public String getMessage(){
        return mMessage;
    }

    public void setMessage(String message){

        mMessage = message;
    }


}
