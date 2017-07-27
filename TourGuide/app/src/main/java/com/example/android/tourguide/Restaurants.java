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

public class Restaurants extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        String[] strings = getResources().getStringArray(R.array.restaurants);

        ArrayList<Words> arrayList = new ArrayList<>();
        arrayList.add(new Words(strings[0], R.drawable.thaichi));
        arrayList.add(new Words(strings[1], R.drawable.tavolomondo));
        arrayList.add(new Words(strings[2], R.drawable.wallofasia));
        arrayList.add(new Words(strings[3], R.drawable.bluechilli));
        arrayList.add(new Words(strings[4], R.drawable.zyka));
        arrayList.add(new Words(strings[5], R.drawable.masalabar));

        WordAdapter wordAdapter = new WordAdapter(Restaurants.this, arrayList, R.color.Restaurants);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
    }
}
