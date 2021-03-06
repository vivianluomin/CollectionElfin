package com.example.asus1.collectionelfin.Utills;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.DownloadListener;

import com.example.asus1.collectionelfin.R;

/**
 * Created by asus1 on 2017/10/19.
 */

public class DialogUtill {
    //context当前上下文，cancle标志是否要取消键，content内容，titile标题
    //listener接口  position子项 flag选择功能
    private static AlertDialog.Builder DialogNormal(Context context, boolean cancle,
                                                    String content, String titile,
                                                    final DownloadListener listener,
                                                    final int position, final int flag){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_dialog);
        builder.setTitle(titile);
        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.download(position,flag);
            }
        });
        if(cancle){
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }



        return  builder;
    }

    public static void  showNomalDialog(Context context,boolean cancle,
                                  String content,String titile,DownloadListener listener,int position,int flag){

        AlertDialog.Builder builder = DialogNormal(context,cancle,content,titile,listener,position,flag);
        builder.show();

    }




   public interface DownloadListener{
       void  download(int position,int flag);
   }

}
