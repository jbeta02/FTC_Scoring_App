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

public class AutoActivity extends GameModeActivity {

    public static String LAUNCH_NEW = "from new Auto";
    public static String FROM_BACK = "from back";

    private boolean fromNew;
    private boolean fromBack = false;

    LinearLayout autoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        autoLayout = findViewById(R.id.AutoLayout);

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

        autoLayout.addView(scrollView);


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
    public void createScoreTypes(LinearLayout autoLayout){

        add(new ScoreType(this, autoLayout, "Scored in Terminal", true, 1));

        add(new ScoreType(this, autoLayout, "Scored on ground Junction", true, 2));

        add(new ScoreType(this, autoLayout, "Scored on low Junction", true, 3));

        add(new ScoreType(this, autoLayout, "Scored on mid Junction", true, 4));

        add(new ScoreType(this, autoLayout, "Scored on high Junction", true, 5));

        add(new ScoreType(this, autoLayout, "Parked in Terminal or Substation", false, 2));

        add(new ScoreType(this, autoLayout, "Parked in Signal zone", false, 10));

        add(new ScoreType(this, autoLayout, "Parked in Signal zone using team sleeve", false, 20));

    }

    // go to teleOp activity
    public void launchTele(View view) {
        Intent intent = new Intent(this, TeleActivity.class);

        intent.putExtra(LAUNCH_NEW, fromNew);
        intent.putExtra("AutoTotal", getTotalScore());

        startActivity(intent);
    }

    // go to home activity
    public void launchHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}