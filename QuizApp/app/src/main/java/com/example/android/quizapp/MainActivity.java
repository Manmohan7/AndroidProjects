package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String getName()
    {
        return ((TextView) findViewById(R.id.name)).getText().toString();
    }

    public void Submit(View view)
    {
        if(getName().equals(""))
        {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if( !((CheckBox)findViewById(R.id.checkbox)).isChecked() )
        {
            Toast.makeText(this, "Please select the checkbox to submit your Answer", Toast.LENGTH_SHORT).show();
            return;
        }

        int marks = 0;

        if ( ( (RadioButton)findViewById(R.id.rg11)).isChecked() )
            marks++;
        if ( ( (CheckBox)findViewById(R.id.rg21)).isChecked() && ( (CheckBox)findViewById(R.id.rg22)).isChecked() )
            marks++;
        if ( ((EditText)findViewById(R.id.text)).getText().toString().equalsIgnoreCase("log") )
            marks++;
        if ( ( (RadioButton)findViewById(R.id.rg41)).isChecked() )
            marks++;
        if ( ( (RadioButton)findViewById(R.id.rg52)).isChecked() )
            marks++;

        if(marks < 3)
            Toast.makeText(this, "Hey " + getName() + ", you can score more than " + marks + " marks.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Congrats " + getName() + ", you have scored " + marks + " marks.", Toast.LENGTH_LONG).show();
    }

}
