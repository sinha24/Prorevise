package com.joravar.prorevise;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joravar.prorevise.database.DataHelper;

import java.util.ArrayList;

public class wordList extends AppCompatActivity {

    ListView wordsList;
    TextView wlist;
    ArrayAdapter<String> wordsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        wordsList = (ListView) findViewById(R.id.wordsList);
        wlist=(TextView) findViewById(R.id.wlist);


        final ArrayList<String> wordsLst = DataHelper.getInstance(wordList.this).getSortWords();
        int n= wordsLst.size();
        if (wordsLst.contains("N/A")) {
            try {
                wlist.setText("No. of words: 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            wlist.setText("No. of words: " + n + "");

            wordsAdapter = new ArrayAdapter<>(wordList.this, android.R.layout.simple_spinner_dropdown_item, wordsLst);
            wordsList.setAdapter(wordsAdapter);


            wordsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    Log.e("long clicked", "pos: " + position);

                    new AlertDialog.Builder(wordList.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Delete?")
                            .setMessage("Are you sure you want to delete")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String word = wordsLst.get(position);
                                    int t = 0;
                                    t = DataHelper.getInstance(wordList.this).deleteWord(word);
                                    if (t == 0) {
                                        Toast.makeText(wordList.this, "Sorry, Something went wrong", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(wordList.this, "Deletion Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(wordList.this, wordList.class);
                                        wordList.this.startActivity(intent);
                                        wordList.this.finish();

                                    }

                                }

                            })
                            .setNegativeButton("No", null)
                            .show();


                    return true;
                }
            });
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(wordList.this, MainActivity.class);
        wordList.this.startActivity(intent);
        wordList.this.finish();


    }
}
