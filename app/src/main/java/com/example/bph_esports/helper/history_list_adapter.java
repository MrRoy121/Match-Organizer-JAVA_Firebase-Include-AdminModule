package com.example.bph_esports.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bph_esports.R;

import java.util.ArrayList;

public class history_list_adapter extends RecyclerView.Adapter<history_list_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<history_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public history_list_adapter(ArrayList<history_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public history_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new history_list_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull history_list_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        history_list courses = coursesArrayList.get(position);
        holder.time.setText(courses.getDatetime());
        holder.stats.setText(courses.getStats());
        holder.amount.setText(String.valueOf(courses.getAmount()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return coursesArrayList.size();
    }

    public void clear() {
        int size = coursesArrayList.size();
        coursesArrayList.clear();
        notifyItemRangeRemoved(0, size);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView time, stats, amount;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            time = itemView.findViewById(R.id.dtime);
            amount = itemView.findViewById(R.id.amount);
            stats = itemView.findViewById(R.id.stats);

            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
