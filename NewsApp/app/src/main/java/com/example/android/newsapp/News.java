package com.example.android.newsapp;

/**
 * Created by manmohan on 18/6/17.
 */

public class News {
    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrl;

    public News(String title, String section, String date, String url) {
        mTitle = title;
        mSection = section;
        String[] strings = date.split("T");
        mDate = strings[0];
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
