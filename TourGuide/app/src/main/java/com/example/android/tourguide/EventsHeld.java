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

public class EventsHeld extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        String[] stringArrayList = getResources().getStringArray(R.array.events);

        ArrayList<Words> arrayList = new ArrayList<>();
        arrayList.add(new Words(stringArrayList[0], R.drawable.mangomela));
        arrayList.add(new Words(stringArrayList[1], R.drawable.sportsfest));
        arrayList.add(new Words(stringArrayList[2], R.drawable.hollamohalla));
        arrayList.add(new Words(stringArrayList[3], R.drawable.baisakhi));

        ListView listView = (ListView) findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(EventsHeld.this, arrayList, R.color.Events);
        listView.setAdapter(wordAdapter);
    }
}
