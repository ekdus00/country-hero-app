package com.example.hero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MentorMenteeAdapter extends RecyclerView.Adapter<MentorMenteeAdapter.ViewHolder> {

    private final ArrayList<MentorMenteeData> dataList;

    public MentorMenteeAdapter(ArrayList<MentorMenteeData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_matching, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //리스트 각 아이템 객체로
        MentorMenteeData data = dataList.get(position);

        //생성된 객체에서 각각값 뿌려주기
        holder.txtStatus.setText(data.getStatus());
        holder.txtContent.setText(data.getContent());
        holder.txtDate.setText(data.getDate());
        holder.txtName.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtStatus;
        public TextView txtContent;
        public TextView txtDate;
        public TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //아이템 뷰 초기화
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtContent = itemView.findViewById(R.id.txt_content);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtName = itemView.findViewById(R.id.txt_name);

        }
    }
}