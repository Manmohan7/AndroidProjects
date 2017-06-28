package com.example.android.newsapp;

import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private TextView mTextView;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private String mUrl = "https://content.guardianapis.com/search?q=news&api-key=test";
    private ArrayAdapter mAdapter = null;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        mListView = (ListView) findViewById(R.id.list);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setText("No Internet Connection");
        }
        else {
            mTextView.setVisibility(View.GONE);
            loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new Utils(this, mUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, final List<News> data) {
        mProgressBar.setVisibility(View.GONE);

        if (data == null || data.isEmpty()) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("No Results Found");
        }
        else {
            mListView.setAdapter(new NewsAdapter(this, data));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(data.get(position).getUrl()));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        if (mAdapter != null) mAdapter.clear();
    }
}
