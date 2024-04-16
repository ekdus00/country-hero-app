package com.example.hero.Listener;

import android.view.View;

import com.example.hero.JobListAdapter;

public interface OnJobItemClickListener {
    public void onItemClick(JobListAdapter.ViewHolder holder, View view, int position);
}
