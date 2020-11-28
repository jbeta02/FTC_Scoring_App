package com.example.scoringapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class ScoreType {

    private LinearLayout linearLayout;
    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String name;
    private boolean isTapScore;

    // default values
    private int count = 0;
    public int score = 0;
    private boolean switchState = false;

    private int scoringIncrement;

    private TextView countView;
    private TextView scoreView;

    private static final String TAP_DATA = "tap count";
    private static final String SWITCH_DATA = "switch state";


    public ScoreType(Context context, LinearLayout linearLayout, String name, Boolean isTapScore, int scoringIncrement){
        this.context = context;
        this.linearLayout = linearLayout;
        this.name = name;
        this.isTapScore = isTapScore;
        this.scoringIncrement = scoringIncrement;

        sharedPreferences = context.getSharedPreferences(name + "_pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        countView = new TextView(context);
        scoreView = new TextView(context);
    }

    public void addScoreTypeView(){
        if (isTapScore){
            addTapScore();
        }

        else{
            addSwitchScore();
        }
    }

    public void addTapScore(){
        score = calcIncrement(scoringIncrement, count);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameView = new TextView(context);
        nameView.setText("   " + name + ":     ");
        layout.addView(nameView);

        Button buttonDown = new Button(context);
        buttonDown.setText("-1");
        layout.addView(buttonDown);
        buttonListener(buttonDown, -1);

        countView.setText(Integer.toString(count));
        layout.addView(countView);

        Button buttonUp = new Button(context);
        buttonUp.setText("+1");
        layout.addView(buttonUp);
        buttonListener(buttonUp, 1);

        scoreView.setText("Score: " + score);
        layout.addView(scoreView);

        linearLayout.addView(layout);
    }

    private int calcIncrement(int increment, int count){
        return increment * count;
    }

    private void buttonListener(Button button, final int plusOrMinus){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                count += plusOrMinus;
                score = calcIncrement(scoringIncrement, count);

                countView.setText(Integer.toString(count));
                scoreView.setText("Score: " + score);

                saveData();
            }
        });
    }

    public void addSwitchScore(){
        score = calcSwitchScore(scoringIncrement, switchState);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameView = new TextView(context);
        nameView.setText("   " + name + ":     ");
        layout.addView(nameView);

        Switch aSwitch = new Switch(context);
        layout.addView(aSwitch);
        aSwitch.setChecked(switchState);
        switchListener(aSwitch);

        scoreView.setText("Score: " + score);
        layout.addView(scoreView);

        linearLayout.addView(layout);

    }

    public void switchListener(Switch aSwitch){
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchState = isChecked;

                score = calcSwitchScore(scoringIncrement, switchState);

                scoreView.setText("Score: " + score);
                Log.d("logScoreType", "switch state: " + Boolean.toString(switchState));
                saveData();
            }
        });
    }

    private int calcSwitchScore(int increment, Boolean isChecked){
        if (isChecked){
            return increment;
        }

        else {
            return 0;
        }
    }

    public void saveData(){
        editor.putInt(TAP_DATA, count);
        editor.putBoolean(SWITCH_DATA, switchState);

        editor.apply();

    }

    public void loadData(){
        count = sharedPreferences.getInt(TAP_DATA, 0);
        switchState = sharedPreferences.getBoolean(SWITCH_DATA, false);
    }

    public void clearData(){
        editor.clear();
    }


}
