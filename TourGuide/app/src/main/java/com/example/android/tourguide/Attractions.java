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

public class Attractions extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        String[] stringArrayList = getResources().getStringArray(R.array.attractions);

        ArrayList<Words> arrayList = new ArrayList<>();
        arrayList.add(new Words(stringArrayList[0], R.drawable.goldentemple));
        arrayList.add(new Words(stringArrayList[1], R.drawable.ranjitsinghmuseum));
        arrayList.add(new Words(stringArrayList[2], R.drawable.mandir));
        arrayList.add(new Words(stringArrayList[3], R.drawable.jallianwalabagh));
        arrayList.add(new Words(stringArrayList[4], R.drawable.suncity));

        WordAdapter wordAdapter = new WordAdapter(Attractions.this, arrayList, R.color.Attractions);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
    }
}
