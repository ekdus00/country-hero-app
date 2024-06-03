package com.example.hero.matching.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.OnItemClickListenerMatching;
import com.example.hero.matching.dto.MentorRecommendationResponseDTO;

import java.util.List;

public class MatchingRecommendAdapter extends RecyclerView.Adapter<MatchingRecommendAdapter.ViewHolder> {
    private List<MentorRecommendationResponseDTO> slides;
    private OnItemClickListenerMatching listener;


    public MatchingRecommendAdapter(List<MentorRecommendationResponseDTO> slides, OnItemClickListenerMatching listener) {
        this.slides = slides;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maching_recom, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MentorRecommendationResponseDTO dto = slides.get(position);
        holder.bind(dto, listener);
    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recom_name, recom_id, recom_type;
        String userId;

        public void bind(MentorRecommendationResponseDTO dto, OnItemClickListenerMatching listener) {
            userId = dto.getUserId();  // 현재 userId 저장

            recom_name.setText(dto.getUserName());
            recom_id.setText(dto.getUserId());
//            recom_type.setText(dto.getUserType());

        }


        public ViewHolder(@NonNull View itemView, final OnItemClickListenerMatching listener) {
            super(itemView);
            recom_name = itemView.findViewById(R.id.recom_name);
            recom_id = itemView.findViewById(R.id.recom_id);
//            recom_type = itemView.findViewById(R.id.recom_type);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(userId);
                }
            });

        }
    }
}