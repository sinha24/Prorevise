package com.joravar.prorevise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class DataHelper {
    Context cx;
    private SQLiteDatabase db;
    private DataManager dm;

    public DataHelper(Context cx) {
        this.cx = cx;
        dm = new DataManager(cx, DataManager.DATABASE_NAME, null, DataManager.DATABASE_VERSION);
    }

    public static DataHelper getInstance(Context cx) {
        return new DataHelper(cx);
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
        db.delete(DataManager.TABLE_WORDS, null, null);
        close();
    }

    public void createDB() {
        open();
    }

    public long insertWord(String wordName, String meaning) {
        open();
        //db.execSQL("insert into " + DataManager.TABLE_SUBJECT + "values('" + stuId + subjName + "')");
        ContentValues values = new ContentValues();
        values.put(DataManager.KEY_WORD_NAME, wordName);
        values.put(DataManager.KEY_MEANING, meaning);
        return db.insert(DataManager.TABLE_WORDS, null, values);
    }

  public String dbName(){
      return DataManager.DATABASE_NAME;
  }




    public ArrayList<String> getWords() {
        String query = "select " + DataManager.KEY_WORD_NAME + " from" + DataManager.TABLE_WORDS + " order by " + DataManager.KEY_WORD_ID +  " ASC ";
        Log.e("CRUD", query);
        open();
        String id;
        ArrayList<String> wordId = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
             do {
                id = c.getString(c.getColumnIndex(DataManager.KEY_WORD_NAME));
                wordId.add(id);
            }while (c.moveToNext());
        } else {
            wordId.add("N/A");
        }
        return wordId;
    }


    public ArrayList<String> getMeanings() {
        String query = "select " + DataManager.KEY_MEANING + " from " + DataManager.TABLE_WORDS + " order by " + DataManager.KEY_WORD_ID +  " ASC ";
        Log.e("CRUD", query);
        open();
        String id;
        ArrayList<String> wordId = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                id = c.getString(c.getColumnIndex(DataManager.KEY_MEANING));
                wordId.add(id);
            } while (c.moveToNext());
        } else {
            wordId.add("N/A");
        }
        return wordId;
    }
    public ArrayList<String> getAlphWords(String ra){
        String lc = ra.toLowerCase();
        String uc = ra.toUpperCase();
        String query = "select " + DataManager.KEY_WORD_NAME + " from " + DataManager.TABLE_WORDS + " where "+DataManager.KEY_WORD_NAME+" like '"+lc+"%'"+" or '"+uc+"%'"+" order by " + DataManager.KEY_WORD_ID +  " ASC ";
        Log.e("CRUD", query);
        open();
        String id;
        ArrayList<String> wordId = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                id = c.getString(c.getColumnIndex(DataManager.KEY_WORD_NAME));
                wordId.add(id);
            }while (c.moveToNext());
        } else {
            wordId.add("N/A");
        }
        return wordId;

    }
    public ArrayList<String> getAlphaMean(String ra){
        String lc = ra.toLowerCase();
        String uc = ra.toUpperCase();
        String query = "select " + DataManager.KEY_MEANING + " from " + DataManager.TABLE_WORDS + " where "+DataManager.KEY_WORD_NAME+" like '"+lc+"%'"+" or '"+uc+"%'"+" order by " + DataManager.KEY_WORD_ID +  " ASC ";
        Log.e("CRUD", query);
        open();
        String id;
        ArrayList<String> wordId = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                id = c.getString(c.getColumnIndex(DataManager.KEY_MEANING));
                wordId.add(id);
            } while (c.moveToNext());
        } else {
            wordId.add("N/A");
        }
        return wordId;

    }
    public ArrayList<String> getSortWords(){
        String query = "select " + DataManager.KEY_WORD_NAME + " from" + DataManager.TABLE_WORDS + " order by " + DataManager.KEY_WORD_NAME+  " COLLATE NOCASE ";
        Log.e("CRUD", query);
        open();
        String id;
        ArrayList<String> wordId = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                id = c.getString(c.getColumnIndex(DataManager.KEY_WORD_NAME));
                wordId.add(id);
            }while (c.moveToNext());
        } else {
            wordId.add("N/A");
        }
        return wordId;
    }

    public int deleteWord(String word){

        open();
        String query = "DELETE FROM " + DataManager.TABLE_WORDS + " WHERE " + DataManager.KEY_WORD_NAME +  " = '" + word + "'";
        Log.e("Check1",query);
            Log.e("Check2",query);
            db.execSQL("DELETE FROM " + DataManager.TABLE_WORDS + " WHERE " + DataManager.KEY_WORD_NAME +  " = '" + word + "'");
            Log.e("Check3", query);
            return 1;

    }


}
