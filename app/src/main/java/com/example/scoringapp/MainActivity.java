package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static String LAUNCH_NEW = "from new button";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // start next activity and specific that it was launched from new button so clear old values
    public void launchAutoNew(View view){
        Intent intent = new Intent(this, AutoActivity.class);

        intent.putExtra(LAUNCH_NEW, true);

        startActivity(intent);
    }

    // start next activity and specific that it was not launched from new button so keep old values
    public void launchAutoCont(View view){
        Intent intent = new Intent(this, AutoActivity.class);

        intent.putExtra(LAUNCH_NEW, false);

        startActivity(intent);
    }

}