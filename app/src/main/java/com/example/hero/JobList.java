package com.example.hero;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.object.Job;

import com.example.hero.Listener.OnJobItemClickListener;

public class JobList extends AppCompatActivity {
    RecyclerView recyclerView;
    JobListAdapter adapter;
    Context context;
    private ViewGroup rootView;

//    static RequestQueue requestQueue;
//    public TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_list);

        Button search_detail = findViewById(R.id.search_detail);
//        textView = findViewById(R.id.job_list_sum);

        rootView = findViewById(android.R.id.content);
        initUI(rootView);

    }



//        RecyclerView recyclerView = findViewById(R.id.job_list_recyclerView);
//
//        //예시데이터
//        ArrayList<String> testDataSet = new ArrayList<>();
//        for (int i = 0; i<20; i++) {
//            testDataSet.add("주소" + i);
//        }
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        JobListAdapter customAdapter = new JobListAdapter(testDataSet);
//        recyclerView.setAdapter(customAdapter);

//        search_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), JobFilter.class);
//                startActivity(intent);
//            }
//        });

//        makeRequest();
//
//        if (requestQueue == null){
//            requestQueue = Volley.newRequestQueue(getApplicationContext());
//        }


        private void initUI (ViewGroup rooView){
            recyclerView = rooView.findViewById(R.id.job_list_recyclerView);

//            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);

            adapter = new JobListAdapter();
//            adapter.addItem(new Job(0, "(공고제목)", 0, ));

            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new OnJobItemClickListener() {
                @Override
                public void onItemClick(JobListAdapter.ViewHolder holder, View view, int position) {
                    Job item = adapter.getItem(position);
                }
            });
        }

}



//    public void makeRequest() {
//        String url = "서버주소";
//        StringRequest request = new StringRequest(Request.Method.GET, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    println("응답->" + response);
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    println("에러->" + error.getMessage());
//                }
//            }
//        ) {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                return params;
//            }
//        };
//        request.setShouldCache(false);
//        requestQueue.add(request);
//        println("요청 보냄");
//    }
//
//    public void println(String data) {
//        textView.append(data + "/n");
//    }


