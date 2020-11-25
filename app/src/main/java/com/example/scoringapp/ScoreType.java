package com.example.scoringapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreType {

    private LinearLayout linearLayout;
    private Context context;
    private String name;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int count = -1;
    private int score = -1;

    public ScoreType(Context context, LinearLayout linearLayout, String name){
        this.context = context;
        this.linearLayout = linearLayout;
        this.name = name;

        sharedPreferences = context.getSharedPreferences(this.name + "_pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void addTapScore(String scoringName, int scoringIncrement){

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameView = new TextView(context);
        nameView.setText("   " + scoringName + ":         ");
        layout.addView(nameView);

        Button buttonDown = new Button(context);
        buttonDown.setText("-1");
        layout.addView(buttonDown);

        TextView countView = new TextView(context);
        countView.setText("#");
        layout.addView(countView);

        Button buttonUp = new Button(context);
        buttonUp.setText("+1");
        layout.addView(buttonUp);

        TextView scoreView = new TextView(context);
        scoreView.setText("Score: 0"); // calcIncrement(scoringIncrement, count)
        layout.addView(scoreView);

        linearLayout.addView(layout);
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

    public void saveData(){
        if (count != -1){
            editor.putInt(name, count);
        }

        editor.apply();

    }

    public void loadData(){
        count = sharedPreferences.getInt(name, 0);
    }


}
