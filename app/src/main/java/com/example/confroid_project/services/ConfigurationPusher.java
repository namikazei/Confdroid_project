package com.example.confroid_project.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.confroid_project.db.App;
import com.example.confroid_project.db.ConfigDb;
import com.example.confroid_project.receivers.RestartServices;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfigurationPusher extends Service {

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("RestartService");
        broadcastIntent.putExtra("type","pusher");
        broadcastIntent.setClass(this, RestartServices.class);
        this.sendBroadcast(broadcastIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConfigDb db = new ConfigDb(this.getApplicationContext());
        if (intent.getBundleExtra("CONFIG") != null) {
            Bundle bundle = intent.getBundleExtra("CONFIG");
            String app_token = bundle.getString("TOKEN");
            String app_name = bundle.getString("APP");

            if (db.getAppToken(app_name).equals(app_token)) {
                String json_str = bundle.getString("JSON");
                String conf_name = bundle.getString("CONF_NAME");
                db.addConfiguration(app_name, conf_name, json_str);
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
