package com.joravar.prorevise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DataHelper2 {
    Context cx;
    private SQLiteDatabase db;
    private DataManager2 dm;

    public DataHelper2(Context cx) {
        this.cx = cx;
        dm = new DataManager2(cx, DataManager2.DATABASE_NAME, null, DataManager2.DATABASE_VERSION);
    }

    public static DataHelper2 getInstance(Context cx) {
        return new DataHelper2(cx);
    }

    public void open() {
        db = dm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void read() {
        db = dm.getReadableDatabase();
    }

    public void delete() {
        open();
        db.delete(DataManager2.TABLE_WORDS, null, null);
        close();
    }

    public void createDB() {
        open();
    }

    public String dbName(){
        return DataManager2.DATABASE_NAME;
    }



    public ArrayList<String> getWords() {
        String query = "select " + DataManager2.KEY_WORD_NAME + " from" + DataManager2.TABLE_WORDS + " order by " + DataManager2.KEY_WORD_ID +  " ASC ";
        Log.e("CRUD", query);
        open();
        String id;
        ArrayList<String> wordId = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                id = c.getString(c.getColumnIndex(DataManager2.KEY_WORD_NAME));
                wordId.add(id);
            }while (c.moveToNext());
        } else {
            wordId.add("N/A");
        }
        return wordId;
    }


    public ArrayList<String> getMeanings() {
        String query = "select " + DataManager2.KEY_MEANING + " from " + DataManager2.TABLE_WORDS + " order by " + DataManager2.KEY_WORD_ID +  " ASC ";
        Log.e("CRUD", query);
        open();
        String id;
        ArrayList<String> wordId = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                id = c.getString(c.getColumnIndex(DataManager2.KEY_MEANING));
                wordId.add(id);
            } while (c.moveToNext());
        } else {
            wordId.add("N/A");
        }
        return wordId;
    }



}
