package com.example.confroid_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;


public class ConfigurationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ConfigurationResultsAdapter confAdapter;
    private TextView ifEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configs_layout);

        ifEmpty = findViewById(R.id.vide);
        recyclerView = findViewById(R.id.recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        confAdapter = new ConfigurationResultsAdapter(this);
        recyclerView.setAdapter(confAdapter);
        recyclerView.setLayoutManager(createLM());

        if (confAdapter.getItemCount() != 0) {
            ifEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private RecyclerView.LayoutManager createLM() {
        int span = 1;
        int orientation = recyclerView.VERTICAL;
        return new GridLayoutManager(this, span, orientation, false);
    }
}
