package com.example.confroid_project.receivers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.confroid_project.R;
import com.example.confroid_project.confroidUtils.ConfroidConstants;

public class AppsIntentsManagerActivity extends AppCompatActivity {
    // En attendant de trouver une meilleure solution que d'utiliser une activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_intents_manager);

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
                TextView tv_received = (TextView) findViewById(R.id.tv_received);
                tv_received.setText(intentType + " : Hello from " + intent.getExtras().getString(ConfroidConstants.APPLICATION_NAME));
            } else if (intentType.equals(ConfroidConstants.CONFIGURATION_INTENT)) {
                // a voir les autres cas apres
            }
        }
    }
}
