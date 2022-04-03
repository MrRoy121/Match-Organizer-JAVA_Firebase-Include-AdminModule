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
    public match_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull match_list_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        match_list courses = coursesArrayList.get(position);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("You Edit Or Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(context, editmatch.class);
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
                                bundle.putString("11", courses.getMap());
                                i.putExtras(bundle);
                                context.startActivity(i);

                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Daily Matches").document(courses.getUid())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Match is Deleted!!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

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
        private final TextView regions, feess, pers, perss, times, maxs, types;
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
            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
