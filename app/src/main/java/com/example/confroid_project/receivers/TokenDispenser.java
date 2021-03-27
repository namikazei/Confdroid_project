package com.example.confroid_project.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Random;

public class TokenDispenser extends BroadcastReceiver {
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
        String receiver = intent.getStringExtra("receiver");
        String name = intent.getStringExtra("name");
        //calculat token and send the intent
        Intent response = new Intent();
        response.setClassName(name, receiver);
        response.putExtra("token", getToken(name));
        context.startService(response);
    }
}
