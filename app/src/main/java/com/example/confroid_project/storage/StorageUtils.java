package com.example.confroid_project.storage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import com.example.confroid_project.db.ConfigurationVersions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StorageUtils {

    public static final int SAVE_REQUEST_CODE = 100;
    public static final int RESTORE_REQUEST_CODE = 101;

    public static void createBackupFile(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt");
        activity.startActivityForResult(intent, SAVE_REQUEST_CODE);
    }

    public static void openBackupFile(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        activity.startActivityForResult(intent, RESTORE_REQUEST_CODE);
    }

    public static String readConfigurationsFromFile(Activity activity, Uri uri) throws IOException {
        InputStream inputStream = activity.getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentline;
        while ((currentline = reader.readLine()) != null) {
            stringBuilder.append(currentline);
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    public static void writeConfigurationsToFile(Activity activity, List<ConfigurationVersions> configurations, Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = activity.getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            fileOutputStream.write(getConfigurationsToSave(configurations).getBytes());
            fileOutputStream.close();
            parcelFileDescriptor.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfigurationsToSave(List<ConfigurationVersions> configurations) {
        String allConfigurations = "";
        List<String> allConfigurationsList = new ArrayList();

        for (ConfigurationVersions configuration : configurations) {
            String config = new Gson().toJson(configuration);
            allConfigurationsList.add(config);
        }

        allConfigurations = TextUtils.join("|", allConfigurationsList);
        return allConfigurations;
    }

}
