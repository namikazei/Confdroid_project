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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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

            try {
                db.addConfiguration("app1", "conf1");
                db.addConfiguration("app1", "conf2");
                db.addConfiguration("app1", "conf3");
                db.addConfiguration("app2", "conf1");
                db.addConfiguration("app2", "conf2");
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        out.setOnClickListener(v -> {
            ArrayList<Config> output = null;
            try {
//                output = db.getAllConfiguration();
//                StringBuilder str = new StringBuilder();
//                for (Config a : output) {
//                    str.append(a.toString());
//                }
                String str = String.valueOf(db.countConf("app2"));
                txt.setText(str.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
