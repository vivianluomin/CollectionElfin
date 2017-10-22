package com.example.asus1.collectionelfin.Utills;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hasee on 2017/10/13.
 */

public class NoteDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME_NOTES="note";
    private static final String COLUMN_NAME_ID="_id";
    public static final String COLUMN_NAME_NOTE_CONTENT="content";
    public static final String COLUMN_NAME_NOTE_DATE = "date";
    public static final String COLUMN_NAME_NOTE_NAME = "name";
    public static final String COLUMN_NAME_NOTE_TYPE = "type";


    public NoteDB(Context context){
        super(context,"note",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sq1 = "CREATE TABLE "+TABLE_NAME_NOTES+"("+COLUMN_NAME_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_NAME_NOTE_CONTENT+" TEXT ,"
                +COLUMN_NAME_NOTE_DATE+" TEXT ,"
                +COLUMN_NAME_NOTE_NAME+" TEXT ,"
                +COLUMN_NAME_NOTE_TYPE+" TEXT "
                +")";
        Log.d("SQL",sq1);
        db.execSQL(sq1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

    }
}
