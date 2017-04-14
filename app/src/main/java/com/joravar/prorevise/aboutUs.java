package com.joravar.prorevise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class aboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

    }
    public void onBackPressed() {
        Intent intent = new Intent(aboutUs.this, MainActivity.class);
        aboutUs.this.startActivity(intent);
        aboutUs.this.finish();

    }
}
