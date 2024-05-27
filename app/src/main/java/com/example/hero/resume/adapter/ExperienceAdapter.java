package com.example.hero.resume.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.resume.dto.ExperienceDTO;

import java.util.ArrayList;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ViewHolder> {

    private ArrayList<ExperienceDTO> dataList;

    public ExperienceAdapter(ArrayList<ExperienceDTO> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_experience, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //리스트 각 아이템 객체로
        ExperienceDTO data = dataList.get(position);

        //생성된 객체에서 값 뿌려주기
        holder.txtexperience.setText(data.getExperience());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtexperience;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //아이템 뷰 초기화
            txtexperience = itemView.findViewById(R.id.txt_experience);
        }
    }
}