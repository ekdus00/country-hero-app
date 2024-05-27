package com.example.hero.matching.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;

import java.util.List;

public class MatchingCommentAdapter extends RecyclerView.Adapter<MatchingCommentAdapter.ViewHolder>{

    private List<MatchingPostCommentResponseDTO> dataList;

    public MatchingCommentAdapter(List<MatchingPostCommentResponseDTO> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MatchingCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_commnt, parent, false);

        return new MatchingCommentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchingCommentAdapter.ViewHolder holder, int position) {
        MatchingPostCommentResponseDTO data = dataList.get(position);

        holder.writer.setText(data.getUserName());
        holder.createdDate.setText(data.getUpdatedCommentDatetime());
        holder.content.setText(data.getCommentContent());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView writer;
        public TextView createdDate;
        public TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            writer = itemView.findViewById(R.id.writer);
            createdDate = itemView.findViewById(R.id.txt_created_date);
            content = itemView.findViewById(R.id.txt_content);
        }
    }
}
