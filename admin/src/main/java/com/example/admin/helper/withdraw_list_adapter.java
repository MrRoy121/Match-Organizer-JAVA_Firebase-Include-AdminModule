package com.example.admin.helper;

import android.app.AlertDialog;
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

import com.example.admin.R;
import com.example.admin.editmatch;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class withdraw_list_adapter extends RecyclerView.Adapter<withdraw_list_adapter.ViewHolder> {

    private ArrayList<match_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public withdraw_list_adapter(ArrayList<match_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public withdraw_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new withdraw_list_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.withdraw_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull withdraw_list_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        match_list courses = coursesArrayList.get(position);
        holder.uid.setText(courses.getFst());
        holder.uname.setText(courses.getScnd());
        holder.amount.setText(courses.getRegion());
        holder.dtime.setText(courses.getFee());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Payment Completed?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Log.e("TAg",courses.getThrd());
                                db.collection("WithdrawRequest").document(courses.getThrd())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Completed!!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return coursesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView uid, uname, dtime, amount;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            uid = itemView.findViewById(R.id.uid);
            uname = itemView.findViewById(R.id.uname);
            amount = itemView.findViewById(R.id.amount);
            dtime = itemView.findViewById(R.id.dtime);
            relativeLayout = itemView.findViewById(R.id.layot);
        }
    }
}
