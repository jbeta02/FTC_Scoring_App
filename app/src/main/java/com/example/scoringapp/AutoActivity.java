package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

    private String SHARED_PREF_TAG = "_pref";

    private List<ScoreType> scoreTypeList;

    private TextView totalScoreView;
    private int totalScore = 0;

    private boolean fromNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        AutoLayout = findViewById(R.id.AutoLayout);

        scoreTypeList = new ArrayList<>();

        totalScoreView = new TextView(this);

        //TODO: make sure that when new is pressed you do an initial save to save default values and so data will actually exist in sharedPref

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Intent intent = getIntent();
        fromNew = intent.getBooleanExtra(MainActivity.LAUNCH_NEW, false);


        // middle
        createScoreTypes();

        if (fromNew){
            clearAllData();
            saveAllData();
        }
        else{
            loadAllData();
            saveAllData();
        }

        displayViews();


        // bottom
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        Button calcTotal = new Button(this);
        calcTotal.setText("Find Total");
        buttonListener(calcTotal);
        layout.addView(calcTotal);

        totalScoreView.setGravity(Gravity.RIGHT);
        totalScoreView.setText("Total: " + Integer.toString(findTotalScore()));
        layout.addView(totalScoreView);

        AutoLayout.addView(layout);

    }

    private void add(ScoreType scoreType){
        scoreTypeList.add(scoreType);
    }

    public int findTotalScore(){
        totalScore = 0;
        for (ScoreType scoreType:scoreTypeList){
            totalScore += scoreType.score;
        }
        return totalScore;
    }

    private void buttonListener(Button button){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                totalScoreView.setText("Total: " + Integer.toString(findTotalScore()));
            }
        });
    }

    public void createScoreTypes(){
        ScoreType ringsScore = new ScoreType(this, AutoLayout, "ringsScore", true, 5);
        add(ringsScore);

        ScoreType wobbleScore = new ScoreType(this, AutoLayout, "WobbleScoe", true, 1);
        add(wobbleScore);

        ScoreType switchTest = new ScoreType(this, AutoLayout, "SwitchTest", false, 1);
        add(switchTest);

        ScoreType switchTest1 = new ScoreType(this, AutoLayout, "SwitchTest1", false, 1);
        add(switchTest1);
    }

    public void displayViews(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.addScoreTypeView();
        }
    }

    public void saveAllData(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.saveData();
        }
    }

    public void loadAllData(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.loadData();
        }
    }

    public void clearAllData(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.clearData();
        }
    }

}