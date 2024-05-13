package com.example.hero.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.dto.JobPostCommentResponseDTO;

import java.util.ArrayList;
import java.util.List;


public class JobCommentAdapter extends RecyclerView.Adapter<JobCommentAdapter.CommentViewHolder> {
    private List<JobPostCommentResponseDTO> commentsList;

    public JobCommentAdapter(List<JobPostCommentResponseDTO> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_comment_list_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        JobPostCommentResponseDTO comment = commentsList.get(position);
        holder.textViewContent.setText(comment.getCommentContent());
        holder.textViewAuthor.setText(comment.getUserName());
//        holder.textViewDate.setText(comment.getUpdatedCommentDatetime().toString());

        // 자식 댓글 리스트 처리
        if (comment.getChildCommentList() != null && !comment.getChildCommentList().isEmpty()) {
            JobCommentAdapter childAdapter = new JobCommentAdapter(comment.getChildCommentList());
            holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.childRecyclerView.setAdapter(childAdapter);
        } else {
            holder.childRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent, textViewAuthor, textViewDate;
        RecyclerView childRecyclerView;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.job_comment_content);
            textViewAuthor = itemView.findViewById(R.id.job_comment_userName);
//            textViewDate = itemView.findViewById(R.id.text_view_date);
            childRecyclerView = itemView.findViewById(R.id.home_job_commentChild_recyclerView);
        }
    }
}

