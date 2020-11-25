package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;

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

    private List<Integer> counters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        AutoLayout = findViewById(R.id.AutoLayout);

        counters = new ArrayList<>();

        addTapScore("rings", 5);
        addTapScore("wobble", 5);
    }

    public void addTapScore(String scoringName, int scoringIncrement){

        //counters.add()

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameView = new TextView(this);
        nameView.setText("   " + scoringName + ":         ");
        layout.addView(nameView);

        Button buttonDown = new Button(this);
        buttonDown.setText("-1");
        layout.addView(buttonDown);

        TextView countView = new TextView(this);
        countView.setText("#");
        layout.addView(countView);

        Button buttonUp = new Button(this);
        buttonUp.setText("+1");
        layout.addView(buttonUp);

        TextView scoreView = new TextView(this);
        scoreView.setText("Score: 0"); // calcIncrement(scoringIncrement, count)
        layout.addView(scoreView);

        AutoLayout.addView(layout);
    }

    private int calcIncrement(int increment, int count){
        return increment * count;
    }

    private void buttonListener(Button button, int plusOrMinus){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }
}