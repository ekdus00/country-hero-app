package com.example.hero.review.adatper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnButtonClickListenerReviewStatus;
import com.example.hero.review.dto.OwnerReviewInfoDTO;
import com.example.hero.review.dto.WorkerReviewInfoDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ReviewEmployerListAdapter extends RecyclerView.Adapter<ReviewEmployerListAdapter.ViewHolder> {
    private List<OwnerReviewInfoDTO> mData;
    private OnButtonClickListenerReviewStatus mListener;

    public ReviewEmployerListAdapter(List<OwnerReviewInfoDTO> data, OnButtonClickListenerReviewStatus listener) {
        mData = data;
        mListener = listener;
    }

    @Override
    public ReviewEmployerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_employer_list_item, parent, false);
        return new ReviewEmployerListAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ReviewEmployerListAdapter.ViewHolder holder, int position) {
        OwnerReviewInfoDTO jobInfo = mData.get(position);
        holder.bind(jobInfo, mListener); // bind 메소드 사용
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView address_country, address_city, title;
        TextView work_period_start, work_period_end, review_employer_list_name;
        int currentJobId;
        Button review_post_btn;
        String targetUserId;

        public ViewHolder(View itemView, OnButtonClickListenerReviewStatus listener) {
            super(itemView);
            address_country = itemView.findViewById(R.id.address_country);
            address_city = itemView.findViewById(R.id.address_city);

            title = itemView.findViewById(R.id.title);

            work_period_start = itemView.findViewById(R.id.work_period_start);
            work_period_end = itemView.findViewById(R.id.work_period_end);

            review_employer_list_name = itemView.findViewById(R.id.review_employer_list_name);

            review_post_btn = itemView.findViewById(R.id.review_post_btn);

            review_post_btn.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.OnButtonClickListenerReviewStatus(currentJobId, targetUserId);
                }
            });
        }

        public void bind(final OwnerReviewInfoDTO jobInfo, final OnButtonClickListenerReviewStatus listener) {
            currentJobId = jobInfo.getJobId();  // 현재 jobId 저장
            targetUserId = jobInfo.getTargetUserId();

            address_country.setText(jobInfo.getJobName());
            address_city.setText(jobInfo.getCountry());

            title.setText(jobInfo.getCropType());

            review_employer_list_name.setText(jobInfo.getWorkerName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart = jobInfo.getStartWorkDate().format(formatter);
            work_period_start.setText(formattedStart);

            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStart2 = jobInfo.getEndWorkDate().format(formatter2);
            work_period_end.setText(formattedStart2);

        }
    }

}
