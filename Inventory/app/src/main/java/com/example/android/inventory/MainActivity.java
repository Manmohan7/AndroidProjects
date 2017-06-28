package com.example.android.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.example.android.inventory.InventoryContract.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private FloatingActionButton mFab;
    private ListView mListView;
    private View mEmptyView;
    private InventoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mEmptyView = findViewById(R.id.empty_view);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setEmptyView(mEmptyView);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mAdapter = new InventoryAdapter(this, null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.setData(ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id));
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_dummy:
                insertDummy();
                return true;
            case R.id.delete_all:
                deleteAll();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertDummy() {
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_NAME, "Cricket Bat");
        values.put(InventoryEntry.COLUMN_PRICE, 25);
        values.put(InventoryEntry.COLUMN_QUANTITY, 7);
        values.put(InventoryEntry.COLUMN_IMAGE, (byte[]) null);

        getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
    }

    private void deleteAll() {
        getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{InventoryEntry._ID, InventoryEntry.COLUMN_NAME,
                InventoryEntry.COLUMN_PRICE, InventoryEntry.COLUMN_QUANTITY};
        return new CursorLoader(this, InventoryEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}