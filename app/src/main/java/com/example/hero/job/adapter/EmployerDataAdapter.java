package com.example.hero.job.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.job.dto.ReviewContentInfoDTO;

import java.util.List;


public class EmployerDataAdapter extends RecyclerView.Adapter<com.example.hero.job.adapter.EmployerDataAdapter.ViewHolder> {
    private List<ReviewContentInfoDTO> mData;

    public EmployerDataAdapter(List<ReviewContentInfoDTO> data) {
        mData = data;
    }

    @Override
    public com.example.hero.job.adapter.EmployerDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_comment_item, parent, false);
        return new com.example.hero.job.adapter.EmployerDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.hero.job.adapter.EmployerDataAdapter.ViewHolder holder, int position) {
        ReviewContentInfoDTO dto = mData.get(position);
        holder.bind(dto);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView employer_comment_writer;
        TextView employer_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            employer_comment_writer = itemView.findViewById(R.id.employer_comment_writer);
            employer_comment = itemView.findViewById(R.id.employer_comment);

        }

        public void bind(final ReviewContentInfoDTO dto) {
//            currentJobId = participateInfo.getJobId();  // 현재 jobId 저장

            employer_comment_writer.setText(dto.getUserId());
            employer_comment.setText(dto.getReviewContent());

        }


    }
}