package com.example.confroid_project.confroidUtils;

import android.os.Bundle;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.String.valueOf;

public class Utils {

    public static Bundle convertToBundle(String configuration) {
        Bundle bundle = new Bundle();
        bundle.putString(ConfroidConstants.PRIMITIVE_VALUE, configuration);
        return bundle;
    }

    public static Bundle convertToBundle(byte configuration) {
        Bundle bundle = new Bundle();
        bundle.putByte(ConfroidConstants.PRIMITIVE_VALUE, configuration);
        return bundle;
    }

    public static Bundle convertToBundle(float configuration) {
        Bundle bundle = new Bundle();
        bundle.putFloat(ConfroidConstants.PRIMITIVE_VALUE, configuration);
        return bundle;
    }

    public static Bundle convertToBundle(int configuration) {
        Bundle bundle = new Bundle();
        bundle.putInt(ConfroidConstants.PRIMITIVE_VALUE, configuration);
        return bundle;
    }

    public static Bundle convertToBundle(List<String> configuration) {
        Bundle bundle = new Bundle();
        for (String item : configuration) {
            bundle.putString(valueOf(configuration.indexOf(item)), item);
        }
        return bundle;
    }

    public static Bundle convertToBundle(Map<String, String> configuration) {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : configuration.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        return bundle;
    }

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

}