package com.example.confroid_project.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.confroid_project.db.ConfigDb;

public class ConfigurationPuller extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ConfigDb db = new ConfigDb(this.getApplicationContext());

        String config;

        String app_token = intent.getStringExtra("token");
        String app_name = intent.getStringExtra("app");

        if (db.getAppToken(app_name).equals(app_token)){
            int version = intent.getIntExtra("version", -1);
            int requestId = intent.getIntExtra("requestId", 0);
            String receiver = intent.getStringExtra("receiver");
            String conf_name = intent.getStringExtra("confName");

            if (version==-1){
                config = db.getLastConfiguration(app_name, conf_name).getValue();
            }else{
                config = db.getConfiguration(app_name, conf_name, version).getValue();
            }

            Intent outgoingIntent = new Intent("PULL_CONF_SERVICE");
            outgoingIntent.putExtra("content", config);
            outgoingIntent.putExtra("requestId", requestId);
            outgoingIntent.setClassName(app_name, receiver);
            ComponentName c = this.startService(outgoingIntent);

            if (c == null)
                Log.e("faillllll", "failed to start with " + outgoingIntent);
            else
                Log.d("senddd", config);
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
