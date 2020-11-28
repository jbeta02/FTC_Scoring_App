package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeleActivity extends AppCompatActivity {

    public static String LAUNCH_NEW = "from new Tele";
    public static String FROM_BACK = "from back";

    private boolean fromNew;
    private boolean fromBack = false;

    LinearLayout TeleLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String SHARED_PREF_TAG = "_pref";

    private List<ScoreType> scoreTypeList;

    private TextView totalScoreView;
    private int totalScore = 0;

    private Intent fromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele);

        TeleLayout = findViewById(R.id.TeleLayout);

        scoreTypeList = new ArrayList<>();

        totalScoreView = new TextView(this);

        sharedPreferences = this.getSharedPreferences(TeleActivity.class.getSimpleName() + SHARED_PREF_TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        fromIntent = getIntent();
        fromNew = fromIntent.getBooleanExtra(AutoActivity.LAUNCH_NEW, false);
        fromBack = fromIntent.getBooleanExtra(EndActivity.FROM_BACK, false);

        // top
        // top setUp in xml

        // middle
        createScoreTypes();

        if (fromNew && !fromBack){
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
        totalScoreView.setText("Total: " + findTotalScore());
        layout.addView(totalScoreView);

        TeleLayout.addView(layout);

    }

    private void add(ScoreType scoreType){
        scoreTypeList.add(scoreType);
    }

    public int findTotalScore(){
        totalScore = 0;
        for (ScoreType scoreType:scoreTypeList){
            totalScore += scoreType.score;
        }
        saveTotalScore();
        return totalScore;
    }

    private void buttonListener(Button button){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                totalScoreView.setText("Total: " + findTotalScore());
            }
        });
    }

    public void createScoreTypes(){
        ScoreType ringsScore = new ScoreType(this, TeleLayout, "ringsScore1", true, 5);
        add(ringsScore);

        ScoreType wobbleScore = new ScoreType(this, TeleLayout, "WobbleScoe1", true, 1);
        add(wobbleScore);

        ScoreType switchTest = new ScoreType(this, TeleLayout, "SwitchTest01", false, 1);
        add(switchTest);

        ScoreType switchTest1 = new ScoreType(this, TeleLayout, "SwitchTest2", false, 1);
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

    public void saveTotalScore(){
        editor.putInt("totalScore", totalScore);

        editor.apply();
    }

    public int getTotalScore(){
        return sharedPreferences.getInt("totalScore", 0);
    }

    public void launchAutoBack(View view) {
        Intent intent = new Intent(this, AutoActivity.class);

        intent.putExtra(FROM_BACK, true); // need to saved data bc we are going back to activity so we should resest like we coming from "from new"

        startActivity(intent);
    }

    public void launchEnd(View view) {
        Intent intent = new Intent(this, EndActivity.class);

        intent.putExtra(LAUNCH_NEW, fromNew);
        intent.putExtra("AutoTotal", fromIntent.getIntExtra("AutoTotal", 0));
        intent.putExtra("TeleTotal", getTotalScore());

        startActivity(intent);
    }
}