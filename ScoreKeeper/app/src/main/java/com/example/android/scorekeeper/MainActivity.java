package com.example.android.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForTeamA();
        displayForTeamB();
    }

    public void displayForTeamA() {
        ((TextView) findViewById(R.id.team_a_score)).setText(String.valueOf(scoreTeamA));
    }

    public void displayForTeamB() {
        ((TextView) findViewById(R.id.team_b_score)).setText(String.valueOf(scoreTeamB));
    }

    public void aScore3(View view)
    {
        scoreTeamA += 3;
        displayForTeamA();
    }

    public void aScore2(View view)
    {
        scoreTeamA += 2;
        displayForTeamA();
    }

    public void aFreeThrow(View view)
    {
        scoreTeamA += 1;
        displayForTeamA();
    }

    public void bScore3(View view)
    {
        scoreTeamB += 3;
        displayForTeamB();
    }

    public void bScore2(View view)
    {
        scoreTeamB += 2;
        displayForTeamB();
    }

    public void bFreeThrow(View view)
    {
        scoreTeamB += 1;
        displayForTeamB();
    }

    public void Reset(View view)
    {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA();
        displayForTeamB();
    }
}
