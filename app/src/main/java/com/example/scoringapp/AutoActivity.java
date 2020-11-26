package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AutoActivity extends AppCompatActivity {

    LinearLayout AutoLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String name = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        AutoLayout = findViewById(R.id.AutoLayout);

        //TODO: add auto title formatting here

        sharedPreferences = this.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ScoreType ringsScore = new ScoreType(this, AutoLayout, "ringsScore");
        ringsScore.loadData();
        ringsScore.addTapScore("rings", 5);

        ScoreType wobbleScore = new ScoreType(this, AutoLayout, "WobbleScoe");
        wobbleScore.loadData();
        wobbleScore.addTapScore("wobble", 1);

    }
}