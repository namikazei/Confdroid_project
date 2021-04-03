package com.example.confroid_project.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;
import com.example.confroid_project.db.ConfigurationVersions;
import com.example.confroid_project.storage.StorageUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.confroid_project.storage.StorageUtils.RESTORE_REQUEST_CODE;
import static com.example.confroid_project.storage.StorageUtils.SAVE_REQUEST_CODE;

public class ConfigurationActivity extends AppCompatActivity {
    TextView tv_info;
    Button bt_save;
    Button bt_restore;
    ArrayList<ConfigurationVersions> configuratonsList;
    private RecyclerView recyclerView;
    private ConfigurationResultsAdapter confAdapter;
    private TextView ifEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configs_layout);

        confAdapter = new ConfigurationResultsAdapter(this);
        ifEmpty = findViewById(R.id.vide);
        recyclerView = findViewById(R.id.recyclerView);
        tv_info = findViewById(R.id.tv_info);
        bt_save = findViewById(R.id.bt_save);
        bt_restore = findViewById(R.id.bt_restore);
        configuratonsList = confAdapter.getConfigurationsList();

        bt_save.setOnClickListener(view -> {
            StorageUtils.createBackupFile(this);
        });

        bt_restore.setOnClickListener(view -> {
            StorageUtils.openRestoreSourceFile(this);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        confAdapter = new ConfigurationResultsAdapter(this);
        recyclerView.setAdapter(confAdapter);
        recyclerView.setLayoutManager(createLM());

        if (confAdapter.getItemCount() != 0) {
            ifEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private RecyclerView.LayoutManager createLM() {
        int span = 1;
        int orientation = RecyclerView.VERTICAL;
        return new GridLayoutManager(this, span, orientation, false);
    }

    private void performRestorationAction(String configurations) {
        if (!configurations.equals("")) {
            ArrayList<ConfigurationVersions> restoredConfigurations = new ArrayList<>();
            String[] configurationsTab = configurations.split("\\|");
            for (String configuration : configurationsTab) {
                ConfigurationVersions config = new Gson().fromJson(configuration, ConfigurationVersions.class);
                restoredConfigurations.add(config);
            }
            confAdapter.setConfigurationsList(restoredConfigurations);
            configuratonsList = restoredConfigurations;
            // on mets a jour la bdd ici
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);

        Uri currentUri = null;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SAVE_REQUEST_CODE) {
                if (resultData != null) {
                    currentUri = resultData.getData();
                    StorageUtils.writeConfigurationsToFile(this, configuratonsList, currentUri);
                    tv_info.setText("Configurations sauvegardées !");
                }
            } else if (requestCode == RESTORE_REQUEST_CODE) {
                if (resultData != null) {
                    currentUri = resultData.getData();

                    try {
                        String restoredConfigurations = StorageUtils.readConfigurationsFromFile(this, currentUri);
                        performRestorationAction(restoredConfigurations);
                        confAdapter.notifyDataSetChanged();
                        tv_info.setText("Configurations restaurées !");
                    } catch (IOException e) {
                        // Handle error here
                    }
                }
            }
        }
    }

}
