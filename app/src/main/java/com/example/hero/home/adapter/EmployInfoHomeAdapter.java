package com.example.hero.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.home.dto.EmployInfoHomeDTO;
import com.example.hero.home.dto.JobInfoHomeDTO;

import java.util.List;


public class EmployInfoHomeAdapter extends RecyclerView.Adapter<EmployInfoHomeAdapter.ViewHolder> {
    private List<EmployInfoHomeDTO> mData;
    private OnItemClickListener mListener;

    public EmployInfoHomeAdapter(List<EmployInfoHomeDTO> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_owner_status_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmployInfoHomeDTO employInfoHomeDTO = mData.get(position);
        holder.bind(employInfoHomeDTO, mListener); // bind 메소드 사용
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recruiter_status_title;
        TextView recruiter_status_applicant_sum;
        int currentJobId;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            recruiter_status_title = itemView.findViewById(R.id.recruiter_status_title);
            recruiter_status_applicant_sum = itemView.findViewById(R.id.recruiter_status_applicant_sum);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(currentJobId);
                }
            });

        }

        public void bind(final EmployInfoHomeDTO dto, final OnItemClickListener listener) {
            currentJobId = dto.getJobId();  // 현재 jobId 저장

            recruiter_status_title.setText(dto.getJobName());

            Long longValue = dto.getParticipateCount();
            recruiter_status_applicant_sum.setText(String.valueOf(longValue));

        }
    }
}

