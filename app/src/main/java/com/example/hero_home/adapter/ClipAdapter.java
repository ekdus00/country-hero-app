package com.example.hero_home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero_home.R;
import com.example.hero_home.activity.ApiService;
import com.example.hero_home.activity.JobDetail;
import com.example.hero_home.activity.RetrofitClient;
import com.example.hero_home.model.ClipDTO;
import com.example.hero_home.model.ClipDeleteRequestDTO;
import com.example.hero_home.util.Token;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

interface CallBack {
    void deleteItem(int jobId);
}
public class ClipAdapter extends RecyclerView.Adapter<ClipAdapter.ViewHolder>{

    private List<ClipDTO> dataList;

    public ClipAdapter(List<ClipDTO> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_clip, parent, false);

        return new ClipAdapter.ViewHolder(itemView, new CallBack() {
            @Override
            public void deleteItem(int jobId) {
                deleteClipItem(jobId);
            }
        }, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClipDTO data = dataList.get(position);

        holder.jobId = data.getJobId();
        holder.title.setText(data.getJobName());
        holder.cropType.setText("품목 > " + data.getCropType());
        holder.country.setText(data.getCountry() + " " + data.getCity());
        holder.workPeriod.setText("작업기간: " + data.getStartWorkDate() + " ~ " + data.getEndWorkDate());
        holder.recreuitPeriod.setText("모집기간: " + data.getStartRecruitDate() + " ~ " + data.getEndRecruitDate());


        // DecimalFormat 클래스를 사용하여 최대 6자리의 숫자가 들어오면 천단위에 콤마를 찍어줌.
        DecimalFormat df = new DecimalFormat("###,###");
        String formatMoney = df.format(data.getPay());

        holder.pay.setText(formatMoney);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void deleteClipItem(int jobId) {
        for (int i = 0; i < dataList.size();i++) {
            if(jobId == dataList.get(i).getJobId()) {
                dataList.remove(i);
                this.notifyDataSetChanged();
                break;
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int jobId;
        public TextView title;
        public TextView country;

        public TextView cropType;
        public TextView workPeriod;
        public TextView recreuitPeriod;
        public TextView pay;
        public TextView cancleBtn;

        // 각 스크랩 아이템 클릭했을 때의 이벤트를 감지하기 위해서 선언한 변수
        public LinearLayout clipItem;

        public ViewHolder(@NonNull View itemView, CallBack callBack, Context context) {
            super(itemView);

            title = itemView.findViewById(R.id.jobTitle);
            country = itemView.findViewById(R.id.country);
            cropType = itemView.findViewById(R.id.cropType);
            workPeriod = itemView.findViewById(R.id.work_period);
            recreuitPeriod = itemView.findViewById(R.id.recreuit_period);
            pay = itemView.findViewById(R.id.pay);

            cancleBtn = itemView.findViewById(R.id.cancle_btn);
            cancleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteClip(jobId, callBack);
                }
            });

            clipItem = itemView.findViewById(R.id.clip_item);
            clipItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, JobDetail.class);
                    context.startActivity(intent);
                }
            });
        }

        // 스크랩 취소하기
        private void deleteClip(int jobId, CallBack callBack) {
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<Void> call = apiService.deleteClip(Token.token, new ClipDeleteRequestDTO(jobId));
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        callBack.deleteItem(jobId);
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

    // 스크랩 목록 각 아이템 별 상하 여백을 주기 위한 클래스
    public static class OffsetItemDecoration extends RecyclerView.ItemDecoration {

        private final int mPadding;

        public OffsetItemDecoration(int a_padding) {
            mPadding = a_padding;
        }

        @Override
        public void getItemOffsets(Rect a_outRect, View a_view, RecyclerView a_parent, RecyclerView.State a_state) {
            super.getItemOffsets(a_outRect, a_view, a_parent, a_state);

            a_outRect.top = mPadding;
            a_outRect.bottom = mPadding;
        }
    }
}
