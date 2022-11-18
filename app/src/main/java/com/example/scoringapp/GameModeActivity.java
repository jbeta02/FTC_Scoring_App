package com.example.scoringapp;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public abstract class GameModeActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public String SHARED_PREF_TAG = "_pref";

    public List<ScoreType> scoreTypeList;
    public TextView totalScoreView;
    public int totalScore = 0;

    // place ways to score in game here
    public abstract void createScoreTypes(LinearLayout linearLayout);


    // to score type to list
    public void add(ScoreType scoreType){
        scoreTypeList.add(scoreType);
    }

    // calc total score
    public int findTotalScore(){
        totalScore = 0;
        for (ScoreType scoreType:scoreTypeList){
            totalScore += scoreType.score;
        }
        saveTotalScore();
        return totalScore;
    }

    // listen to button presses
    public void buttonListener(Button button){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                totalScoreView.setText("Total: " + findTotalScore());
            }
        });
    }

    // display all views
    public void displayViews(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.addScoreTypeView();
        }
    }

    // save data for future use
    public void saveAllData(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.saveData();
        }
    }

    // load all data
    public void loadAllData(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.loadData();
        }
    }

    // clear all data, probably coming from New
    public void clearAllData(){
        for (ScoreType scoreType: scoreTypeList){
            scoreType.clearData();
        }
    }

    // save total score to be carried for final activity
    public void saveTotalScore(){
        editor.putInt("totalScore", totalScore);

        editor.apply();
    }

    // get saved total score
    public int getTotalScore(){
        return sharedPreferences.getInt("totalScore", 0);
    }

}
