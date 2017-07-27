package com.example.android.inventory;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import static com.example.android.inventory.InventoryContract.*;

/**
 * Created by manmohan on 23/6/17.
 */

public class InventoryProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ITEMS = 100;
    private static final int ITEM_ID = 101;
    DbHelper mDbHelper;
    SQLiteDatabase mDatabase;

    static {
        mUriMatcher.addURI(InventoryEntry.CONTENT_AUTHORITY, InventoryEntry.PATH, ITEMS);
        mUriMatcher.addURI(InventoryEntry.CONTENT_AUTHORITY, InventoryEntry.PATH + "/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case ITEMS:
                return InventoryEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("getType - uri didnot match");
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        mDatabase = mDbHelper.getReadableDatabase();
        Cursor cursor;

        switch (mUriMatcher.match(uri)) {
            case ITEMS:
                cursor = mDatabase.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = mDatabase.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Query uri didnot match");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String name = values.getAsString(InventoryEntry.COLUMN_NAME);
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        Integer price = values.getAsInteger(InventoryEntry.COLUMN_PRICE);
        if (price == null || !InventoryEntry.isValidPrice(price)) {
            throw new IllegalArgumentException("Price not entered");
        }

        Integer quantity = values.getAsInteger(InventoryEntry.COLUMN_QUANTITY);
        if (quantity == null || !InventoryEntry.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity not valid");
        }

        mDatabase = mDbHelper.getWritableDatabase();
        long id;
        switch (mUriMatcher.match(uri)) {
            case ITEMS:
                id = mDatabase.insert(InventoryEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Insert uri not valid");
        }

        if (id == -1) {
            Toast.makeText(getContext(), "Data not inserted", Toast.LENGTH_SHORT).show();
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.v("Provider update", "uri is " + uri);

        String name = values.getAsString(InventoryEntry.COLUMN_NAME);
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        Integer price = values.getAsInteger(InventoryEntry.COLUMN_PRICE);
        if (price == null || !InventoryEntry.isValidPrice(price)) {
            throw new IllegalArgumentException("Price not entered");
        }

        Integer quantity = values.getAsInteger(InventoryEntry.COLUMN_QUANTITY);
        if (quantity == null || !InventoryEntry.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity not valid");
        }

        int row = 0;
        mDatabase = mDbHelper.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        Log.v("Provider update ", "match value is " + match);
        switch (match) {
            case ITEMS:
                row = mDatabase.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                Log.v("Provider itemId", "Selection is " + selection + "\n Args are " + selectionArgs);
                row = mDatabase.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Update uri didnot match");
        }
        if (row != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int row = 0;
        mDatabase = mDbHelper.getWritableDatabase();
        switch (mUriMatcher.match(uri)) {
            case ITEMS:
                row = mDatabase.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                row = mDatabase.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete uri didnot match");
        }
        if (row != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
