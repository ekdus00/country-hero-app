package com.example.hero.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;

import java.util.ArrayList;

public class EmployerStatusAdapterA extends RecyclerView.Adapter<EmployerStatusAdapterA.ViewHolder>{

    private ArrayList<String> localDataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
        }
        public TextView getTextView() {
            return textView;
        }
    }
    public EmployerStatusAdapterA (ArrayList<String> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public EmployerStatusAdapterA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employer_status_progress_item, parent, false);
        EmployerStatusAdapterA.ViewHolder viewHolder = new EmployerStatusAdapterA.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerStatusAdapterA.ViewHolder holder, int position) {
        String text = localDataSet.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
