package com.example.hero.matching.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnCommentClickListener;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;

import java.util.List;

public class MatchingCommentAdapter extends RecyclerView.Adapter<MatchingCommentAdapter.ViewHolder> {

    private List<MatchingPostCommentResponseDTO> commentsList;
    private OnCommentClickListener buttonClickListener;

    public MatchingCommentAdapter(List<MatchingPostCommentResponseDTO> commentsList, OnCommentClickListener buttonClickListener) {
        this.commentsList = commentsList;
        this.buttonClickListener = buttonClickListener;

    }

    @Override
    public int getItemViewType(int position) {
        MatchingPostCommentResponseDTO comment = commentsList.get(position);
        return comment.isParent() ? 0 : 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matching_comment_list_item, parent, false);
        return new ViewHolder(view, buttonClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchingPostCommentResponseDTO dto = commentsList.get(position);
        holder.bind(dto, buttonClickListener);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewContent, textViewAuthor, textViewDate;
        RecyclerView childRecyclerView;
        int commentId;
        Button matching_comment_childBtn, matching_comment_editBtn, matching_comment_deleteBtn;

        public ViewHolder(View itemView, OnCommentClickListener buttonClickListener) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.matching_comment_content);
            textViewAuthor = itemView.findViewById(R.id.matching_comment_userName);
            childRecyclerView = itemView.findViewById(R.id.matching_commentChild_recyclerView);

            matching_comment_childBtn = itemView.findViewById(R.id.matching_comment_childBtn);
            matching_comment_editBtn = itemView.findViewById(R.id.matching_comment_editBtn);
            matching_comment_deleteBtn = itemView.findViewById(R.id.matching_comment_deleteBtn);

            matching_comment_childBtn.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.CHILD);
                }
            });

            matching_comment_editBtn.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.EDIT);
                }
            });

            matching_comment_deleteBtn.setOnClickListener(v -> {
                if (buttonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    buttonClickListener.OnCommentClick(commentId, OnCommentClickListener.ButtonType.DELETE);
                }
            });
        }

        public void bind(MatchingPostCommentResponseDTO dto, OnCommentClickListener buttonClickListener) {
            commentId = dto.getCommentId();  // 현재 Id 저장

            textViewContent.setText(dto.getCommentContent());
            textViewAuthor.setText(dto.getUserName());

            //자식댓글
            if (dto.getChildCommentList() != null && !dto.getChildCommentList().isEmpty()) {
                MatchingChildCommentAdapter childAdapter = new MatchingChildCommentAdapter(dto.getChildCommentList());
                childRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                childRecyclerView.setAdapter(childAdapter);

            } else {
                childRecyclerView.setVisibility(View.GONE);  // 자식 댓글이 없을 경우 RecyclerView 숨김 처리
            }
        }

    }
}