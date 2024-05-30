package com.example.hero.job.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnButtonClickListenerOwnerStatus;
import com.example.hero.etc.OnCommentClickListener;
import com.example.hero.job.dto.JobPostCommentResponseDTO;

import java.util.List;


public class JobCommentAdapter extends RecyclerView.Adapter {
    private List<JobPostCommentResponseDTO> commentsList;
    private OnCommentClickListener buttonClickListener;

    public JobCommentAdapter(List<JobPostCommentResponseDTO> commentsList) {
        this.commentsList = commentsList;
    }

    @Override
    public int getItemViewType(int position) {
        JobPostCommentResponseDTO comment = commentsList.get(position);
        return comment.isParent() ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {  // 부모 댓글
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_comment_list_item, parent, false);
            return new JobCommentAdapter.ParentCommentViewHolder(view, buttonClickListener);
        } else {  // 자식 댓글
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_comment_list_child_item, parent, false);
            return new JobCommentAdapter.ChildCommentViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JobPostCommentResponseDTO comment = commentsList.get(position);
        if (getItemViewType(position) == 0) {
            ((JobCommentAdapter.ParentCommentViewHolder) holder).bind(comment);
        } else {
            ((JobCommentAdapter.ChildCommentViewHolder) holder).bind(comment);
        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public static class ParentCommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent, textViewAuthor, textViewDate;
        RecyclerView childRecyclerView;
        int commentId;
        Button job_comment_childBtn, job_comment_editBtn, job_comment_deleteBtn;

        public ParentCommentViewHolder(View itemView, OnCommentClickListener buttonClickListener) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.job_comment_content);
            textViewAuthor = itemView.findViewById(R.id.job_comment_userName);
            childRecyclerView = itemView.findViewById(R.id.job_commentChild_recyclerView);

            job_comment_childBtn = itemView.findViewById(R.id.job_comment_childBtn);
            job_comment_editBtn = itemView.findViewById(R.id.job_comment_editBtn);
            job_comment_deleteBtn = itemView.findViewById(R.id.job_comment_deleteBtn);

            job_comment_childBtn.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.CHILD);
                }
            });

            job_comment_editBtn.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.EDIT);
                }
            });

            job_comment_deleteBtn.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.DELETE);
                }
            });

        }

        public void bind(JobPostCommentResponseDTO dto) {
            commentId = dto.getCommentId();  // 현재 Id 저장

            textViewContent.setText(dto.getCommentContent());
            textViewAuthor.setText(dto.getUserName());

            //자식댓글
            if (dto.getChildCommentList() != null && !dto.getChildCommentList().isEmpty()) {
                JobCommentAdapter childAdapter = new JobCommentAdapter(dto.getChildCommentList());
                childRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                childRecyclerView.setAdapter(childAdapter);
            } else {
                childRecyclerView.setVisibility(View.GONE);  // 자식 댓글이 없을 경우 RecyclerView 숨김 처리
            }

        }
    }

    public static class ChildCommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent, textViewAuthor, textViewDate;
        int commentId;

        public ChildCommentViewHolder(View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.job_comment_content);
            textViewAuthor = itemView.findViewById(R.id.job_comment_userName);
        }

        public void bind(JobPostCommentResponseDTO dto) {
            commentId = dto.getCommentId();  // 현재 Id 저장

            textViewContent.setText(dto.getCommentContent());
            textViewAuthor.setText(dto.getUserName());
        }
    }



}

