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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AutoActivity extends AppCompatActivity {

    public static String LAUNCH_NEW = "from new Auto";
    public static String FROM_BACK = "from back";

    private boolean fromNew;
    private boolean fromBack = false;

    LinearLayout AutoLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String SHARED_PREF_TAG = "_pref";

    private List<ScoreType> scoreTypeList;

    private TextView totalScoreView;
    private int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        AutoLayout = findViewById(R.id.AutoLayout);

        scoreTypeList = new ArrayList<>();

        totalScoreView = new TextView(this);

        sharedPreferences = this.getSharedPreferences(TeleActivity.class.getSimpleName() + SHARED_PREF_TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Intent intent = getIntent();
        fromNew = intent.getBooleanExtra(MainActivity.LAUNCH_NEW, false);
        fromBack = intent.getBooleanExtra(TeleActivity.FROM_BACK, false);

        // top
        // top setUp in xml


        // middle
        LinearLayout holdViews = new LinearLayout(this);
        holdViews.setOrientation(LinearLayout.VERTICAL);

        createScoreTypes(holdViews);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(holdViews);

        AutoLayout.addView(scrollView);


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

        holdViews.addView(layout);

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

    //TODO: create score types and add them to list
    public void createScoreTypes(LinearLayout AutoLayout){
        ScoreType duckOnLevel = new ScoreType(this, AutoLayout, "Duck On Right Level", false, 10);
        add(duckOnLevel);

        ScoreType shippingElementOnLevel = new ScoreType(this, AutoLayout, "Shipping Elem On Right Level", false, 20);
        add(shippingElementOnLevel);

        ScoreType deliveredDuck = new ScoreType(this, AutoLayout, "Delivered Duck", true, 10);
        add(deliveredDuck);

        ScoreType freightInSU = new ScoreType(this, AutoLayout, "Delivered Freight to Storage Unit", true, 2);
        add(freightInSU);

        ScoreType freightInSH = new ScoreType(this, AutoLayout, "Delivered Freight to Shipping Hub", true, 6);
        add(freightInSH);

        ScoreType parkedOnSU = new ScoreType(this, AutoLayout, "Parked: On Storage Unit", false, 3);
        add(parkedOnSU);

        ScoreType parkedInSU = new ScoreType(this, AutoLayout, "Parked: In Storage Unit", false, 6);
        add(parkedInSU);

        ScoreType parkedOnW = new ScoreType(this, AutoLayout, "Parked: On Warehouse", false, 5);
        add(parkedOnW);

        ScoreType parkedInW = new ScoreType(this, AutoLayout, "Parked: In Warehouse", false, 10);
        add(parkedInW);

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

    public void launchTele(View view) {
        Intent intent = new Intent(this, TeleActivity.class);

        intent.putExtra(LAUNCH_NEW, fromNew);
        intent.putExtra("AutoTotal", getTotalScore());

        startActivity(intent);
    }

    public void launchHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}