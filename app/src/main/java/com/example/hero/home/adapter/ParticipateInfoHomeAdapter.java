package com.example.hero.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.home.dto.ParticipateInfoHomeDTO;

import java.util.List;

public class ParticipateInfoHomeAdapter extends RecyclerView.Adapter<ParticipateInfoHomeAdapter.ViewHolder> {
    private List<ParticipateInfoHomeDTO> mData;

    public ParticipateInfoHomeAdapter(List<ParticipateInfoHomeDTO> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_applicant_status_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParticipateInfoHomeDTO participateInfo = mData.get(position);
        holder.applicant_status_address_country.setText(participateInfo.getCountry());
        holder.applicant_status_address_city.setText(participateInfo.getCity());
        holder.applicant_status_title.setText(participateInfo.getJobName());
        holder.applicant_status_approval.setText(participateInfo.getApproval());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView applicant_status_address_country;
        TextView applicant_status_address_city;
        TextView applicant_status_title;
        TextView applicant_status_approval;

        public ViewHolder(View itemView) {
            super(itemView);
            applicant_status_address_country = itemView.findViewById(R.id.applicant_status_address_country);
            applicant_status_address_city = itemView.findViewById(R.id.applicant_status_address_city);
            applicant_status_title = itemView.findViewById(R.id.applicant_status_title);
            applicant_status_approval = itemView.findViewById(R.id.applicant_status_approval);

        }
    }
}
