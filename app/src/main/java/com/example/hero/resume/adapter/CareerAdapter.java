package com.example.hero.resume.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;

import java.util.List;

public class CareerAdapter extends RecyclerView.Adapter<CareerAdapter.ViewHolder> {

    private List<String> careerList;

    public CareerAdapter(List<String> careerList) {
        this.careerList = careerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resume_career_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String career = careerList.get(position);
        holder.career_textView.setText(career);
    }

    @Override
    public int getItemCount() {
        return careerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView career_textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            career_textView = itemView.findViewById(R.id.career_textView);
        }
    }
}