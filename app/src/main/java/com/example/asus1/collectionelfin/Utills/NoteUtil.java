package com.example.asus1.collectionelfin.Utills;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by asus1 on 2017/10/19.
 */

public class NoteUtil {

    public  static String NoteFileAdreess = "data/CollectionElfin/notes";

    public static  NoteDB dataBase = new NoteDB(SystemManager.getContext());
    private static  String[] Colunms = {
            NoteDB.COLUMN_NAME_NOTE_DATE,
            NoteDB.COLUMN_NAME_NOTE_NAME,
            NoteDB.COLUMN_NAME_NOTE_TYPE,
            NoteDB.COLUNM_NAME_NOTE_CONTENT

    };


    public static  String  getNoteAddress(String type,String name){

       SQLiteDatabase db =  dataBase.getReadableDatabase();
        Cursor cursor = db.query(NoteDB.TABLE_NAME_NOTES,Colunms,"name = ? and type= ?",new String[]{name,type},null,null,null);

        String file = cursor.getString(cursor.getColumnIndex(NoteDB.COLUNM_NAME_NOTE_CONTENT));

        Log.d("AAAAA",file+"   fffffff");
        return file;
    }



}
