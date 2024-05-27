package com.example.hero.employer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.employer.dto.WorkerInfoDTO;
import com.example.hero.etc.OnButtonClickListener;

import java.util.List;


public class EmployerStatusDetailAdapter extends RecyclerView.Adapter<EmployerStatusDetailAdapter.ViewHolder> {
    private List<WorkerInfoDTO> jobList;
    private OnButtonClickListener buttonClickListener;
    private Context context;

    public EmployerStatusDetailAdapter(List<WorkerInfoDTO> jobList, OnButtonClickListener listener) {
        this.jobList = jobList;
        this.buttonClickListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkerInfoDTO workerInfoDTO = jobList.get(position);
        holder.bind(workerInfoDTO, buttonClickListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView worker_name, worker_rating, defer_and_approve;
        ImageView worker_rating_image;
        Button resume_check;
        public ViewHolder(View itemView) {
            super(itemView);
            worker_name = itemView.findViewById(R.id.worker_name);
            defer_and_approve = itemView.findViewById(R.id.defer_and_approve);

            resume_check = itemView.findViewById(R.id.resume_check);

            worker_rating_image = itemView.findViewById(R.id.worker_rating_image);
            worker_rating = itemView.findViewById(R.id.worker_rating);
        }

        public void bind(final WorkerInfoDTO workerInfoDTO, final OnButtonClickListener listener) {
            worker_name.setText(workerInfoDTO.getUserName());
            defer_and_approve.setText(workerInfoDTO.getApproval());

            resume_check.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onButtonClick(workerInfoDTO.getJobId());
                }
            });

            int userLevel = workerInfoDTO.getUserLevel();

            switch (userLevel) {
                case 1:
                    worker_rating_image.setImageResource(R.drawable.level_1_img);
                    worker_rating.setText("씨앗");
                    break;
                case 2:
                    worker_rating_image.setImageResource(R.drawable.level_2_img);
                    worker_rating.setText("새싹");
                    break;
                case 3:
                    worker_rating_image.setImageResource(R.drawable.level_3_img);
                    worker_rating.setText("나무");
                    break;
                case 4:
                    worker_rating_image.setImageResource(R.drawable.level_4_img);
                    worker_rating.setText("열매");
                    break;
                default:
                    worker_rating_image.setImageResource(R.drawable.level_1_img);
                    worker_rating.setText("?");
                    break;
            }
        }
    }
}