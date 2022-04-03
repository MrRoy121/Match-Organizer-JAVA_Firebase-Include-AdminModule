package com.example.bph_esports.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bph_esports.R;
import com.example.bph_esports.exam_room;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class quiz_result_adapter extends RecyclerView.Adapter<quiz_result_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<history_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public quiz_result_adapter(ArrayList<history_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public quiz_result_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new quiz_result_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.quiz_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull quiz_result_adapter.ViewHolder holder, int position) {

        history_list courses = coursesArrayList.get(position);
        holder.qid.setText(courses.getStats());
        holder.pid.setText(courses.getDatetime());
        holder.corr.setText(courses.getUid());


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
        private final TextView qid, pid, corr;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            qid = itemView.findViewById(R.id.qid);
            pid = itemView.findViewById(R.id.pid);
            corr = itemView.findViewById(R.id.corr);
            relativeLayout = itemView.findViewById(R.id.lyout);
        }
    }
}
