package com.joravar.prorevise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class revisionType extends AppCompatActivity {

    Button regular, hintCount, timer, alphabetically, hcGo, abGo, tmGo;
    LinearLayout hcll,abll,tmll;
    EditText hintNum,abStr,tmStr;
    int hints,timeNum;
    String alphaStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_type);

        regular = (Button) findViewById(R.id.regular);
        hintCount = (Button) findViewById(R.id.hintCount);
        timer = (Button) findViewById(R.id.timer);
        alphabetically = (Button) findViewById(R.id.alphabetically);
        hcll = (LinearLayout) findViewById(R.id.hcll);
        abll = (LinearLayout) findViewById(R.id.abll);
        tmll = (LinearLayout) findViewById(R.id.tmll);
        hcGo = (Button) findViewById(R.id.hcGo);
        abGo = (Button) findViewById(R.id.abGo);
        tmGo = (Button) findViewById(R.id.tmGo);
        hintNum = (EditText) findViewById(R.id.hintNum);
        abStr = (EditText) findViewById(R.id.alphaStr);
        tmStr = (EditText) findViewById(R.id.tmstr);

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(revisionType.this, revision.class);
                intent.putExtra("testtype",1);
                revisionType.this.startActivity(intent);
                revisionType.this.finish();
            }
        });
        hintCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hcll.setVisibility(View.VISIBLE);
                abll.setVisibility(View.GONE);
                tmll.setVisibility(View.GONE);
                hcGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hintNum.getText().toString().isEmpty()) {
                            YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.hintNum));
                            hintNum.requestFocus();
                            hintNum.setError("Enter something");
                        } else {
                            hints = Integer.parseInt(hintNum.getText().toString());
                            Intent intent = new Intent(revisionType.this, revision.class);
                            intent.putExtra("testtype",2);
                            intent.putExtra("counter",hints);
                            revisionType.this.startActivity(intent);
                            revisionType.this.finish();
                        }
                    }
                });

            }
        });

        alphabetically.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abll.setVisibility(View.VISIBLE);
                tmll.setVisibility(View.GONE);
                hcll.setVisibility(View.GONE);
                abGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (abStr.getText().toString().isEmpty()) {
                            YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.alphaStr));
                            abStr.requestFocus();
                            abStr.setError("Enter something");
                        } else {
                            if (abStr.getText().toString().contains("1") || abStr.getText().toString().contains("2") || abStr.getText().toString().contains("3") || abStr.getText().toString().contains("4") || abStr.getText().toString().contains("5") || abStr.getText().toString().contains("6") || abStr.getText().toString().contains("7") || abStr.getText().toString().contains("8") || abStr.getText().toString().contains("9") || abStr.getText().toString().contains("0") ) {
                                YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.alphaStr));
                                abStr.requestFocus();
                                abStr.setError("Enter no number");
                            } else {
                                alphaStr = abStr.getText().toString();
                                Intent intent = new Intent(revisionType.this, revision.class);
                                intent.putExtra("testtype",3);
                                intent.putExtra("alpha",alphaStr);
                                revisionType.this.startActivity(intent);
                                revisionType.this.finish();
                            }
                        }
                    }
                });
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmll.setVisibility(View.VISIBLE);
                abll.setVisibility(View.GONE);
                hcll.setVisibility(View.GONE);
                tmGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tmStr.getText().toString().isEmpty()) {
                            YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.tmstr));
                            tmStr.requestFocus();
                            tmStr.setError("Enter something");
                        } else {
                            timeNum = Integer.parseInt(tmStr.getText().toString());
                            Intent intent = new Intent(revisionType.this, revision.class);
                            intent.putExtra("testtype",4);
                            intent.putExtra("time",timeNum);
                            revisionType.this.startActivity(intent);
                            revisionType.this.finish();
                        }
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(revisionType.this, MainActivity.class);
        revisionType.this.startActivity(intent);
        revisionType.this.finish();

    }
}
