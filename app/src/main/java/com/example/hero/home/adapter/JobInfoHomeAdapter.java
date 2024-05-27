package com.example.hero.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.home.dto.JobInfoHomeDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JobInfoHomeAdapter extends RecyclerView.Adapter<JobInfoHomeAdapter.ViewHolder> {
    private List<JobInfoHomeDTO> mData;
    private OnItemClickListener mListener;

    public JobInfoHomeAdapter(List<JobInfoHomeDTO> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_job_list_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JobInfoHomeDTO jobInfo = mData.get(position);
        holder.bind(jobInfo, mListener); // bind 메소드 사용

//        holder.home_list_title.setText(jobInfo.getJobName());
//        holder.home_list_address_country.setText(jobInfo.getCountry());
//        holder.home_list_address_city.setText(jobInfo.getCity());
//        holder.home_list_item_crop_form.setText(jobInfo.getCropForm());
//        holder.home_list_item_crop_type.setText(jobInfo.getCropType());
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedStart = jobInfo.getStartWorkDate().format(formatter);
//        holder.home_list_work_period_start.setText(formattedStart);
//
//        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedStart2 = jobInfo.getEndWorkDate().format(formatter2);
//        holder.home_list_work_period_end.setText(formattedStart2);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView home_list_title, home_list_address_country, home_list_address_city;
        TextView home_list_item_crop_form, home_list_item_crop_type;
        TextView home_list_work_period_start, home_list_work_period_end;
        int currentJobId;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            home_list_title = itemView.findViewById(R.id.home_list_title);
            home_list_address_country = itemView.findViewById(R.id.home_list_address_country);
            home_list_address_city = itemView.findViewById(R.id.home_list_address_city);
            home_list_item_crop_form = itemView.findViewById(R.id.home_list_item_crop_form);
            home_list_item_crop_type = itemView.findViewById(R.id.home_list_item_crop_type);
            home_list_work_period_start = itemView.findViewById(R.id.home_list_work_period_start);
            home_list_work_period_end = itemView.findViewById(R.id.home_list_work_period_end);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(currentJobId);
                }
            });
        }

        public void bind(final JobInfoHomeDTO jobInfo, final OnItemClickListener listener) {
            currentJobId = jobInfo.getJobId();  // 현재 jobId 저장

            home_list_title.setText(jobInfo.getJobName());
            home_list_address_country.setText(jobInfo.getCountry());
            home_list_address_city.setText(jobInfo.getCity());
            home_list_item_crop_form.setText(jobInfo.getCropForm());
            home_list_item_crop_type.setText(jobInfo.getCropType());

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            String formattedStart = jobInfo.getStartWorkDate().format(formatter);
//            home_list_work_period_start.setText(formattedStart);

            home_list_work_period_start.setText(jobInfo.getStartWorkDate());


//            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            String formattedStart2 = jobInfo.getEndWorkDate().format(formatter2);
//            home_list_work_period_end.setText(formattedStart2);

            home_list_work_period_end.setText(jobInfo.getEndWorkDate());

        }
    }

}


