package com.example.android.books;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by manmohan on 16/5/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String url;

    public BookLoader(Context context, String url)
    {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if(url == null)
            return null;
        return QueryUtils.fetchBooks(url);
    }
}
