package com.example.android.books;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manmohan on 16/5/17.
 */

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Book> fetchBooks(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Problem Loading Request", e);
        }

        return extractDataFromJson(jsonResponse);
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error Fetching URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readJsonStream(inputStream);
            }
            else
                Log.e(LOG_TAG, "Error Code" + urlConnection.getResponseCode());
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving Data ", e);
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String readJsonStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractDataFromJson(String jsonResponse) {
        if (jsonResponse == null) return null;
        List<Book> books = new ArrayList<Book>();
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                if (!volumeInfo.has("title")) {
                    throw new IllegalArgumentException("Title not found");
                }
                String title = volumeInfo.getString("title");

                if (!volumeInfo.has("authors")) {
                    throw new IllegalArgumentException("Authors not Found");
                }
                JSONArray authorName = volumeInfo.getJSONArray("authors");

                String author = authorName.getString(0);

                String pageCount;
                if (volumeInfo.has("pageCount"))
                    pageCount = volumeInfo.getString("pageCount");
                else
                    pageCount = "--";

                String rating;
                if (!volumeInfo.has("averageRating"))
                    rating = "--";
                else
                    rating = volumeInfo.getString("averageRating");

                Book book = new Book(title, author, rating, pageCount);

                books.add(book);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON ", e);
        }

        return books;
    }
}
