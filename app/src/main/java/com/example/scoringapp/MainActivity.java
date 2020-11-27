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

    public void launchAutoNew(View view){
        Intent intent = new Intent(this, AutoActivity.class);

        intent.putExtra(LAUNCH_NEW, true);

        startActivity(intent);
    }

    public void launchAutoCont(View view){
        Intent intent = new Intent(this, AutoActivity.class);

        intent.putExtra(LAUNCH_NEW, false);

        startActivity(intent);
    }

}