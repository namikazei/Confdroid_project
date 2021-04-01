package com.example.confroid_project.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;


public class MainActivity extends AppCompatActivity {
    private RecyclerView rView;
    private ApplicationResultsAdapter appAdapter;
    private EditText searchTxt;
    private Button butSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchTxt = findViewById(R.id.searchTxt);
        butSearch = findViewById(R.id.butSearch);
        rView = findViewById(R.id.recyclerView);
        appAdapter = new ApplicationResultsAdapter(this);
        rView.setAdapter(appAdapter);
        rView.setLayoutManager(createLM());
        butSearch.setOnClickListener(view -> {
            appAdapter.setApps(searchTxt.getText().toString());
            appAdapter.notifyDataSetChanged();
        });
    }

    private RecyclerView.LayoutManager createLM() {
        int span = 1;
        int orientation = RecyclerView.VERTICAL;
        return new GridLayoutManager(this, span, orientation, false);
    }
}
