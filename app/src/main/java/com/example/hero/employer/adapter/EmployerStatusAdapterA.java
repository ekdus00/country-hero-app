package com.example.hero.employer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;

import com.example.hero.employer.dto.EmployInfoDTO;
import com.example.hero.etc.OnButtonClickListenerOwnerStatus;
import com.example.hero.etc.OnItemClickListener;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployerStatusAdapterA extends RecyclerView.Adapter<com.example.hero.employer.adapter.EmployerStatusAdapterA.ViewHolder> {
    private List<EmployInfoDTO> mData;
    private OnItemClickListener mListener;
    private OnButtonClickListenerOwnerStatus buttonClickListener;

    public EmployerStatusAdapterA(List<EmployInfoDTO> data, OnItemClickListener listener, OnButtonClickListenerOwnerStatus buttonClickListener) {
        mData = data;
        mListener = listener;
        this.buttonClickListener = buttonClickListener;
    }

    public void updateData(List<EmployInfoDTO> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public com.example.hero.employer.adapter.EmployerStatusAdapterA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_status_progress_item, parent, false);
        return new com.example.hero.employer.adapter.EmployerStatusAdapterA.ViewHolder(view, mListener, buttonClickListener);
    }

    @Override
    public void onBindViewHolder(com.example.hero.employer.adapter.EmployerStatusAdapterA.ViewHolder holder, int position) {
        EmployInfoDTO jobInfo = mData.get(position);
        holder.bind(jobInfo, mListener, buttonClickListener); // bind 메소드 사용
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView address_country, address_city, cropForm;
        TextView cropType, title, participateCount;
        TextView work_period_start, work_period_end, recruitment_period_start, recruitment_period_end;
        int currentJobId;
        Button employer_status_modify, employer_status_deadline;

        public ViewHolder(View itemView, OnItemClickListener listener, OnButtonClickListenerOwnerStatus buttonClickListener) {
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
            participateCount = itemView.findViewById(R.id.participateCount);

            employer_status_modify = itemView.findViewById(R.id.employer_status_modify);
            employer_status_deadline = itemView.findViewById(R.id.employer_status_deadline);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(currentJobId);
                }
            });

            employer_status_modify.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.onButtonClickWorkerStatus(currentJobId, OnButtonClickListenerOwnerStatus.ButtonType.MODIFY);
                }
            });

            employer_status_deadline.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.onButtonClickWorkerStatus(currentJobId, OnButtonClickListenerOwnerStatus.ButtonType.DEADLINE);
                }
            });


        }

        public void bind(final EmployInfoDTO jobInfo, final OnItemClickListener listener, final OnButtonClickListenerOwnerStatus buttonClickListener) {
            currentJobId = jobInfo.getJobId();  // 현재 jobId 저장

            address_country.setText(jobInfo.getJobName());
            address_city.setText(jobInfo.getCountry());
            cropForm.setText(jobInfo.getCity());
            cropType.setText(jobInfo.getCropForm());
            title.setText(jobInfo.getCropType());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart = jobInfo.getStartWorkDate().format(formatter);
            work_period_start.setText(formattedStart);

            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart2 = jobInfo.getEndWorkDate().format(formatter2);
            work_period_end.setText(formattedStart2);

            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart3 = jobInfo.getStartRecruitDate().format(formatter3);
            recruitment_period_start.setText(formattedStart3);

            DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart4 = jobInfo.getEndRecruitDate().format(formatter4);
            recruitment_period_end.setText(formattedStart4);

            participateCount.setText(String.valueOf(jobInfo.getParticipateCount()));

        }
    }

}

