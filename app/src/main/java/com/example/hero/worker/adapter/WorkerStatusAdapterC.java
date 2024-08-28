package com.example.hero.worker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.worker.dto.CareerDTO;
import com.example.hero.worker.dto.ParticipateInfoDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;


public class WorkerStatusAdapterC extends RecyclerView.Adapter<com.example.hero.worker.adapter.WorkerStatusAdapterC.ViewHolder> {
    private List<CareerDTO> mData;
    private OnItemClickListener mListener;

    public WorkerStatusAdapterC(List<CareerDTO> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    public void updateData(List<CareerDTO> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public com.example.hero.worker.adapter.WorkerStatusAdapterC.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_status_detail_item, parent, false);
        return new com.example.hero.worker.adapter.WorkerStatusAdapterC.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(com.example.hero.worker.adapter.WorkerStatusAdapterC.ViewHolder holder, int position) {
        CareerDTO jobInfo = mData.get(position);
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

            salary = itemView.findViewById(R.id.salary);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(currentJobId);
                }
            });
        }

        public void bind(final CareerDTO jobInfo, final OnItemClickListener listener) {
            currentJobId = jobInfo.getJobId();  // 현재 jobId 저장

            address_country.setText(jobInfo.getCountry());
            address_city.setText(jobInfo.getCity());
            cropForm.setText(jobInfo.getCropForm());
            cropType.setText(jobInfo.getCropType());
            title.setText(jobInfo.getJobName());

            work_period_start.setText(jobInfo.getStartWorkDate());
            work_period_end.setText(jobInfo.getEndWorkDate());
            recruitment_period_start.setText(jobInfo.getStartRecruitDate());
            recruitment_period_end.setText(jobInfo.getEndRecruitDate());

            salary.setText(String.valueOf(jobInfo.getPay()));

        }
    }

}