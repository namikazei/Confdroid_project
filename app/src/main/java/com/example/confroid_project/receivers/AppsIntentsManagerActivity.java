package com.example.confroid_project.receivers;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.confroid_project.R;
import com.example.confroid_project.confroidUtils.ConfroidConstants;
import com.example.confroid_project.confroidUtils.Utils;

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
                // AF
                //vérifer déja que l'application n'a pas de token dans la BDD

                Intent result = new Intent();
                result.putExtra("TOKEN", Utils.getToken(intent.getExtras().getString(ConfroidConstants.APPLICATION_NAME)));
                setResult(RESULT_OK, result);
                finish();

                // AS
                //TextView tv_received = (TextView) findViewById(R.id.tv_received);
                //tv_received.setText(intentType + " : Hello from " + intent.getExtras().getString(ConfroidConstants.APPLICATION_NAME));

            } else if (intentType.equals(ConfroidConstants.CONFIGURATION_INTENT)) {
                // AF
                // traiter les autres cas
            }
        }
    }
}
