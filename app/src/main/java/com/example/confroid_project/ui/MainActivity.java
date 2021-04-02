package com.example.confroid_project.ui;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;
import com.example.confroid_project.db.ConfigDb;
import com.example.confroid_project.receivers.TokenDispenser;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rView;
    private ApplicationResultsAdapter appAdapter;
    private EditText searchTxt;
    private Button butSearch;
    private TokenDispenser tokenDispenser = new TokenDispenser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchTxt = findViewById(R.id.searchTxt);
        butSearch = findViewById(R.id.butSearch);
        rView = findViewById(R.id.recyclerView);
        IntentFilter intentFilter = new IntentFilter("GET_TOKEN");
        registerReceiver(tokenDispenser, intentFilter);
        butSearch.setOnClickListener(view -> {
            appAdapter.setApps(searchTxt.getText().toString());
            appAdapter.notifyDataSetChanged();
        });
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
        int orientation = RecyclerView.VERTICAL;
        return new GridLayoutManager(this, span, orientation, false);
    }
}
