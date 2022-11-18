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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeleActivity extends GameModeActivity {

    public static String LAUNCH_NEW = "from new Tele";
    public static String FROM_BACK = "from back";

    private boolean fromNew;
    private boolean fromBack = false;

    LinearLayout teleLayout;

    private Intent fromIntent;

    // format layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele);

        teleLayout = findViewById(R.id.TeleLayout);

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
        LinearLayout holdViews = new LinearLayout(this);
        holdViews.setOrientation(LinearLayout.VERTICAL);

        createScoreTypes(holdViews);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(holdViews);

        teleLayout.addView(scrollView);

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
    public void createScoreTypes(LinearLayout teleLayout){
        add(new ScoreType(this, teleLayout, "Scored in Terminal (tele)", true, 1));

        add(new ScoreType(this, teleLayout, "Scored on ground Junction (tele)", true, 2));

        add(new ScoreType(this, teleLayout, "Scored on low Junction (tele)", true, 3));

        add(new ScoreType(this, teleLayout, "Scored on mid Junction (tele)", true, 4));

        add(new ScoreType(this, teleLayout, "Scored on high Junction (tele)", true, 5));

    }

    // launch auto auto activity
    public void launchAutoBack(View view) {
        Intent intent = new Intent(this, AutoActivity.class);

        intent.putExtra(FROM_BACK, true); // need to saved data bc we are going back to activity so we should resest like we coming from "from new"

        startActivity(intent);
    }

    // launch endgame activity
    public void launchEnd(View view) {
        Intent intent = new Intent(this, EndActivity.class);

        intent.putExtra(LAUNCH_NEW, fromNew);
        intent.putExtra("AutoTotal", fromIntent.getIntExtra("AutoTotal", 0));
        intent.putExtra("TeleTotal", getTotalScore());

        startActivity(intent);
    }
}