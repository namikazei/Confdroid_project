package com.example.confroid_project.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TokenDispenser extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals("com.example.confroid_project.receivers.tokendispenser.login")) {
            Bundle bundle = intent.getBundleExtra("data");
            if (bundle != null) {
                String appname = bundle.getString("appname");
                Log.d("Received", appname);
                sendToken(appname);
            }
        }
    }

    private void sendToken(String appname) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("token", appname + "_generatednumer");
        intent.putExtra("token", bundle);
        setResult(Activity.RESULT_OK, "token", bundle);
        //finish();
    }
}
