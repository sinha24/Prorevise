package com.joravar.prorevise;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.joravar.prorevise.database.DataHelper;
import com.joravar.prorevise.database.DataHelper2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_FILE = 1;

    Button newWord, revision;
    TextView progress;
    private long backPressedTime = 0;    // used by onBackPressed()
    private File selectedFile;      // for import
    int menuCheck =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//creating a new folder for the database to be backuped to
        File direct = new File(Environment.getExternalStorageDirectory() + "/DbExport");

        if (!direct.exists()) {
            if (direct.mkdir()) {
                //directory is created;
            }

        }

        DataHelper.getInstance(this).createDB();
        DataHelper2.getInstance(this).createDB();
        newWord = (Button) findViewById(R.id.newWord);
        revision = (Button) findViewById(R.id.revision);
        progress = (TextView) findViewById(R.id.progress1);

        assert progress != null;
        progress.setText("");


        newWord.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(MainActivity.this, newWord.class);
                                           MainActivity.this.startActivity(intent);
                                           MainActivity.this.finish();

                                       }
                                   }

        );

        revision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(MainActivity.this, revisionType.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();

                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.wordLst) {
            Intent intent = new Intent(MainActivity.this, wordList.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();

        }
        if (item.getItemId() == R.id.aboutUs) {
            Intent intent = new Intent(MainActivity.this, aboutUs.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();


        }
        if (item.getItemId() == R.id.importDB) {
            menuCheck=0;
            int MyVersion = Build.VERSION.SDK_INT;
            if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (!checkIfAlreadyhavePermission()) {
                    requestForSpecificPermission();
                }else{
                    Intent intent = new Intent(this, FilePicker.class);
                    startActivityForResult(intent, REQUEST_PICK_FILE);
                }
            }else{
            Intent intent = new Intent(this, FilePicker.class);
            startActivityForResult(intent, REQUEST_PICK_FILE);
            }
        }
        if (item.getItemId() == R.id.exportDB) {
            menuCheck=1;
            int MyVersion = Build.VERSION.SDK_INT;
            if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (!checkIfAlreadyhavePermission()) {
                    requestForSpecificPermission();
                }else
                    exportDB();
            }else
            exportDB();
        }


        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_PICK_FILE:

                    if (data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));
                        String backupDBPath = selectedFile.getPath();
                        importDB(backupDBPath);
                    }
                    break;
            }
        }
    }


    //importing database
    private void importDB(String backupDBPath) {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.joravar.prorevise" + "//databases//" + DataHelper2.getInstance(this).dbName();
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {

        }
        ArrayList<String> words;
        ArrayList<String> meanings;
        words = DataHelper2.getInstance(MainActivity.this).getWords();
        meanings = DataHelper2.getInstance(MainActivity.this).getMeanings();
        if (words.contains("N/A") && words.size() == 1) {
            Toast.makeText(getBaseContext(), "Imported Database is Empty", Toast.LENGTH_LONG).show();

        } else {
            int c, f = 0, p = 0;
            long i;
            for (c = 0; c < words.size(); c++) {
                i = 0;
                i = DataHelper.getInstance(MainActivity.this).insertWord(words.get(c), meanings.get(c));
                if (i > 0) {
                    p++;
                } else {
                    f++;
                }
            }
            Toast.makeText(getBaseContext(), " " + p + " words added, " + f + " words failed to add due to redundancy of total " + words.size() + " words", Toast.LENGTH_LONG).show();
        }
    }

    //exporting database
    private void exportDB() {
        // TODO Auto-generated method stub
        Log.e("export", "start");
        try {
            String state = Environment.getExternalStorageState();

            Log.e("export", "try");
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                Log.e("export", "if");
                String currentDBPath = "//data//" + "com.joravar.prorevise" + "//databases//" + DataHelper.getInstance(this).dbName();
                String backupDBPath = "/Database.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                Log.e("export", "ini");
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                Log.e("export", "close");
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), "Database exported to Internal SD storage :\n" + backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                Log.d("Test", "sdcard mounted readonly");
            } else {
                Log.d("Test", "sdcard state: " + state);
            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void requestForSpecificPermission() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    if (menuCheck==0) {
                        Intent intent = new Intent(this, FilePicker.class);
                        startActivityForResult(intent, REQUEST_PICK_FILE);
                    } else {
                        exportDB();
                    }
                } else {
                    //not granted
                    Toast.makeText(getApplicationContext(), "Grant storage permissions to IMPORT/EXPORT", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public void onBackPressed() {
        backPressedTime = (backPressedTime + 1);

        if (backPressedTime > 1) {
            super.onBackPressed(); // this line close the  app on backpress
            MainActivity.this.finish();
        } else
            Toast.makeText(getApplicationContext(), " Press Back again to exit ", Toast.LENGTH_SHORT).show();

    }
}