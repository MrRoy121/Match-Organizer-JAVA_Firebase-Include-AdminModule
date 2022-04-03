package com.example.admin.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;

import java.util.ArrayList;

public class mamem_list_adapter extends RecyclerView.Adapter<mamem_list_adapter.ViewHolder> {

    private ArrayList<match_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public mamem_list_adapter(ArrayList<match_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public mamem_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mamem_list_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.mamem_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull mamem_list_adapter.ViewHolder holder, int position) {
        match_list courses = coursesArrayList.get(position);
        if(courses.getPer().equals("SOLO")){
            holder.uid.setText(courses.getFst());
            holder.uname.setText(courses.getScnd());
            holder.amount.setVisibility(View.GONE);
            holder.uname2.setVisibility(View.GONE);
            holder.uname3.setVisibility(View.GONE);

        }else if(courses.getPer().equals("DUO")){
            holder.uid.setText(courses.getFst());
            holder.uname.setText(courses.getScnd());
            holder.amount.setText(courses.getRegion());
            holder.uname2.setVisibility(View.GONE);
            holder.uname3.setVisibility(View.GONE);
        }else if(courses.getPer().equals("SQUAD")){

            holder.uid.setText(courses.getFst());
            holder.uname.setText(courses.getScnd());
            holder.amount.setText(courses.getRegion());
            holder.uname2.setText(courses.getTime());
            holder.uname3.setText(courses.getPers());
            holder.uname4.setText(courses.getUid());
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {

        return coursesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView uid, uname, amount, uname2, uname3, uname4;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            uid = itemView.findViewById(R.id.uid);
            uname = itemView.findViewById(R.id.uname);
            amount = itemView.findViewById(R.id.name);
            uname2 = itemView.findViewById(R.id.uname2);
            uname3 = itemView.findViewById(R.id.uname3);
            uname4 = itemView.findViewById(R.id.uname4);
            relativeLayout = itemView.findViewById(R.id.layot);
        }
    }
}
