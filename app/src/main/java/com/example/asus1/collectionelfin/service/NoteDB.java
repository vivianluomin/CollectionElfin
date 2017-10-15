package com.example.asus1.collectionelfin.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hasee on 2017/10/13.
 */

public class NoteDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME_NOTES="note";
    private static final String COLUMN_NAME_ID="_id";
    private static final String COLUNM_NAME_NOTE_CONTENT="content";
    private static final String COLUMN_NAME_NOTE_DATE = "date";

    public NoteDB(Context context){
        super(context,"note",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sq1 = "CREATE TABLE "+TABLE_NAME_NOTES+"("+COLUMN_NAME_ID
                +"INTEGRE PRIMARY KEY AUTOINCREMENT,"
                +COLUNM_NAME_NOTE_CONTENT+"TEXT NOT NULL DEFAULF\"\","
                +COLUMN_NAME_NOTE_DATE+"TEXT NOT NULL DEFAULT\"\""+")";
        Log.d("SQL",sq1);
        db.execSQL(sq1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

    }
}
