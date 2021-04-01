package com.example.confroid_project.receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.confroid_project.db.ConfigDb;

import java.util.Random;

public class TokenDispenser extends BroadcastReceiver {
    ConfigDb db;

    public static String getToken(String appName) {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 10;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);
        }
        return appName + "_" + sb.toString();
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        String token = "";
        db = new ConfigDb(context);
        if (intent.getAction().equals("GET_TOKEN")) {
            String receiver = intent.getStringExtra("receiver");
            String name = intent.getStringExtra("name");
            token = getToken(name);
            //register to data base
            db.addApplication(name, token);
            //calculat token and send the intent
            Intent response = new Intent("TOKEN_PULL");
            response.setClassName(name, receiver);
            response.putExtra("token", token);
            ComponentName c = context.startService(response);

            if (c == null)
                Log.e("faillllll", "failed to start with " + response);
            else
                Log.d("senddd", token);
        }
    }
}
