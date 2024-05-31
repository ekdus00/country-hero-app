package com.example.hero.job.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.job.dto.JobPostCommentResponseDTO;
import com.example.hero.job.dto.ReviewContentInfoDTO;

import java.util.List;


public class JobChildCommentAdapter extends RecyclerView.Adapter<com.example.hero.job.adapter.JobChildCommentAdapter.ViewHolder> {
    private List<JobPostCommentResponseDTO> mData;

    public JobChildCommentAdapter(List<JobPostCommentResponseDTO> data) {
        mData = data;
    }

    @Override
    public com.example.hero.job.adapter.JobChildCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_comment_list_child_item, parent, false);
        return new com.example.hero.job.adapter.JobChildCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.hero.job.adapter.JobChildCommentAdapter.ViewHolder holder, int position) {
        JobPostCommentResponseDTO dto = mData.get(position);
        holder.bind(dto);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent, textViewAuthor, textViewDate;
        int commentId;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.job_comment_content);
            textViewAuthor = itemView.findViewById(R.id.job_comment_userName);
        }

        public void bind(final JobPostCommentResponseDTO dto) {
            commentId = dto.getCommentId();  // 현재 Id 저장

            textViewContent.setText(dto.getCommentContent());
            textViewAuthor.setText(dto.getUserName());

        }


    }
}