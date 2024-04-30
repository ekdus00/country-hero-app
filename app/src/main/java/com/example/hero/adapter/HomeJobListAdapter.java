

package com.example.hero.adapter;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.hero.R;

        import java.util.ArrayList;

public class HomeJobListAdapter extends RecyclerView.Adapter<HomeJobListAdapter.ViewHolder>{

    private ArrayList<String> localDataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.home_list_title);
        }
        public TextView getTextView() {
            return textView;
        }
    }
    public HomeJobListAdapter (ArrayList<String> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public HomeJobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_job_list_item, parent, false);
        HomeJobListAdapter.ViewHolder viewHolder = new HomeJobListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeJobListAdapter.ViewHolder holder, int position) {
        String text = localDataSet.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

