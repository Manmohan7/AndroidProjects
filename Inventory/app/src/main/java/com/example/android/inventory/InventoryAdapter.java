package com.example.android.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static com.example.android.inventory.InventoryContract.*;

/**
 * Created by manmohan on 23/6/17.
 */

public class InventoryAdapter extends CursorAdapter {
    public InventoryAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        final String name = cursor.getString(cursor.getColumnIndex(InventoryEntry.COLUMN_NAME));
        final Integer price = cursor.getInt(cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE));
        final Integer quantity = cursor.getInt(cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY));
        view.findViewById(R.id.button).setTag(cursor.getString(cursor.getColumnIndex(InventoryEntry._ID)));

        ((TextView) view.findViewById(R.id.name)).setText(name);
        ((TextView) view.findViewById(R.id.price)).setText(String.valueOf(price));
        ((TextView) view.findViewById(R.id.quantity)).setText(String.valueOf(quantity));

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = quantity;
                if (quant > 0)
                    quant--;

                ContentValues values = new ContentValues();
                values.put(InventoryEntry.COLUMN_NAME, name);
                values.put(InventoryEntry.COLUMN_PRICE, price);
                values.put(InventoryEntry.COLUMN_QUANTITY, quant);
                context.getContentResolver().update(
                        ContentUris.withAppendedId(InventoryEntry.CONTENT_URI,
                                Long.valueOf(view.findViewById(R.id.button).getTag().toString())),
                        values, null, null);
            }
        });
    }
}
