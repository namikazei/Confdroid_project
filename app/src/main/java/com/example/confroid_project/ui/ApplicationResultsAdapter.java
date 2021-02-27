package com.example.confroid_project.ui;

import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confroid_project.R;

import java.util.List;

public class ApplicationResultsAdapter extends RecyclerView.Adapter<ApplicationResultsAdapter.ViewHolder> {
    private MainActivity activity;
    private final List<Integer> l;
    public ApplicationResultsAdapter(List<Integer> l, MainActivity activity){
        this.l = l;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.update(l.get(i));
    }

    @Override
    public int getItemCount() {
        return l.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout content;
        private final TextView title;
        private final TextView state;
        private final TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.applicationRes);
            title = itemView.findViewById(R.id.applicationTitle);
            date = itemView.findViewById(R.id.applicationDate);
            state = itemView.findViewById(R.id.state);
        }

        public void update (int i){

        }
    }
}
