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


public class JobCommentAdapter extends RecyclerView.Adapter<JobCommentAdapter.ViewHolder> {
    private List<JobPostCommentResponseDTO> commentsList;
    private OnCommentClickListener buttonClickListener;

    public JobCommentAdapter(List<JobPostCommentResponseDTO> commentsList, OnCommentClickListener buttonClickListener) {
        this.commentsList = commentsList;
        this.buttonClickListener = buttonClickListener;

    }

    @Override
    public int getItemViewType(int position) {
        JobPostCommentResponseDTO comment = commentsList.get(position);
        return comment.isParent() ? 0 : 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_comment_list_item, parent, false);
        return new JobCommentAdapter.ViewHolder(view, buttonClickListener, this);

//        if (viewType == 0) {  // 부모 댓글
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_comment_list_item, parent, false);
//            return new JobCommentAdapter.ParentCommentViewHolder(view, buttonClickListener);
//        } else {  // 자식 댓글
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_comment_list_child_item, parent, false);
//            return new JobCommentAdapter.ChildCommentViewHolder(view);
//        }
    }


    @Override
    public void onBindViewHolder(@NonNull JobCommentAdapter.ViewHolder holder, int position) {
        JobPostCommentResponseDTO dto = commentsList.get(position);
        holder.bind(dto, buttonClickListener);

//        if (getItemViewType(position) == 0) {
//            ((JobCommentAdapter.ParentCommentViewHolder) holder).bind(comment, buttonClickListener);
//        } else {
//            ((JobCommentAdapter.ChildCommentViewHolder) holder).bind(comment);
//        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent, textViewAuthor, textViewDate;
        RecyclerView childRecyclerView;
        int commentId;
        Button job_comment_childBtn, job_comment_editBtn, job_comment_deleteBtn;
        JobCommentAdapter adapter;

        public ViewHolder(View itemView, OnCommentClickListener buttonClickListener, JobCommentAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
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
                int position = getAdapterPosition();
                if (buttonClickListener != null && position != RecyclerView.NO_POSITION) {
                    // 데이터 수정
                    adapter.commentsList.get(position).setCommentContent("삭제된 댓글입니다");
                    // RecyclerView 업데이트
                    adapter.notifyItemChanged(position);
                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.DELETE);
                }
            });

//            job_comment_deleteBtn.setOnClickListener(v -> {
//                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
//                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.DELETE);
//                }
//            });

        }

        public void bind(JobPostCommentResponseDTO dto, OnCommentClickListener buttonClickListener) {
            commentId = dto.getCommentId();  // 현재 Id 저장

            textViewContent.setText(dto.getCommentContent());
            textViewAuthor.setText(dto.getUserName());

            //자식댓글
            if (dto.getChildCommentList() != null && !dto.getChildCommentList().isEmpty()) {
//                JobCommentAdapter childAdapter = new JobCommentAdapter(dto.getChildCommentList(), buttonClickListener);

                JobChildCommentAdapter childAdapter = new JobChildCommentAdapter(dto.getChildCommentList());
                childRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                childRecyclerView.setAdapter(childAdapter);
                childRecyclerView.setVisibility(View.VISIBLE); // 자식 댓글이 있을 때 보이게 처리

            } else {
                childRecyclerView.setVisibility(View.GONE);  // 자식 댓글이 없을 경우 RecyclerView 숨김 처리
            }

        }
    }



}

