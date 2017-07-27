package com.example.android.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manmohan on 18/5/17.
 */

public class WordAdapter extends ArrayAdapter<Words> {

    private int mBackground;

    public WordAdapter(Context context, ArrayList<Words> arrayList, int color)
    {
        super(context, 0, arrayList);
        mBackground = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null)
        {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        ((ImageView) listView.findViewById(R.id.image)).setImageResource(getItem(position).getImageId());
        (listView.findViewById(R.id.root)).setBackgroundResource(mBackground);
        ((TextView) listView.findViewById(R.id.text)).setText(getItem(position).getPlace());

        return listView;
    }
}
