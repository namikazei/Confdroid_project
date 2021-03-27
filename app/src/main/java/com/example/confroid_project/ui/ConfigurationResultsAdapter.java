package com.example.confroid_project.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;
import com.example.confroid_project.db.ConfigurationVersions;
import com.example.confroid_project.db.ConfigDb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ConfigurationResultsAdapter extends RecyclerView.Adapter<ConfigurationResultsAdapter.ViewHolder> {
    private Activity activity;
    private Context context;
    private String idApp;
    private ConfigDb db;
    private ArrayList<ConfigurationVersions> Confs;
    private AlertDialog.Builder builder;

    public ConfigurationResultsAdapter(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.db = new ConfigDb(activity.getApplicationContext());

        Intent intent = activity.getIntent();
        String app = intent.getStringExtra("app");
        this.idApp = app;
        this.Confs = db.getAllAppConfiguration(app);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.config, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.update(Confs.get(i));
    }

    @Override
    public int getItemCount() {
        return Confs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout content;
        private final TextView configurationTitle;
        private final TextView version;
        private final TextView date;
        private Button buttonEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.config);
            configurationTitle = itemView.findViewById(R.id.configurationTitle);
            version = itemView.findViewById(R.id.version);
            date = itemView.findViewById(R.id.date);
            buttonEdit = itemView.findViewById(R.id.editConf);
        }

        public void update (ConfigurationVersions conf) {
            String id = String.valueOf(conf.getId());
            configurationTitle.setText(id);
            String v = String.valueOf(conf.getVersion());
            version.setText(v);
            String d = conf.getDate();
            date.setText(d);
            buttonEdit.setOnClickListener(view -> editTaskDialog(conf));
        }
    }

    private void editTaskDialog(ConfigurationVersions conf) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("Modifier configuration");
        builder.setCancelable(false);
        LayoutInflater customLayout = LayoutInflater.from(activity);
        View customLayoutS = customLayout.inflate(R.layout.edit_config, null);
        builder.setView(customLayoutS);

        final EditText IdConf = customLayoutS.findViewById(R.id.editIdConf);
        final EditText versionConf = customLayoutS.findViewById(R.id.editVersionConf);
        final EditText valueConf = customLayoutS.findViewById(R.id.editValueConf);
        final EditText dateConf = customLayoutS.findViewById(R.id.editDateConf);

        if(conf != null){
            IdConf.setText(String.valueOf(conf.getId()));
            versionConf.setText(String.valueOf(conf.getVersion()));
            dateConf.setText(String.valueOf(conf.getDate()));
            valueConf.setText(String.valueOf(conf.getValue()));
        }

        builder.setPositiveButton("VALIDER", (dialog, id) -> {
            Toast.makeText(context, "Modification validée.", Toast.LENGTH_SHORT).show();
            db.updateConf(conf.getId(),String.valueOf(valueConf.getText()));
            Confs.get(Confs.indexOf(conf)).setValue(String.valueOf(valueConf.getText()));
        });
        builder.setNegativeButton("ANNULER", (dialog, id) -> {
            Toast.makeText(context, "Modification annulée !", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
