package com.example.asus1.collectionelfin.Utills;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

/**
 * Created by asus1 on 2017/10/19.
 */

public class NoteUtil {

    public  static String NoteFileAdreess = SystemManager.getContext().getFilesDir().getAbsolutePath();

    public static  NoteDB dataBase = new NoteDB(SystemManager.getContext());

    private static  String[] Colunms = {
            NoteDB.COLUMN_NAME_NOTE_NAME,
            NoteDB.COLUMN_NAME_NOTE_TYPE,

    };


    public static  String  getNoteAddress(String type,String name){

       SQLiteDatabase db =  dataBase.getWritableDatabase();
        String file = "";
        if(db!=null){
            Cursor cursor = db.rawQuery("select * from "+NoteDB.TABLE_NAME_NOTES+" where type =? and name=?", new String[]{type, name});
            cursor.moveToFirst();
            if(cursor.getCount()>0&&cursor.getColumnIndex(NoteDB.COLUMN_NAME_NOTE_CONTENT)!= -1){
                file = cursor.getString(cursor.getColumnIndex(NoteDB.COLUMN_NAME_NOTE_CONTENT));
            }


            cursor.close();
            Log.d("AAAAA",file+"   fffffff");
        }

        return file;
    }


    public static void  saveFile(String type,String name,String file){
        SQLiteDatabase db =  dataBase.getWritableDatabase();
        db.execSQL("insert into "+NoteDB.TABLE_NAME_NOTES+" ("+NoteDB.COLUMN_NAME_NOTE_TYPE+","
                    +NoteDB.COLUMN_NAME_NOTE_NAME+","+NoteDB.COLUMN_NAME_NOTE_CONTENT
                    +")"+" values(?,?,?)",new String[]{type,name,file});
    }

    public static void  deleteFile(String type,String name){
        SQLiteDatabase db = dataBase.getWritableDatabase();
//        db.execSQL("delete from "+NoteDB.TABLE_NAME_NOTES+" ("+NoteDB.COLUMN_NAME_NOTE_TYPE+","
//                +NoteDB.COLUMN_NAME_NOTE_NAME
//                +")"+" values(?,?)",new String[]{type,name});
        db.delete(NoteDB.TABLE_NAME_NOTES,NoteDB.COLUMN_NAME_NOTE_TYPE+"=? and "
                +NoteDB.COLUMN_NAME_NOTE_NAME+"=?",new String[]{type,name});
    }

}
