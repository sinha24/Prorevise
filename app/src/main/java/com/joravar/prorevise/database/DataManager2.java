package com.joravar.prorevise.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataManager2 extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "importDB";
    public static final String TABLE_WORDS = " words";
    public static final String KEY_WORD_ID = "_wordId";
    public static final String KEY_WORD_NAME = "wordName";
    public static final String KEY_MEANING = "meaning";

    public boolean isTableCreated = false ;




    public DataManager2(Context context, String databasename, CursorFactory factory, int version) {
        super(context, databasename, factory, version);
        Log.e("Create table", "Constructor of data manager");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORD_TABLE = "create table " + TABLE_WORDS + " ("
                + KEY_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_WORD_NAME + " text UNIQUE, "
                + KEY_MEANING + " text "
                + ")";
        db.execSQL(CREATE_WORD_TABLE);

        isTableCreated = true;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int paramInt1, int paramInt2) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_WORDS);
        Log.e("Create table", "onupgrade");
        onCreate(db);
    }


}