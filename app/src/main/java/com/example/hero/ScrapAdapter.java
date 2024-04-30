package com.example.hero;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScrapAdapter extends RecyclerView.Adapter<ScrapAdapter.ViewHolder> {

    private ArrayList<ScrapData> scrapList;

    public ScrapAdapter(ArrayList<ScrapData> scrapList) {
        this.scrapList = scrapList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScrapData scrapData = scrapList.get(position);
    }

    @Override
    public int getItemCount() {
        return scrapList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
