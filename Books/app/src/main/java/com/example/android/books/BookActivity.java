package com.example.android.books;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private ArrayAdapter mAdapter;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        findViewById(R.id.hint).setVisibility(View.GONE);
        findViewById(R.id.search).setVisibility(View.GONE);
        findViewById(R.id.button).setVisibility(View.GONE);
        findViewById(R.id.output).setVisibility(View.GONE);
        findViewById(R.id.loading).setVisibility(View.VISIBLE);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(BookActivity.this, getIntent().getStringExtra("url"));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        findViewById(R.id.loading).setVisibility(View.GONE);

        if (data == null || data.isEmpty()) {
            findViewById(R.id.output).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.output)).setText("No Results Found");
            return;
        }

        findViewById(R.id.list).setVisibility(View.VISIBLE);
        mAdapter = new BookAdapter(BookActivity.this, data);
        ((ListView) findViewById(R.id.list)).setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        if (mAdapter != null)
            mAdapter.clear();
    }
}
