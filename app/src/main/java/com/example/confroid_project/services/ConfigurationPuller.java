package com.example.confroid_project.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.confroid_project.db.ConfigDb;
import com.example.confroid_project.db.ConfigurationVersions;

public class ConfigurationPuller extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ConfigDb db = new ConfigDb(this.getApplicationContext());

        ConfigurationVersions config;

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
            if (config.getValue() == null) {
                Log.e("conf value", "does not exist");
            } else {
                outgoingIntent.putExtra("content", config.getValue());
                outgoingIntent.putExtra("config_name", config.getName());
            }


            outgoingIntent.putExtra("requestId", requestId);
            outgoingIntent.setClassName(app_name, receiver);
            ComponentName c = this.startService(outgoingIntent);

            if (c == null)
                Log.e("faillllll", "failed to start with " + outgoingIntent);
            else
                Log.d("senddd", config.getValue()==null?"NULL":config.getValue());
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
