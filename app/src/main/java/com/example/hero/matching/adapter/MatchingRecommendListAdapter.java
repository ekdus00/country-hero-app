package com.example.hero.matching.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.matching.dto.MatchingListInfoDTO;

import java.util.List;


public class MatchingRecommendListAdapter extends RecyclerView.Adapter<MatchingRecommendListAdapter.ViewHolder> {
    private List<MatchingListInfoDTO> slides;

    public MatchingRecommendListAdapter(List<MatchingListInfoDTO> slides) {
        this.slides = slides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matching_recom_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchingListInfoDTO dto = slides.get(position);
        holder.recom_title.setText(dto.getMatchingName());
        holder.recom_userName.setText(dto.getUserName());

        String createDate = dto.getCreatedMatchingDate();
        String dateOnly = createDate.substring(0, 10);
        holder.recom_date.setText(dateOnly);
    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView recom_title, recom_userName, recom_date;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            recom_title = itemView.findViewById(R.id.recom_title);
            recom_userName = itemView.findViewById(R.id.recom_userName);
            recom_date = itemView.findViewById(R.id.recom_date);

        }
    }
}