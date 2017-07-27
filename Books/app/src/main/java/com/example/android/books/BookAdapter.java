package com.example.android.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by manmohan on 16/5/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books)
    {
        super(context, 0, books);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        Book bookCursor = getItem(position);
        ((TextView) listItemView.findViewById(R.id.author)).setText(bookCursor.getAuthor());
        ((TextView) listItemView.findViewById(R.id.book)).setText(bookCursor.getTitle());
        ((TextView) listItemView.findViewById(R.id.rating)).setText(bookCursor.getRating());
        ((TextView) listItemView.findViewById(R.id.page_count)).setText(bookCursor.getPageCount());

        return listItemView;
    }
}
