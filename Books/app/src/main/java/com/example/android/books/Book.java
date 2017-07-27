package com.example.android.books;

/**
 * Created by manmohan on 15/5/17.
 */

public class Book {
    private String mTitle;
    private String mAuthor;
    private String mRating;
    private String mPageCount;

    public Book(String title, String author, String rating, String pageCount)
    {
        this.mTitle = title;
        this.mAuthor = author;
        mRating = rating;
        mPageCount = pageCount;
    }

    public String getTitle()
    {
        return this.mTitle;
    }

    public String getAuthor()
    {
        return this.mAuthor;
    }

    public String getRating() { return mRating; }

    public String getPageCount() { return mPageCount; }
}
