package com.example.confroid_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;
import com.example.confroid_project.db.App;
import com.example.confroid_project.db.Config;
import com.example.confroid_project.db.ConfigDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView rView;
    private ApplicationResultsAdapter appAdapter;
    private ConfigDb db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = new ConfigDb(this);

        db.addApplication("app1", "ger546erg");
        db.addApplication("app2", "g656edf6z");

        rView = findViewById(R.id.recyclerView);

        appAdapter = new ApplicationResultsAdapter(this);

        rView.setAdapter(appAdapter);
        rView.setLayoutManager(createLM());
    }


    private RecyclerView.LayoutManager createLM(){
        int span = 1;
        int orientation = rView.VERTICAL;
        return new GridLayoutManager(this,span,orientation,false);
    }
}
