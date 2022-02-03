package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EndActivity extends AppCompatActivity {

    public static String LAUNCH_NEW = "from new Tele";
    public static String FROM_BACK = "from back";

    private boolean fromNew;
    private boolean fromBack = false;

    LinearLayout EndLayout;

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
        setContentView(R.layout.activity_end);

        EndLayout = findViewById(R.id.EndLayout);

        scoreTypeList = new ArrayList<>();

        totalScoreView = new TextView(this);

        sharedPreferences = this.getSharedPreferences(TeleActivity.class.getSimpleName() + SHARED_PREF_TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        fromIntent = getIntent();
        fromNew = fromIntent.getBooleanExtra(TeleActivity.LAUNCH_NEW, false);
        fromBack = fromIntent.getBooleanExtra(FinalActivity.FROM_BACK, false);

        // top
        // top setUp in xml

        // middle
        LinearLayout holdViews = new LinearLayout(this);
        holdViews.setOrientation(LinearLayout.VERTICAL);

        createScoreTypes(holdViews);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(holdViews);

        EndLayout.addView(scrollView);


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
    public void createScoreTypes(LinearLayout EndLayout){
        ScoreType delivered = new ScoreType(this, EndLayout, "Shipping Elem Delivered", false, 6);
        add(delivered);

        ScoreType balanced = new ScoreType(this, EndLayout, "Shipping Hub Balanced", false, 10);
        add(balanced);

        ScoreType unbalanced = new ScoreType(this, EndLayout, "Shared Hub Unbalanced", false, 20);
        add(unbalanced);

        ScoreType onWarehouse = new ScoreType(this, EndLayout, "Park: On Warehouse", false, 3);
        add(onWarehouse);

        ScoreType inWarehouse = new ScoreType(this, EndLayout, "Park: In Warehouse", false, 6);
        add(inWarehouse);

        ScoreType capped = new ScoreType(this, EndLayout, "Capped", false, 15);
        add(capped);


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

    public void launchTeleBack(View view) {
        Intent intent = new Intent(this, TeleActivity.class);

        intent.putExtra(FROM_BACK, true); // need to saved data bc we are going back to activity so we should resest like we coming from "from new"

        startActivity(intent);
    }

    public void launchFinal(View view) {
        Intent intent = new Intent(this, FinalActivity.class);
        intent.putExtra("AutoTotal", fromIntent.getIntExtra("AutoTotal", 0));
        intent.putExtra("TeleTotal", fromIntent.getIntExtra("TeleTotal", 0));
        intent.putExtra("EndTotal", getTotalScore());

        startActivity(intent);
    }
}