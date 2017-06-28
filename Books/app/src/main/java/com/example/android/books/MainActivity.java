package com.example.android.books;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String mUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private String query;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo == null || !netInfo.isConnected()) {
                findViewById(R.id.output).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.output)).setText("No Internet Connection");
                return;
            }

            String url = mUrl;
            query = ((EditText) findViewById(R.id.search)).getText().toString();

            if (query.contains(" ")) {
                String[] strings = query.split(" ");
                for (int i = 0; i < strings.length; i++) {
                    url += strings[i];
                    if (i < strings.length - 1)
                        url += "+";
                }
            }
            else {
                url += query;
            }

            Intent intent = new Intent(MainActivity.this, BookActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        findViewById(R.id.output).setVisibility(View.GONE);
        findViewById(R.id.list).setVisibility(View.GONE);
        findViewById(R.id.loading).setVisibility(View.GONE);

        ((EditText)findViewById(R.id.search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = s.length(); i > 0; i--) {

                    if(s.subSequence(i-1, i).toString().equals("\n"))
                        s.replace(i-1, i, "");
                }
            }
        });

        findViewById(R.id.button).setOnClickListener(listener);
    }
}
