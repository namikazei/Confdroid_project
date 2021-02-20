package com.example.confroid_project.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TokenDispenser extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // AF : refactor AppsIntentsManagerActivity en TokenDispenser ?
        /*
        if (intent != null && intent.getAction() != null && intent.getAction().equals("com.example.confroid_project.receivers.tokendispenser.login")) {
            Bundle bundle = intent.getBundleExtra("data");
            if (bundle != null) {
                String appname = bundle.getString("appname");
                Log.d("Received", appname);
                sendToken(appname);
            }
        }
         */
    }
}
