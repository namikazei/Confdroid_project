package com.example.confroid_project.ui;

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
        return ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.applicationRes, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationResultsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout content;
        private final TextView title;
        private final TextView desc;
        private final TextView state;
        private final TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.search_entry);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            desc = itemView.findViewById(R.id.desc);
            state = itemView.findViewById(R.id.state);
        }
    }
}
