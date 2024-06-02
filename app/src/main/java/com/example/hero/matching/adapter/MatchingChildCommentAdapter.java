package com.example.hero.matching.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.job.dto.JobPostCommentResponseDTO;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;

import java.util.List;


public class MatchingChildCommentAdapter extends RecyclerView.Adapter<MatchingChildCommentAdapter.ViewHolder> {
    private List<MatchingPostCommentResponseDTO> mData;

    public MatchingChildCommentAdapter(List<MatchingPostCommentResponseDTO> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matching_comment_list_child_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MatchingPostCommentResponseDTO dto = mData.get(position);
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
            textViewContent = itemView.findViewById(R.id.matching_comment_content);
            textViewAuthor = itemView.findViewById(R.id.matching_comment_userName);
        }

        public void bind(final MatchingPostCommentResponseDTO dto) {
            commentId = dto.getCommentId();  // 현재 Id 저장

            textViewContent.setText(dto.getCommentContent());
            textViewAuthor.setText(dto.getUserName());

        }


    }
}