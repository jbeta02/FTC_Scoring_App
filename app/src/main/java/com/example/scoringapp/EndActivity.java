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

public class EndActivity extends GameModeActivity {

    public static String LAUNCH_NEW = "from new Tele";
    public static String FROM_BACK = "from back";

    private boolean fromNew;
    private boolean fromBack = false;

    LinearLayout endLayout;

    private Intent fromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        endLayout = findViewById(R.id.EndLayout);

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

        endLayout.addView(scrollView);


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


    //TODO: create score types and add them to list
    @Override
    public void createScoreTypes(LinearLayout endLayout){

        add(new ScoreType(this, endLayout, "Junction owned by Cone", true, 2));

        add(new ScoreType(this, endLayout, "Junction owned by Beacon", true, 10));

        add(new ScoreType(this, endLayout, "Parked in Terminal", true, 2));

        add(new ScoreType(this, endLayout, "Completed Circuit", true, 20));
    }

    // to go teleOp activity
    public void launchTeleBack(View view) {
        Intent intent = new Intent(this, TeleActivity.class);

        intent.putExtra(FROM_BACK, true); // need to saved data bc we are going back to activity so we should resest like we coming from "from new"

        startActivity(intent);
    }

    // go to final activity
    public void launchFinal(View view) {
        Intent intent = new Intent(this, FinalActivity.class);
        intent.putExtra("AutoTotal", fromIntent.getIntExtra("AutoTotal", 0));
        intent.putExtra("TeleTotal", fromIntent.getIntExtra("TeleTotal", 0));
        intent.putExtra("EndTotal", getTotalScore());

        startActivity(intent);
    }
}