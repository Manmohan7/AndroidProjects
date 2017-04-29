package com.example.android.miwok;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
    }

    abstract class NumberListener implements View.OnClickListener
    {
        public void OnClick(View view)
        {

        }
    }
}
