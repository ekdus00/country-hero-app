package com.example.hero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.object.Job;

import java.util.ArrayList;

import com.example.hero.Listener.OnJobItemClickListener;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder>
                            implements OnJobItemClickListener {
    ArrayList<Job> items = new ArrayList<Job>();
    OnJobItemClickListener listener;
    int layoutType = 0;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.job_list_item, viewGroup, false);

        return new ViewHolder(itemView, this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Job item = items.get(position);
        viewHolder.setItem(item);
//        String text = localDataSet.get(position);
//        viewHolder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Job item) {
        items.add(item);
    }

    public void setItems(ArrayList<Job> items) {
        this.items = items;
    }

    public Job getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnJobItemClickListener listener) {
        this.listener = listener;
    }

    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

//    public void switchLayout(int position) {
//        layoutType = position;
//    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView job_list_title;
        TextView job_list_work_period;
        TextView job_list_recruitment_period;
        TextView job_list_type;
        TextView job_list_salary;

        public ViewHolder(View itemView, final  OnJobItemClickListener listener, int layoutType) {
            super(itemView);

            job_list_title = itemView.findViewById(R.id.job_list_title);
            job_list_work_period = itemView.findViewById(R.id.job_list_work_period);
            job_list_recruitment_period = itemView.findViewById(R.id.job_list_recruitment_period);
            job_list_type = itemView.findViewById(R.id.job_list_type);
            job_list_salary = itemView.findViewById(R.id.job_list_salary);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
//            setLayoutType(layoutType);
        }

        public void setItem(Job item) {
            job_list_title.setText(item.getContents());
            job_list_work_period.setText(item.getContents());
            job_list_recruitment_period.setText(item.getContents());
            job_list_type.setText(item.getContents());
            job_list_salary.setText(item.getContents());
        }

    }

}
