package com.example.confroid_project.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;
import com.example.confroid_project.db.ConfigurationVersions;
import com.example.confroid_project.db.ConfigDb;

import java.util.ArrayList;

public class ConfigurationResultsAdapter extends RecyclerView.Adapter<ConfigurationResultsAdapter.ViewHolder> {
    private Activity activity;

    private ConfigDb db;
    private ArrayList<ConfigurationVersions> Confs;

    public ConfigurationResultsAdapter(Activity activity){
        this.activity = activity;
        this.db = new ConfigDb(activity.getApplicationContext());

        Intent intent = activity.getIntent();
        String app = intent.getStringExtra("app");

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.config);
            configurationTitle = itemView.findViewById(R.id.configurationTitle);
            version = itemView.findViewById(R.id.version);
            date = itemView.findViewById(R.id.date);
        }

        public void update (ConfigurationVersions Conf){
            String id = String.valueOf(Conf.getId());
            configurationTitle.setText(id);
            String v = String.valueOf(Conf.getVersion());
            version.setText(v);
            String d = Conf.getDate();
            date.setText(d);
        }
    }
}
