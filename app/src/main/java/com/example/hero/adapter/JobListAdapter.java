//package com.example.hero.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.hero.R;
//import com.example.hero.object.Job;
//
//import java.util.ArrayList;
//
//import com.example.hero.listener.OnJobItemClickListener;
//
//public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder>
//                            implements OnJobItemClickListener {
//    ArrayList<Job> items = new ArrayList<Job>();
//    OnJobItemClickListener listener;
//    int layoutType = 0;
//
//
//    //임시
//    private ArrayList<String> localDataSet;
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView textView;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(R.id.job_list_address);
//        }
//        public TextView getTextView() {
//            return textView;
//        }
//    }
//    public JobListAdapter (ArrayList<String> dataSet) { localDataSet = dataSet; }
//    //임시
//
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//        View itemView = inflater.inflate(R.layout.job_list_item, viewGroup, false);
//
//        return new ViewHolder(itemView, this, layoutType);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
//        Job item = items.get(position);
//        viewHolder.setItem(item);
////        String text = localDataSet.get(position);
////        viewHolder.textView.setText(text);
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public void addItem(Job item) {
//        items.add(item);
//    }
//
//    public void setItems(ArrayList<Job> items) {
//        this.items = items;
//    }
//
//    public Job getItem(int position) {
//        return items.get(position);
//    }
//
//    public void setOnItemClickListener(OnJobItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    public void onItemClick(ViewHolder holder, View view, int position) {
//        if (listener != null) {
//            listener.onItemClick(holder, view, position);
//        }
//    }
//
//    public void switchLayout(int position) {
//        layoutType = position;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView job_list_title;
//        TextView job_list_work_period;
//        TextView job_list_recruitment_period;
//        TextView job_list_type;
//        TextView job_list_salary;
//
//        public ViewHolder(View itemView, final  OnJobItemClickListener listener, int layoutType) {
//            super(itemView);
//
//            job_list_title = itemView.findViewById(R.id.job_list_title);
//            job_list_work_period = itemView.findViewById(R.id.job_list_work_period);
//            job_list_recruitment_period = itemView.findViewById(R.id.job_list_recruitment_period);
//            job_list_type = itemView.findViewById(R.id.job_list_type);
//            job_list_salary = itemView.findViewById(R.id.job_list_salary);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//
//                    if (listener != null) {
//                        listener.onItemClick(ViewHolder.this, view, position);
//                    }
//                }
//            });
////            setLayoutType(layoutType);
//        }
//
//        public void setItem(Job item) {
////            job_list_title.setText(item.getContents());
////            job_list_work_period.setText(item.getContents());
////            job_list_recruitment_period.setText(item.getContents());
////            job_list_type.setText(item.getContents());
////            job_list_salary.setText(item.getContents());
//        }
//
//    }

package com.example.hero.adapter;
import com.example.hero.R;
import com.example.hero.object.JobInfoDTO;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder>{

        private List<JobInfoDTO> jobList;

        public JobListAdapter(List<JobInfoDTO> jobList) {
            this.jobList = jobList;
        }

        @NonNull
        @Override
        public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list_item, parent, false);
            return new JobViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
            JobInfoDTO job = jobList.get(position);
            holder.job_list_title.setText(job.getJobName());

            holder.job_list_address_country.setText(job.getCountry());
            holder.job_list_address_city.setText(job.getCity());
            holder.job_list_item_crop_form.setText(job.getCropForm());
            holder.job_list_item_crop_type.setText(job.getCropType());

//            holder.job_list_work_period_start.setText(job.getStartWorkDate());
//            holder.job_list_work_period_end.setText(job.getEndWorkDate());
//            holder.job_list_recruitment_period_start.setText(job.getStartRecruitDate());
//            holder.job_list_recruitment_period_end.setText(job.getEndRecruitDate());

//            Date date1 = job.getStartWorkDate();
//            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//            String stringDate1 = dateFormat1.format(date1);
//            holder.job_list_work_period_start.setText(stringDate1);
//
//            Date date2 = job.getEndWorkDate();
//            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//            String stringDate2 = dateFormat2.format(date2);
//            holder.job_list_work_period_end.setText(stringDate2);
//
//            Date date3 = job.getStartRecruitDate();
//            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
//            String stringDate3 = dateFormat3.format(date3);
//            holder.job_list_recruitment_period_start.setText(stringDate3);
//
//            Date date4 = job.getEndRecruitDate();
//            SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd");
//            String stringDate4 = dateFormat4.format(date4);
//            holder.job_list_recruitment_period_end.setText(stringDate4);

            holder.job_list_type.setText(job.getJobName());
            holder.job_list_salary.setText(job.getPay());
        }

        @Override
        public int getItemCount() {
            return jobList.size();
        }

        public static class JobViewHolder extends RecyclerView.ViewHolder {
            TextView job_list_address_country;
            TextView job_list_address_city;
            TextView job_list_item_crop_form;
            TextView job_list_item_crop_type;
            TextView job_list_title;
            TextView job_list_work_period_start;
            TextView job_list_work_period_end;
            TextView job_list_recruitment_period_start;
            TextView job_list_recruitment_period_end;
            TextView job_list_type;
            TextView job_list_salary;
            public JobViewHolder(@NonNull View itemView) {
                super(itemView);
                job_list_address_country = itemView.findViewById(R.id.job_list_address_country);
                job_list_address_city = itemView.findViewById(R.id.job_list_address_city);
                job_list_item_crop_form = itemView.findViewById(R.id.job_list_item_crop_form);
                job_list_item_crop_type = itemView.findViewById(R.id.job_list_item_crop_type);

                job_list_title = itemView.findViewById(R.id.job_list_title);

                job_list_work_period_start = itemView.findViewById(R.id.job_list_work_period_start);
                job_list_work_period_end = itemView.findViewById(R.id.job_list_work_period_end);

                job_list_recruitment_period_start = itemView.findViewById(R.id.job_list_recruitment_period_start);
                job_list_recruitment_period_end = itemView.findViewById(R.id.job_list_recruitment_period_end);

                job_list_type = itemView.findViewById(R.id.job_list_type);
                job_list_salary = itemView.findViewById(R.id.job_list_salary);
            }
        }

//        private ArrayList<String> localDataSet;
//        public static class ViewHolder extends RecyclerView.ViewHolder {
//            private TextView textView;
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                textView = itemView.findViewById(R.id.job_list_address);
//            }
//            public TextView getTextView() {
//                return textView;
//            }
//        }
//        public JobListAdapter (ArrayList<String> dataSet) { localDataSet = dataSet; }
//
//        @NonNull
//        @Override
//        public JobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.job_list_item, parent, false);
//            JobListAdapter.ViewHolder viewHolder = new JobListAdapter.ViewHolder(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull JobListAdapter.ViewHolder holder, int position) {
//            String text = localDataSet.get(position);
//            holder.textView.setText(text);
//        }
//
//        @Override
//        public int getItemCount() {
//            return localDataSet.size();
//        }


    }

