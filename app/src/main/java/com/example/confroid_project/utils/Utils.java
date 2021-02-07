package com.example.confroid_project.utils;

import android.os.Bundle;

import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class Utils {

    public static Bundle convertToBundle(String configuration) {
        Bundle bundle = new Bundle();
        bundle.putString(ConfroidConstants.PRIMITIVE_VALUE, configuration);
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
}