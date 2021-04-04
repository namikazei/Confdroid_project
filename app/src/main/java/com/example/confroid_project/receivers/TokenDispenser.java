package com.example.confroid_project.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.confroid_project.confroidUtils.Utils;
import com.example.confroid_project.db.ConfigDb;

public class TokenDispenser extends BroadcastReceiver {

    ConfigDb db;

    @Override
    public void onReceive(Context context, Intent intent) {
        String token;
        db = new ConfigDb(context);
        if (intent.getAction().equals("GET_TOKEN")) {
            String receiver = intent.getStringExtra("receiver");
            String name = intent.getStringExtra("name");
            token = db.getAppToken(name);
            if (token == null || token.equals("")) {
                token = Utils.getToken(name);
                //register to data base
                db.addApplication(name, token);
            }
            Intent response = new Intent("TOKEN_PULL");
            response.setClassName(name, receiver);
            response.putExtra("token", token);
            context.startService(response);

        }
    }
}
