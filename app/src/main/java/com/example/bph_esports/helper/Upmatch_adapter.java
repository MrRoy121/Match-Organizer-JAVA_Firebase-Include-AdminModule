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
import com.example.bph_esports.match_room;

import java.util.ArrayList;

public class Upmatch_adapter extends RecyclerView.Adapter<Upmatch_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<history_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public Upmatch_adapter(ArrayList<history_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Upmatch_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Upmatch_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.mm_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Upmatch_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        history_list courses = coursesArrayList.get(position);
        holder.dime.setText(courses.getStats());
        holder.type.setText(courses.getDatetime());
        holder.mid.setText(courses.getUid());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("You Sure you want to join The room");
                builder.setMessage("Click Yes To Proceed To Payment")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                    Intent i = new Intent(context, match_room.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("1", courses.getRegion());
                    bundle.putString("2", courses.getFee());
                    bundle.putString("3", courses.getPer());
                    bundle.putString("4", courses.getPers());
                    bundle.putString("5", courses.getTime());
                    bundle.putString("6", courses.getRoomid());
                    bundle.putString("7", courses.getRoompass());
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
