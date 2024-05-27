package com.example.hero.worker.adapter;

import static java.lang.String.valueOf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.worker.dto.ParticipateInfoDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class WorkerStatusAdapterA extends RecyclerView.Adapter<com.example.hero.worker.adapter.WorkerStatusAdapterA.ViewHolder> {
    private List<ParticipateInfoDTO> mData;
    private OnItemClickListener mListener;
    private OnButtonClickListener buttonClickListener;

    public WorkerStatusAdapterA(List<ParticipateInfoDTO> data, OnItemClickListener listener, OnButtonClickListener buttonClickListener) {
        this.mData = data;
        this.mListener = listener;
        this.buttonClickListener = buttonClickListener;
    }

    public void updateData(List<ParticipateInfoDTO> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public com.example.hero.worker.adapter.WorkerStatusAdapterA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_status_apply_item, parent, false);
        return new com.example.hero.worker.adapter.WorkerStatusAdapterA.ViewHolder(view, mListener, buttonClickListener);
    }

    @Override
    public void onBindViewHolder(com.example.hero.worker.adapter.WorkerStatusAdapterA.ViewHolder holder, int position) {
        ParticipateInfoDTO participateInfoDTO = mData.get(position);
        holder.bind(participateInfoDTO, mListener, buttonClickListener); // bind 메소드 사용
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
        Button worker_status_cancel;

        public ViewHolder(View itemView, OnItemClickListener listener, OnButtonClickListener buttonClickListener) {
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

            worker_status_cancel = itemView.findViewById(R.id.worker_status_cancel);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(currentJobId);
                }
            });

//            itemView.setOnClickListener(v -> {
//                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
//                    listener.onItemClick(currentJobId);
//                }
//            });

            worker_status_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.onButtonClick(currentJobId);
                }
            });

//            worker_status_cancel.setOnClickListener(v -> {
//                int jobId = mData.get(getAdapterPosition()).getJobId();
//                buttonClickListener.onButtonClick(jobId);
//            });

//            itemView.setOnClickListener(v -> {
//                int position = getAdapterPosition();
//                if (listener != null && position != RecyclerView.NO_POSITION) {
//                    listener.onItemClick(position);
//                }
//            });


//            worker_status_cancel.setOnClickListener(v -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    buttonClickListener.onButtonClick(currentJobId);
//                }
//            });


        }

        public void bind(final ParticipateInfoDTO participateInfoDTO, final OnItemClickListener listener, final OnButtonClickListener buttonClickListener) {
            currentJobId = participateInfoDTO.getJobId();  // 현재 jobId 저장

            address_country.setText(participateInfoDTO.getJobName());
            address_city.setText(participateInfoDTO.getCountry());
            cropForm.setText(participateInfoDTO.getCity());
            cropType.setText(participateInfoDTO.getCropForm());
            title.setText(participateInfoDTO.getCropType());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart = participateInfoDTO.getStartWorkDate().format(formatter);
            work_period_start.setText(formattedStart);

            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart2 = participateInfoDTO.getEndWorkDate().format(formatter2);
            work_period_end.setText(formattedStart2);

            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart3 = participateInfoDTO.getStartRecruitDate().format(formatter3);
            recruitment_period_start.setText(formattedStart3);

            DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart4 = participateInfoDTO.getEndRecruitDate().format(formatter4);
            recruitment_period_end.setText(formattedStart4);

            //협의 or 일급
//            type.setText(participateInfoDTO.getCropType());

            salary.setText(String.valueOf(participateInfoDTO.getPay()));



        }
    }

}


