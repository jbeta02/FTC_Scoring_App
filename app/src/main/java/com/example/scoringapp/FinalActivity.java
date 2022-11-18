package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FinalActivity extends AppCompatActivity {

    public static String FROM_BACK = "from back";

    public LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        linearLayout = findViewById(R.id.final_linear_layout);

        Intent intent = getIntent();

        int autoScore = intent.getIntExtra("AutoTotal", 0);
        int teleScore = intent.getIntExtra("TeleTotal", 0);
        int endScore = intent.getIntExtra("EndTotal", 0);
        int finalScore = autoScore + teleScore + endScore;

        // display final scores
        addView("Auto Score", linearLayout, autoScore);
        addView("Tele Score", linearLayout, teleScore);
        addView("End Score", linearLayout, endScore);
        addView("Final Score", linearLayout, finalScore);

    }

    // add score result view
    private void addView(String name, LinearLayout linearLayout, int score){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView autoView = new TextView(this);
        autoView.setText("   " + name + ":   ");
        layout.addView(autoView);

        TextView autoScoreView = new TextView(this);
        autoScoreView.setText(Integer.toString(score));
        layout.addView(autoScoreView);

        linearLayout.addView(layout);
    }

    // go back to Endgame activity
    public void launchEnd(View view) {
        Intent intent = new Intent(this, EndActivity.class);

        intent.putExtra(FROM_BACK, true);

        startActivity(intent);
    }

    // go back to Home activity
    public void launchHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    // go back to Auto activity
    public void viewAuto(View view) {
        Intent intent = new Intent(this, AutoActivity.class);

        intent.putExtra(FROM_BACK, true);

        startActivity(intent);
    }

    // go back to TeleOp activity
    public void viewTele(View view) {
        Intent intent = new Intent(this, TeleActivity.class);

        intent.putExtra(FROM_BACK, true);

        startActivity(intent);
    }

    // go back to Endgame activity
    public void viewEnd(View view) {
        Intent intent = new Intent(this, EndActivity.class);

        intent.putExtra(FROM_BACK, true);

        startActivity(intent);
    }
}