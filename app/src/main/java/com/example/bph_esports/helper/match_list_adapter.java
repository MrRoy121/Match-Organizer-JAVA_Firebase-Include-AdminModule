package com.example.bph_esports.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.bph_esports.match_room;
import com.example.bph_esports.payment;

import java.util.ArrayList;

public class match_list_adapter extends RecyclerView.Adapter<match_list_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<match_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public match_list_adapter(ArrayList<match_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        match_list courses = coursesArrayList.get(position);
        holder.fst.setText(courses.getFst());
        holder.regions.setText(courses.getRegion());
        holder.feess.setText(courses.getFee());
        holder.pers.setText(courses.getPer());
        holder.perss.setText(courses.getPers());
        holder.times.setText(courses.getTime());
        holder.types.setText(courses.getType());
        holder.maxs.setText(courses.getMax());
        holder.map.setText(courses.getMap());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("You Sure you want to join The room");
                builder.setMessage("Click Yes To Proceed To Payment")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) */{
                                Intent i = new Intent(context, match_room.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("1", courses.getRegion());
                                bundle.putString("2", courses.getFee());
                                bundle.putString("3", courses.getPer());
                                bundle.putString("4", courses.getPers());
                                bundle.putString("5", courses.getTime());
                                bundle.putString("6", courses.getFst());
                                bundle.putString("7", courses.getSnd());
                                bundle.putString("7q", courses.getThd());
                                bundle.putString("8", courses.getMax());
                                bundle.putString("9", courses.getType());
                                bundle.putString("10", courses.getUid());
                                bundle.putString("11", courses.getMap());
                                i.putExtras(bundle);
                                context.startActivity(i);

                            }/*
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();*/

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
        private final TextView regions, feess, pers, perss, times, fst, maxs, types, map;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            regions = itemView.findViewById(R.id.region);
            feess = itemView.findViewById(R.id.fees);
            pers = itemView.findViewById(R.id.per);
            perss = itemView.findViewById(R.id.perspect);
            times = itemView.findViewById(R.id.time);
            types = itemView.findViewById(R.id.type);
            maxs = itemView.findViewById(R.id.max);
            fst = itemView.findViewById(R.id.fst);
            map = itemView.findViewById(R.id.map);
            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
