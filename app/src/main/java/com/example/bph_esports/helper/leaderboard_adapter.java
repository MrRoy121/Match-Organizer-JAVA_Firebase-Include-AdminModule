package com.example.bph_esports.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bph_esports.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class leaderboard_adapter extends RecyclerView.Adapter<leaderboard_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<team_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public leaderboard_adapter(ArrayList<team_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public leaderboard_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new leaderboard_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.lead_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull leaderboard_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        team_list courses = coursesArrayList.get(position);
        holder.tname.setText(courses.getName());
        holder.mp.setText(courses.getUid());
        holder.sl.setText(String.valueOf(holder.getAdapterPosition()+1));
        holder.kill.setText(String.valueOf(courses.getType()));
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

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView sl, tname, kill, mp;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            sl = itemView.findViewById(R.id.sl);
            tname = itemView.findViewById(R.id.tname);
            kill = itemView.findViewById(R.id.kill);
            mp = itemView.findViewById(R.id.mp);

            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
