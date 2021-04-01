package com.example.confroid_project.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.confroid_project.db.ConfigDb;

public class ConfigurationPuller extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ConfigDb db = new ConfigDb(this.getApplicationContext());

        String config = "";

        String app_token = intent.getStringExtra("TOKEN");
        String app_name = intent.getStringExtra("APP");

        if (db.getAppToken(app_name).equals(app_token)){
            int version = intent.getIntExtra("VERSION", -1);
            int requestId = intent.getIntExtra("REQUEST_ID", 0);
            String destination = intent.getStringExtra("DEST");

            if (version==-1){
                config = db.getLastConfiguration(app_name).getValue();
            }else{
                config = db.getConfiguration(app_name, version).getValue();
            }

            Intent outgoingIntent = new Intent("PULL_CONF_SERVICE");
            outgoingIntent.putExtra("config", config);
            outgoingIntent.putExtra("requestId", requestId);
            outgoingIntent.setClassName(app_name, destination);
            ComponentName c = this.startService(outgoingIntent);

            if (c == null)
                Log.e("faillllll", "failed to start with " + outgoingIntent);
            else
                Log.d("senddd", config);
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
