package com.example.hero.matching.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.matching.activity.MenteeDetail;
import com.example.hero.matching.activity.MentorDetail;
import com.example.hero.matching.dto.MatchingListInfoDTO;

import java.util.List;

public class MentorMenteeAdapter extends RecyclerView.Adapter<MentorMenteeAdapter.ViewHolder> {

    private final List<MatchingListInfoDTO> dataList;

    public MentorMenteeAdapter(List<MatchingListInfoDTO> dataList) {
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
        MatchingListInfoDTO data = dataList.get(position);

        //생성된 객체에서 각각값 뿌려주기

        if (data.getWriterType().equals("mentor")){
            holder.txtStatus.setText("멘토");
        } else {
            holder.txtStatus.setText("멘티");
        }

        String createDate = data.getCreatedMatchingDate();
        String dateOnly = createDate.substring(0, 10);
        holder.txtDate.setText(dateOnly);

        holder.txtContent.setText(data.getMatchingName());
        holder.txtName.setText(data.getUserName());

        holder.matchingId.setText(data.getMatchingId().toString());
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
        public TextView matchingId;
        public RelativeLayout matchingItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //아이템 뷰 초기화
            matchingItem = itemView.findViewById(R.id.matching_item);
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtContent = itemView.findViewById(R.id.txt_content);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtName = itemView.findViewById(R.id.txt_name);
            matchingId = itemView.findViewById(R.id.matching_id);

            matchingItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txtStatus.getText().toString().equals("mentor")) {
                        Intent intent = new Intent(itemView.getContext(), MenteeDetail.class);
                        intent.putExtra("matchingId", matchingId.getText().toString());
                        itemView.getContext().startActivity(intent);
                    } else {
                        Intent intent = new Intent(itemView.getContext(), MentorDetail.class);
                        intent.putExtra("matchingId", matchingId.getText().toString());
                        itemView.getContext().startActivity(intent);
                    }
                }
            });

        }
    }
}
