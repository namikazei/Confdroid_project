package com.example.confroid_project.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.confroid_project.services.ConfigurationPuller;
import com.example.confroid_project.services.ConfigurationPusher;

public class RestartServices extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("type");

        if (type.equals("puller"))
            context.startForegroundService(new Intent(context, ConfigurationPuller.class));

        if (type.equals("pusher"))
            context.startForegroundService(new Intent(context, ConfigurationPusher.class));
    }
}
