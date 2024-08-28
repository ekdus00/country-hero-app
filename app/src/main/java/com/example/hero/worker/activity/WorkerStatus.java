package com.example.hero.worker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.JobDetail;
import com.example.hero.worker.adapter.WorkerStatusAdapterA;
import com.example.hero.worker.adapter.WorkerStatusAdapterB;
import com.example.hero.worker.adapter.WorkerStatusAdapterC;
import com.example.hero.worker.dto.CareerDTO;
import com.example.hero.worker.dto.ParticipateDeleteRequestDTO;
import com.example.hero.worker.dto.ParticipateInfoDTO;
import com.example.hero.worker.dto.ParticipateResponseDTO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerStatus extends AppCompatActivity{
    private ApiService apiService;
    private Context context;
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener buttonClickListener;
    public RecyclerView worker_status_apply_recyclerView, worker_status_approval_recyclerView, worker_status_defer_recyclerView, worker_status_past_recyclerView;
    WorkerStatusAdapterA adapter1;
    WorkerStatusAdapterB adapter2;
    WorkerStatusAdapterB adapter3;
    WorkerStatusAdapterC adapter4;
    private List<ParticipateInfoDTO> list1  = new ArrayList<>();
    private List<ParticipateInfoDTO> list2  = new ArrayList<>();
    private List<ParticipateInfoDTO> list3  = new ArrayList<>();
    private List<CareerDTO> list4  = new ArrayList<>();
    private TokenManager tokenManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_status);
        tokenManager = new TokenManager(this);

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("지원현황");

        worker_status_apply_recyclerView = findViewById(R.id.worker_status_apply_recyclerView);
        worker_status_approval_recyclerView = findViewById(R.id.worker_status_approval_recyclerView);
        worker_status_defer_recyclerView = findViewById(R.id.worker_status_defer_recyclerView);
        worker_status_past_recyclerView = findViewById(R.id.worker_status_past_recyclerView);

        worker_status_apply_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        worker_status_approval_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        worker_status_defer_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        worker_status_past_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemClickListener = jobId -> {
            Intent intent = new Intent(WorkerStatus.this, JobDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };

        buttonClickListener = jobId -> {
            workerStatusDelete(jobId);
        };

        workerStatusRequest();

    }//onCreate()

    private void workerStatusRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //지원현황 서버요청
        Call<ParticipateResponseDTO> call = apiService.getParticipateList();
        call.enqueue(new Callback<ParticipateResponseDTO>() {
            @Override
            public void onResponse(Call<ParticipateResponseDTO> call, Response<ParticipateResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ParticipateResponseDTO dto = response.body();

                    List<ParticipateInfoDTO> list1 = dto.getJobRequestList();
                    List<ParticipateInfoDTO> list2 = dto.getApproveParticipateList();
                    List<ParticipateInfoDTO> list3 = dto.getDeferParticipateList();
                    List<CareerDTO> list4 = dto.getPreviousParticipateList();

                    adapter1 = new WorkerStatusAdapterA(list1, itemClickListener, buttonClickListener); //버튼 리스너 추가 해야됨
                    adapter2 = new WorkerStatusAdapterB(list2, itemClickListener);
                    adapter3 = new WorkerStatusAdapterB(list3, itemClickListener);
//                    adapter4 = new WorkerStatusAdapterC(list4, itemClickListener);

                    worker_status_apply_recyclerView.setAdapter(adapter1);
                    worker_status_approval_recyclerView.setAdapter(adapter2);
                    worker_status_defer_recyclerView.setAdapter(adapter3);
//                    worker_status_past_recyclerView.setAdapter(adapter4);

                } else {
                    Log.e("api", "지원현황 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("api", "지원현황 서버응답 실패" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ParticipateResponseDTO> call, Throwable t) {
                Log.e("NETWORK_ERROR", "지원현황 서버요청 실패", t);
            }
        });

    }

    
    private void workerStatusDelete(int jobId) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        ParticipateDeleteRequestDTO dto = new ParticipateDeleteRequestDTO();
        dto.setJobId(jobId);

        //지원취소 서버요청
        Call<ResponseBody> call = apiService.DeleteParticipate(dto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(WorkerStatus.this, WorkerStatus.class);
                    intent.putExtra("jobId", jobId);
                    startActivity(intent);

                    removeItemFromRecyclerView(jobId);
                    Log.e("api", "지원취소 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "지원취소 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("api", "지원취소 서버응답 실패" + response.errorBody());                
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("NETWORK_ERROR", "지원현황 서버요청 실패", t);
            }
        });

    }

    private void removeItemFromRecyclerView(int jobId) {
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).getJobId() == jobId) {
                list1.remove(i);
                adapter1.notifyItemRemoved(i);
                return;
            }
        }
    }

}
