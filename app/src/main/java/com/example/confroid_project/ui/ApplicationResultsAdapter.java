package com.example.confroid_project.ui;

import android.util.Log;
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
    private MainActivity activity;

    private ConfigDb db;
    private ArrayList<App> apps;

    public ApplicationResultsAdapter(MainActivity activity) {
        this.activity = activity;
        this.db = new ConfigDb(activity.getApplicationContext());
        this.apps = db.getApps();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.update(apps.get(i));
        Log.d("TAGapp", apps.get(i).getName());
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
            String t = app.getName();
            title.setText(t);
            String count = String.valueOf(db.countConf(app.getName()));
            state.setText(count);
        }
    }
}
