package com.example.confroid_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.confroid_project.R;
import com.example.confroid_project.db.App;
import com.example.confroid_project.db.Config;
import com.example.confroid_project.db.ConfigDb;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ConfigDb db;
    Button in, out;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //data base
        db = new ConfigDb(this);

        in = findViewById(R.id.bddin);
        out = findViewById(R.id.bddout);
        txt = findViewById(R.id.txt);

        in.setOnClickListener(v -> {
            db.addApplication("app1", "ger546erg");
            db.addApplication("app2", "g656edf6z");
        });

        out.setOnClickListener(v -> {
            ArrayList<App> output = db.getApps();
            String str = "";
            for (App a : output){
                str+=output.toString();
            }
            txt.setText(str);
        });
    }
}
