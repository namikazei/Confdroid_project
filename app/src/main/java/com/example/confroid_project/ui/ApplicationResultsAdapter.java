package com.example.confroid_project.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;
import com.example.confroid_project.db.App;
import com.example.confroid_project.db.ConfigDb;

import java.util.ArrayList;

public class ApplicationResultsAdapter extends RecyclerView.Adapter<ApplicationResultsAdapter.ViewHolder> {
    public ArrayList<App> apps;
    private MainActivity activity;
    private ConfigDb db;

    public ApplicationResultsAdapter(MainActivity activity) {
        this.activity = activity;
        this.db = new ConfigDb(activity.getApplicationContext());
        this.apps = db.getApps();
    }

    public void setApps(String app) {
        ArrayList<App> sApp = new ArrayList<>();
        for (App a : db.getApps()) { if (a.getName().contains(app)) { sApp.add(a); }}
        if (app.equals("")) { sApp.clear(); sApp.addAll(db.getApps()); }
        apps.clear();
        apps.addAll(sApp);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.update(apps.get(i));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView state;
        public LinearLayout content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.applicationRes);
            title = itemView.findViewById(R.id.applicationTitle);
            state = itemView.findViewById(R.id.state);
        }

        public void update(App app) {
            String[] t = app.getName().split("\\.");
            String name = t[t.length - 1];
            title.setText(name);
            String count = String.valueOf(db.countConf(app.getName()));
            state.setText(count);
            content.setOnClickListener(a -> {
                Intent intent = new Intent(activity, ConfigurationActivity.class);
                intent.putExtra("app", app.getName());
                activity.startActivity(intent);
            });
        }
    }
}
