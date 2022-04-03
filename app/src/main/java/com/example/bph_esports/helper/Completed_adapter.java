package com.example.bph_esports.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bph_esports.R;
import com.example.bph_esports.completed_room;

import java.util.ArrayList;

public class Completed_adapter extends RecyclerView.Adapter<Completed_adapter.ViewHolder> {
    private ArrayList<completed_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public Completed_adapter(ArrayList<completed_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Completed_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Completed_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.mm_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Completed_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        completed_list courses = coursesArrayList.get(position);
        holder.dime.setText(courses.getTname());
        holder.type.setText(courses.getKills());
        holder.mid.setText(courses.getMatchid());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(context, completed_room.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("1", courses.getType());
                    bundle.putString("2", courses.getMatchid());
                    bundle.putString("3", courses.getUid());
                    i.putExtras(bundle);
                    context.startActivity(i);

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
        private final TextView dime, type, mid;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            dime = itemView.findViewById(R.id.dime);
            type = itemView.findViewById(R.id.type);
            mid = itemView.findViewById(R.id.matchid);
            relativeLayout = itemView.findViewById(R.id.lyout);
        }
    }
}
