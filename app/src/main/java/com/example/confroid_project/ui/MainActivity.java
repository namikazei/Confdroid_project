package com.example.confroid_project.ui;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;
import com.example.confroid_project.receivers.TokenDispenser;


public class MainActivity extends AppCompatActivity {
    private RecyclerView rView;
    private ApplicationResultsAdapter appAdapter;
    private TokenDispenser tokenDispenser = new TokenDispenser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter("GET_TOKEN");
        registerReceiver(tokenDispenser, intentFilter);

        rView = findViewById(R.id.recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        appAdapter = new ApplicationResultsAdapter(this);
        rView.setAdapter(appAdapter);
        rView.setLayoutManager(createLM());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(tokenDispenser);
    }

    private RecyclerView.LayoutManager createLM() {
        int span = 1;
        int orientation = rView.VERTICAL;
        return new GridLayoutManager(this, span, orientation, false);
    }
}
