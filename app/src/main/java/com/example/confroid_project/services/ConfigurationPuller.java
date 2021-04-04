package com.example.confroid_project.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.confroid_project.db.ConfigDb;
import com.example.confroid_project.db.ConfigurationVersions;
import com.example.confroid_project.receivers.RestartServices;

public class ConfigurationPuller extends Service {

    @Override
    public void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("RestartService");
        broadcastIntent.putExtra("type","puller");
        broadcastIntent.setClass(this, RestartServices.class);
        this.sendBroadcast(broadcastIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConfigDb db = new ConfigDb(this.getApplicationContext());
        ConfigurationVersions config;

        if (intent.getExtras() != null){
            String app_token = intent.getStringExtra("token");
            String app_name = intent.getStringExtra("app_name");

            if (db.getAppToken(app_name).equals(app_token)) {
                int version = intent.getIntExtra("version", -1);
                int requestId = intent.getIntExtra("requestId", 0);
                String receiver = intent.getStringExtra("receiver");
                String conf_name = intent.getStringExtra("name");

                if (version == -1) {
                    config =  db.getLastConfiguration(app_name, conf_name);
                } else {
                    config =  db.getConfiguration(app_name, conf_name, version);
                }

                Intent outgoingIntent = new Intent("PULL_CONF_SERVICE");
                if (config.getValue() != null) {
                    outgoingIntent.putExtra("content", config.getValue());
                    outgoingIntent.putExtra("config_name", config.getName());
                }

                outgoingIntent.putExtra("requestId", requestId);
                outgoingIntent.setClassName(app_name, receiver);
                this.startService(outgoingIntent);
            }

        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
