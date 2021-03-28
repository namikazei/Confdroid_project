package com.example.confroid_project.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.confroid_project.db.ConfigDb;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfigurationPusher extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConfigDb db = new ConfigDb(this.getApplicationContext());
        Bundle bundle = intent.getBundleExtra("bundle");
        String json_str = bundle.getString("json");
        try {
            JSONObject json_obj = new JSONObject(json_str);

            String name = json_obj.getString("name");
            String token = json_obj.getString("token");
            String tag = json_obj.getString("tag");
            String content = json_obj.getJSONObject("content").toString();

            db.addConfiguration(name,content);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
