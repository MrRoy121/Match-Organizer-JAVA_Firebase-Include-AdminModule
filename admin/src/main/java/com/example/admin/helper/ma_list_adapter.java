package com.example.admin.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.match_members;
import java.util.ArrayList;

public class ma_list_adapter extends RecyclerView.Adapter<ma_list_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<match_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public ma_list_adapter(ArrayList<match_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ma_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ma_list_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ma_list_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        match_list courses = coursesArrayList.get(position);
        holder.fst.setText(courses.getFst());
        holder.snd.setText(courses.getScnd());
        holder.thd.setText(courses.getThrd());
        holder.regions.setText(courses.getRegion());
        holder.feess.setText(courses.getFee());
        holder.pers.setText(courses.getPer());
        holder.perss.setText(courses.getPers());
        holder.times.setText(courses.getTime());
        holder.types.setText(courses.getType());
        holder.maxs.setText(courses.getMax());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                                Intent i = new Intent(context, match_members.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("1", courses.getRegion());
                                bundle.putString("2", courses.getFee());
                                bundle.putString("3", courses.getPer());
                                bundle.putString("4", courses.getPers());
                                bundle.putString("5", courses.getTime());
                                bundle.putString("6", courses.getFst());
                                bundle.putString("7", courses.getScnd());
                                bundle.putString("71", courses.getThrd());
                                bundle.putString("8", courses.getMax());
                                bundle.putString("9", courses.getType());
                                bundle.putString("10", courses.getUid());
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

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView regions, feess, pers, perss, times, fst, snd, thd, maxs, types;
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
            snd = itemView.findViewById(R.id.snd);
            thd = itemView.findViewById(R.id.thd);
            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
