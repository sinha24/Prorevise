package com.joravar.prorevise;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.joravar.prorevise.database.DataHelper;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;


public class revision extends AppCompatActivity {


    public int meaningCount = 0, meaningPress = 1;
    public int counter = 0;
    public int check = 0;
    public int wordSize;
    TextView word, meaning, progress;
    Button showMeaning, next;
    int testtype = 0;
    String alpha = "";
    MyCount timecounter;
    int time = 0;
    ArrayList<String> words = new ArrayList<>();
    ArrayList<String> meanings = new ArrayList<>();
    ArrayList<Integer> wordNo = new ArrayList<>();
    private long backPressedTime = 0;    // used by onBackPressed()

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);

        showMeaning = (Button) findViewById(R.id.showMeaning);
        next = (Button) findViewById(R.id.nextWord);
        word = (TextView) findViewById(R.id.revWord);
        meaning = (TextView) findViewById(R.id.meaningText);
        progress = (TextView) findViewById(R.id.progress2);
        try {
            progress.setText(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent in = getIntent();
        testtype = in.getIntExtra("testtype", 0);
        if (testtype == 1) {
            words = DataHelper.getInstance(revision.this).getWords();
            meanings = DataHelper.getInstance(revision.this).getMeanings();
        } else if (testtype == 2) {
            counter = in.getIntExtra("counter", 0);
            words = DataHelper.getInstance(revision.this).getWords();
            meanings = DataHelper.getInstance(revision.this).getMeanings();
        } else if (testtype == 3) {
            alpha = in.getStringExtra("alpha");
            words = DataHelper.getInstance(revision.this).getAlphWords(alpha);
            meanings = DataHelper.getInstance(revision.this).getAlphaMean(alpha);
            wordSize = words.size();
            Log.e("Check", " " + wordSize);
        } else if (testtype == 4) {
            time = in.getIntExtra("time", 0);
            words = DataHelper.getInstance(revision.this).getWords();
            meanings = DataHelper.getInstance(revision.this).getMeanings();

            Log.e("Check", " started ");
        }

        try {
            next.setText("START");
        } catch (Exception e) {
            e.printStackTrace();
        }


        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        check++;
                                        if (next.getText().toString().equals("START")) {
                                            if (testtype == 4) {
                                                timecounter = new MyCount((time * 1000), 1000);
                                                timecounter.start();
                                            }
                                            meaningCount = 0;
                                        }



                                        int randomNo = -1, i;

                                        meaningPress = 0;
                                        meaning.setVisibility(View.INVISIBLE);
                                        wordSize = words.size();
                                        if (words.contains("N/A")) {
                                            wordSize = -1;
                                        }

                                        try {
                                            next.setText("NEXT");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        for (i = 0; i <= wordSize; i++) {
                                            Log.e("Check", " " + wordSize + words.size());

                                            Random r = new Random();
                                            randomNo = r.nextInt(wordSize);
                                            if (!wordNo.contains(randomNo)) {
                                                wordNo.add(randomNo);
                                                break;
                                            }
                                        }

                                        if (i > wordSize && i != 0) {
                                            if (testtype != 4) {
                                                Toast.makeText(revision.this, "Revision completed", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(revision.this, scorecard.class);
                                                intent.putExtra("meaningCount", meaningCount);
                                                intent.putExtra("wordSize", wordSize);
                                                intent.putExtra("testtype", testtype);
                                                revision.this.startActivity(intent);
                                                revision.this.finish();
                                            } else {
                                                timecounter.cancel();
                                                Toast.makeText(revision.this, "Revision completed", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(revision.this, scorecard.class);
                                                intent.putExtra("meaningCount", meaningCount);
                                                intent.putExtra("wordSize", wordSize);
                                                intent.putExtra("testtype", testtype);
                                                intent.putExtra("check", check);
                                                revision.this.startActivity(intent);
                                                revision.this.finish();
                                            }

                                        } else {
                                            if (!(randomNo == -1)) {
                                                YoYo.with(Techniques.SlideOutLeft).duration(300).playOn(findViewById(R.id.revWord));
                                                word.setText(words.get(randomNo));
                                                YoYo.with(Techniques.SlideInRight).duration(300).playOn(findViewById(R.id.revWord));
                                                meaning.setText(meanings.get(randomNo));

                                            } else {
                                                meaningPress=1;
                                                if (testtype == 3) {
                                                    Toast.makeText(revision.this, "Add some WORDS starting with the entered string", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (testtype == 4) {
                                                        timecounter.cancel();
                                                        Toast.makeText(revision.this, "Add some WORDS", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(revision.this, "Add some WORDS", Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                                            }
                                        }
                                    }

                                }

        );

        showMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meaningPress == 0) {
                    if ((meaningCount == counter) && (testtype == 2)) {
                        meaningCount++;
                        Log.e("meaningcount",meaningCount+"");
                        word.setVisibility(View.INVISIBLE);
                        Toast.makeText(revision.this, "Revision completed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(revision.this, scorecard.class);
                        intent.putExtra("check", check);
                        intent.putExtra("wordSize", wordSize);
                        intent.putExtra("counter", counter);
                        intent.putExtra("testtype", testtype);
                        intent.putExtra("meaningCount", meaningCount);
                        revision.this.startActivity(intent);
                        revision.this.finish();
                    }
                    meaningCount++;
                    meaningPress++;
                    meaning.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FlipInX).duration(700).playOn(findViewById(R.id.meaningText));

                }
            }
        });


    }

    public void onBackPressed() {
        backPressedTime = (backPressedTime + 1);
        if (backPressedTime > 1) {
            Intent intent = new Intent(revision.this, MainActivity.class);
            revision.this.startActivity(intent);
            revision.this.finish();
        } else
            Toast.makeText(getApplicationContext(), " Press Back again to end revision ", Toast.LENGTH_SHORT).show();

    }

    public class MyCount extends CountDownTimer {


        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            progress.setText("Done");
            Toast.makeText(revision.this, "Revision completed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(revision.this, scorecard.class);
            intent.putExtra("meaningCount", meaningCount);
            intent.putExtra("check", check);
            intent.putExtra("wordSize", wordSize);
            intent.putExtra("testtype", testtype);
            revision.this.startActivity(intent);
            revision.this.finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            progress.setText("Time Left: " + hms);
        }


    }
}

