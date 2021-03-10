package com.example.confroid_project.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;


public class MainActivity extends AppCompatActivity {
    private RecyclerView rView;
    private ApplicationResultsAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = findViewById(R.id.recyclerView);
        appAdapter = new ApplicationResultsAdapter(this);
        rView.setAdapter(appAdapter);
        rView.setLayoutManager(createLM());
    }

    private RecyclerView.LayoutManager createLM() {
        int span = 1;
        int orientation = rView.VERTICAL;
        return new GridLayoutManager(this, span, orientation, false);
    }
}
