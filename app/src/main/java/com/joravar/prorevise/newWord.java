package com.joravar.prorevise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.joravar.prorevise.database.DataHelper;

public class newWord extends AppCompatActivity {

    EditText wordName,meaning;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        add = (Button) findViewById(R.id.add);
        wordName = (EditText) findViewById(R.id.wordName);
        meaning = (EditText) findViewById(R.id.meaning);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tWord,tMeaning;
                tWord = wordName.getText().toString();
                tMeaning = meaning.getText().toString();
                if (tWord.length() > 0) {
                    if (tMeaning.length() > 0) {

                        long i = DataHelper.getInstance(newWord.this).insertWord(tWord,tMeaning);
                        if (i > 0) {
                            Toast.makeText(newWord.this, "Word Added", Toast.LENGTH_LONG).show();
                            System.out.println("Data Entered");
                        } else {
                            Toast.makeText(newWord.this, "Word already added", Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(newWord.this, MainActivity.class);
                        newWord.this.startActivity(intent);
                        newWord.this.finish();


                    }else {
                        YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.meaning));
                        meaning.requestFocus();
                        meaning.setError("Give word a meaning");
                    }
                }else {
                    YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.wordName));
                    wordName.requestFocus();
                    wordName.setError("Give a word first!");
                }
            }
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent(newWord.this, MainActivity.class);
        newWord.this.startActivity(intent);
        newWord.this.finish();

    }
}
