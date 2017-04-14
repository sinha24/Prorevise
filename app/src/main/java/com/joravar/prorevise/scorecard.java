package com.joravar.prorevise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class scorecard extends AppCompatActivity {
    TextView progressCount, totalCount, percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);
        Intent in = getIntent();
        final int meaningCount = in.getIntExtra("meaningCount", 0);
        final int wordSize = in.getIntExtra("wordSize", 0);
        final  int testype=  in.getIntExtra("testtype", 0);
        progressCount = (TextView) findViewById(R.id.progressCount);
        totalCount = (TextView) findViewById(R.id.totalCount);
        percentage = (TextView) findViewById(R.id.percentage);
        if(testype==1)
        {
            int q = wordSize - meaningCount;
            progressCount.setText("" + q);
            totalCount.setText("" + wordSize);
            int p = (wordSize - meaningCount) * 100 / wordSize;

            percentage.setText(p + "%");
        }
        else
        if(testype==2)
        {
            final int check=in.getIntExtra("check", 0);
            final int counter=in.getIntExtra("counter", 0);
            int q = check-meaningCount;
            progressCount.setText("" + q);
            totalCount.setText("" + check);
            Log.e("check",check+"");
            int p = (q) * 100 / check;

            percentage.setText(p + "%");
        }
        else if(testype==3)
        {
            int q = wordSize - meaningCount;
            progressCount.setText("" + q);
            totalCount.setText("" + wordSize);
            int p = (wordSize - meaningCount) * 100 / wordSize;

            percentage.setText(p + "%");
        }
        else
        if(testype==4){
            final int check = in.getIntExtra("check", 0);
            int q = check - meaningCount;
            progressCount.setText("" + q);
            totalCount.setText("" + check);
            int p = (q) * 100 / check;

            percentage.setText(p + "%");
        }

    }
    public void onBackPressed() {
        Intent intent = new Intent(scorecard.this, MainActivity.class);
        scorecard.this.startActivity(intent);
        scorecard.this.finish();

    }
}
