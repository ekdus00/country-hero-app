package com.example.hero.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.home.dto.EmployInfoHomeDTO;
import com.example.hero.home.dto.ParticipateInfoHomeDTO;

import java.util.List;


public class EmployInfoHomeAdapter extends RecyclerView.Adapter<EmployInfoHomeAdapter.ViewHolder> {
    private List<EmployInfoHomeDTO> mData;

    public EmployInfoHomeAdapter(List<EmployInfoHomeDTO> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recruiter_status_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmployInfoHomeDTO employInfoHomeDTO = mData.get(position);
        holder.recruiter_status_title.setText(employInfoHomeDTO.getJobName());

        Long longValue = employInfoHomeDTO.getParticipateCount();
        holder.recruiter_status_applicant_sum.setText(String.valueOf(longValue));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recruiter_status_title;
        TextView recruiter_status_applicant_sum;

        public ViewHolder(View itemView) {
            super(itemView);
            recruiter_status_title = itemView.findViewById(R.id.recruiter_status_title);
            recruiter_status_applicant_sum = itemView.findViewById(R.id.recruiter_status_applicant_sum);

        }
    }
}

