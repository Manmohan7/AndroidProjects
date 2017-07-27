package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manmohan on 18/6/17.
 */

public class Utils extends AsyncTaskLoader<List<News>> {
    private String mUrl;

    public Utils(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (TextUtils.isEmpty(mUrl))
            return null;

        URL url = null;
        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            Log.v("Utils", "Url not created");
        }

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.v("Utils", "Did not get Json Response");
        }

        return extractFromJson(jsonResponse);
    }

    private String makeHttpRequest(URL url) throws IOException {
        if (url == null)
            return null;

        String jsonResponse = "";
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                jsonResponse = readInput(inputStream);
            }
        }
        catch (IOException e) {
            Log.v("Utils", "Error Connection to Internet");
        }

        return jsonResponse;
    }

    private String readInput(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.defaultCharset());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }

    private List<News> extractFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse))
            return null;

        List<News> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONObject response = obj.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                list.add(new News(result.getString("webTitle"), result.getString("sectionName"),
                        result.getString("webPublicationDate"), result.getString("webUrl")));
            }
        } catch (JSONException e) {
            Log.v("Utils", "Json Object not created");
        }

        return list;
    }
}