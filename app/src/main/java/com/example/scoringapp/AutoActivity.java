package com.example.scoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

    private int count = 0;

    private TextView countView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String name = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        AutoLayout = findViewById(R.id.AutoLayout);

        counters = new ArrayList<>();

        sharedPreferences = this.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadData();

        addTapScore("rings", 5);
    }

    public void addTapScore(String scoringName, int scoringIncrement){

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameView = new TextView(this);
        nameView.setText("   " + scoringName + ":         ");
        layout.addView(nameView);

        Button buttonDown = new Button(this);
        buttonDown.setText("-1");
        layout.addView(buttonDown);
        buttonListener(buttonDown, -1);

        countView = new TextView(this);
        countView.setText(Integer.toString(count));
        layout.addView(countView);

        Button buttonUp = new Button(this);
        buttonUp.setText("+1");
        layout.addView(buttonUp);
        buttonListener(buttonUp, 1);

        TextView scoreView = new TextView(this);
        scoreView.setText("Score: 0"); // calcIncrement(scoringIncrement, count)
        layout.addView(scoreView);

        AutoLayout.addView(layout);
    }

    private int calcIncrement(int increment, int count){
        return increment * count;
    }

    private void buttonListener(Button button, final int plusOrMinus){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                count += plusOrMinus;
                countView.setText(Integer.toString(count));
                saveData();
            }
        });
    }

    public void saveData(){
        if (count != 0){
            editor.putInt(name, count);
        }

        editor.apply();

    }

    public void loadData(){
            count = sharedPreferences.getInt(name, 0);
    }
}