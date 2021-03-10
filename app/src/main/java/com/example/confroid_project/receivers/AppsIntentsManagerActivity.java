package com.example.confroid_project.receivers;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.confroid_project.R;
import com.example.confroid_project.confroidUtils.ConfroidConstants;
import com.example.confroid_project.confroidUtils.Utils;
import com.example.confroid_project.db.ConfigDb;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppsIntentsManagerActivity extends AppCompatActivity {

    // En attendant de trouver une meilleure solution que d'utiliser une activité

    ConfigDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_intents_manager);

        db = new ConfigDb(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (ConfroidConstants.TEXT_PLAIN.equals(type)) {
                handleSendText(intent);
            }
        }
    }

    void handleSendText(Intent intent) {
        String intentType = intent.getExtras().getString(ConfroidConstants.INTENT_TYPE);
        if (intentType != null) {
            if (intentType.equals(ConfroidConstants.FIRST_INTENT_REQUEST_TOKEN)) {

                String packageName = intent.getExtras().getString(ConfroidConstants.APPLICATION_PACKAGE_NAME);
                String token = Utils.getToken(intent.getExtras().getString(ConfroidConstants.APPLICATION_NAME));
                db.addApplication(packageName, token); /** add name and token to db */
                Intent result = new Intent();
                result.putExtra("TOKEN", token);
                setResult(RESULT_OK, result);
                finish();

            } else if (intentType.equals(ConfroidConstants.CONFIGURATION_INTENT)) {

                // Ici on récupere la config pour la mettre dans la BDD
                // Verifier le Token !!

                String appName = intent.getExtras().getString(ConfroidConstants.APPLICATION_PACKAGE_NAME);
                if (db.getAppToken(appName) != null){
                    Bundle config = intent.getExtras().getBundle("CONFIG");

                    Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                    HashMap<String, String> map = new Gson().fromJson(config.getString("d"), type);

                    boolean addconf = db.addConfiguration(appName, map.toString());

                    Intent result = new Intent();
                    if (addconf)
                        setResult(RESULT_OK, result);
                    else
                        setResult(RESULT_CANCELED, result);

                    finish();
                }


                //String appName = intent.getExtras().getString(ConfroidConstants.APPLICATION_NAME);
                //String confName = config.getString("configName");
                //Toast.makeText(this, appName + " : " + confName, Toast.LENGTH_LONG).show();
                //result.putExtra("TOKEN", Utils.getToken(intent.getExtras().getString(ConfroidConstants.APPLICATION_NAME)));
                //setResult(RESULT_OK, result);


            }
        }
    }
}
