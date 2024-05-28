package com.example.hero.worker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.employer.dto.EmployInfoDTO;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.worker.dto.ParticipateInfoDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class WorkerStatusAdapterB extends RecyclerView.Adapter<com.example.hero.worker.adapter.WorkerStatusAdapterB.ViewHolder> {
    private List<ParticipateInfoDTO> mData;
    private OnItemClickListener mListener;

    public WorkerStatusAdapterB(List<ParticipateInfoDTO> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    public void updateData(List<ParticipateInfoDTO> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public com.example.hero.worker.adapter.WorkerStatusAdapterB.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_status_all_item, parent, false);
        return new com.example.hero.worker.adapter.WorkerStatusAdapterB.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(com.example.hero.worker.adapter.WorkerStatusAdapterB.ViewHolder holder, int position) {
        ParticipateInfoDTO jobInfo = mData.get(position);
        holder.bind(jobInfo, mListener); // bind 메소드 사용
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView address_country, address_city, cropForm;
        TextView cropType, title, type, salary;
        TextView work_period_start, work_period_end, recruitment_period_start, recruitment_period_end;
        int currentJobId;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            address_country = itemView.findViewById(R.id.address_country);
            address_city = itemView.findViewById(R.id.address_city);
            cropForm = itemView.findViewById(R.id.cropForm);
            cropType = itemView.findViewById(R.id.cropType);
            title = itemView.findViewById(R.id.title);

            work_period_start = itemView.findViewById(R.id.work_period_start);
            work_period_end = itemView.findViewById(R.id.work_period_end);
            recruitment_period_start = itemView.findViewById(R.id.recruitment_period_start);
            recruitment_period_end = itemView.findViewById(R.id.recruitment_period_end);
            type = itemView.findViewById(R.id.type);
            salary = itemView.findViewById(R.id.salary);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(currentJobId);
                }
            });
        }

        public void bind(final ParticipateInfoDTO jobInfo, final OnItemClickListener listener) {
            currentJobId = jobInfo.getJobId();  // 현재 jobId 저장

            address_country.setText(jobInfo.getJobName());
            address_city.setText(jobInfo.getCountry());
            cropForm.setText(jobInfo.getCity());
            cropType.setText(jobInfo.getCropForm());
            title.setText(jobInfo.getCropType());

            work_period_start.setText(jobInfo.getStartWorkDate());
            work_period_end.setText( jobInfo.getEndWorkDate());
            recruitment_period_start.setText(jobInfo.getStartRecruitDate());
            recruitment_period_end.setText(jobInfo.getEndRecruitDate());

            //협의 or 일급
//            type.setText(participateInfoDTO.getCropType());

            salary.setText(String.valueOf(jobInfo.getPay()));


        }
    }

}
