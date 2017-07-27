package com.example.android.tourguide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by manmohan on 18/5/17.
 */

public class Hotels extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        String[] strings = getResources().getStringArray(R.array.hotels);

        ArrayList<Words> arrayList = new ArrayList<>();
        arrayList.add(new Words(strings[0], R.drawable.hyatt));
        arrayList.add(new Words(strings[1], R.drawable.hongkong));
        arrayList.add(new Words(strings[2], R.drawable.pr_residency));
        arrayList.add(new Words(strings[3], R.drawable.regenta));
        arrayList.add(new Words(strings[4], R.drawable.sawera));
        arrayList.add(new Words(strings[5], R.drawable.raddison));
        arrayList.add(new Words(strings[6], R.drawable.ranjit));
        arrayList.add(new Words(strings[7], R.drawable.humble));

        ListView listView = (ListView) findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(Hotels.this, arrayList, R.color.Hotels);
        listView.setAdapter(wordAdapter);
    }
}