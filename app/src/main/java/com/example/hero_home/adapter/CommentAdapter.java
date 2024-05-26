package com.example.hero_home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero_home.R;
import com.example.hero_home.model.ClipDTO;
import com.example.hero_home.model.MatchingPostCommentRequestDTO;
import com.example.hero_home.model.MatchingPostCommentResponseDTO;

import java.text.DecimalFormat;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<MatchingPostCommentResponseDTO> dataList;

    public CommentAdapter(List<MatchingPostCommentResponseDTO> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_commnt, parent, false);

        return new CommentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
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
