package com.example.android.inventory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.InputType;

import static com.example.android.inventory.InventoryContract.*;

/**
 * Created by manmohan on 23/6/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "storage.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + InventoryEntry.TABLE_NAME + " ( "
                + InventoryEntry._ID + " integer primary key autoincrement, "
                + InventoryEntry.COLUMN_NAME + " text not null, "
                + InventoryEntry.COLUMN_QUANTITY + " integer not null, "
                + InventoryEntry.COLUMN_IMAGE + " BLOB, "
                + InventoryEntry.COLUMN_PRICE + " integer not null "
                + ");";
        db.execSQL(createTable);
        /*
        String createTable = "create table " + InventoryEntry.TABLE_NAME + " ( "
                + InventoryEntry._ID + " integer primary key autoincrement, "
                + InventoryEntry.COLUMN_NAME + " text not null, "
                + InventoryEntry.COLUMN_QUANTITY + " integer not null, "
                + InventoryEntry.COLUMN_PRICE + " integer not null, "
                + InventoryEntry.COLUMN_IMAGE + "BLOB );";
        db.execSQL(createTable);
        */
        /*
        String table = "create table " + InventoryEntry.TABLE_NAME + "( "
                + InventoryEntry._ID + " integer primary key autoincrement, "
                + InventoryEntry.COLUMN_NAME + " text not null"
                + InventoryEntry.COLUMN_PRICE + " integer not null, "
                + InventoryEntry.COLUMN_QUANTITY + " integer not null, "
                + InventoryEntry.COLUMN_IMAGE + " BLOB );";
        db.execSQL(table);
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
