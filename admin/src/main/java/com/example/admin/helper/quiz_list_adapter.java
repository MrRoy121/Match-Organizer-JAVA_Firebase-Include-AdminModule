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
import com.example.admin.editmatch;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class quiz_list_adapter extends RecyclerView.Adapter<quiz_list_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<match_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public quiz_list_adapter(ArrayList<match_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public quiz_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new quiz_list_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.quiz_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull quiz_list_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        match_list courses = coursesArrayList.get(position);
        holder.eid.setText(courses.getRegion());
        holder.fee.setText(courses.getFee());
        holder.len.setText(courses.getPer());
        holder.fst.setText(courses.getFst());
        holder.snd.setText(courses.getScnd());
        holder.thd.setText(courses.getThrd());
        holder.time.setText(courses.getTime());
        holder.num.setText(courses.getType());
        holder.date.setText(courses.getMax());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("You Edit Or Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "Comming Soon", Toast.LENGTH_SHORT).show();
                               /* Intent i = new Intent(context, editmatch.class);
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
                                context.startActivity(i);*/

                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                DocumentReference f = db.collection("Quiz").document(courses.getUid());
                                f.collection("ExamId").document(courses.getRegion())
                                .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Quiz is Deleted!!", Toast.LENGTH_SHORT).show();
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
        private final TextView eid, num, date, time, fee, len, fst, snd, thd;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            eid = itemView.findViewById(R.id.mid);
            fee = itemView.findViewById(R.id.fees);
            num = itemView.findViewById(R.id.num);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            fst = itemView.findViewById(R.id.fst);
            snd = itemView.findViewById(R.id.snd);
            thd = itemView.findViewById(R.id.thd);
            len = itemView.findViewById(R.id.len);
            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
