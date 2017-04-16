package com.example.android.practiceset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA() {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(scoreTeamA));
    }

    public void displayForTeamB() {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(scoreTeamB));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForTeamA();
        displayForTeamB();
    }

    public void Score3(View view)
    {
        scoreTeamA += 3;
        displayForTeamA();
    }

    public void Score2(View view)
    {
        scoreTeamA += 2;
        displayForTeamA();
    }

    public void FreeThrow(View view)
    {
        scoreTeamA += 1;
        displayForTeamA();
    }

    public void BScore3(View view)
    {
        scoreTeamB += 3;
        displayForTeamB();
    }

    public void BScore2(View view)
    {
        scoreTeamB += 2;
        displayForTeamB();
    }

    public void BFreeThrow(View view)
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
