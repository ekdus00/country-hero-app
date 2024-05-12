package com.example.hero;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClipAdapter extends RecyclerView.Adapter<ClipAdapter.ViewHolder>{

    private List<ClipDTO> dataList;
    private Context context;

    public ClipAdapter(List<ClipDTO> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_clip, parent, false);

        return new ClipAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClipDTO data = dataList.get(position);

        holder.title.setText(data.getJobName());
        holder.country.setText(data.getCountry());
        holder.startWorkDate.setText(data.getStartWorkDate());
        holder.endWorkDate.setText(data.getEndWorkDate());
        holder.pay.setText(data.getPay());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout clipSection; // 스크랩 공고(클릭하면 상세 페이지로 넘어감)
        public TextView title;
        public TextView country;
        public TextView startWorkDate;
        public TextView endWorkDate;
        public TextView pay;
        public TextView cancleBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            clipSection = itemView.findViewById(R.id.scrap_item);
            cancleBtn = itemView.findViewById(R.id.cancle_btn);

            clipSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToDetailView();
                }
            });
            cancleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteClip();
                }
            });
        }

        // 스크랩 취소하기
        private void deleteClip() {
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

            Call<Void> call = apiService.deleteClip();
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Void res = response.body();
                        Log.d("SCRAP", res.toString());
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ScrapActivity.this,
//                            android.R.layout.simple_spinner_dropdown_item, clipList);
//                    spinnerProvince.setAdapter(adapter);
                    } else {
                        Log.e("API_CALL", "Response error: " + response.errorBody());

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("SCRAP", "스크랩 취소하기 에러 발생", t);
                }
            });
        }
    }

    private void goToDetailView() {
        Intent intent = new Intent(context, JobDetail.class);
        context.startActivity(intent);
    }
}
