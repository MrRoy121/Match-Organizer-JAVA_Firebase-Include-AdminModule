package com.example.bph_esports.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bph_esports.R;
import com.example.bph_esports.match_room;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class team_list_adapter extends RecyclerView.Adapter<team_list_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<team_list> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public team_list_adapter(ArrayList<team_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public team_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new team_list_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.team_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull team_list_adapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        team_list courses = coursesArrayList.get(position);
        String types = courses.getType();
        holder.tname.setText(courses.getName());
        holder.sl.setText(String.valueOf(holder.getAdapterPosition()+1));
        holder.kill.setText(String.valueOf(courses.getKill()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialogs = ProgressDialog.show(context, "Your Internet Is Slow", "Loading. Please wait...", true);
                if(types.equals("SOLO")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(courses.getName());
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View customLayout = inflater.inflate(R.layout.regi, null);
                    builder.setView(customLayout);
                    Button svc = customLayout.findViewById(R.id.svc);
                    AlertDialog dialog = builder.create();
                    dialogs.dismiss();
                    dialog.show();
                    svc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                else if(types.equals("DUO")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(courses.getName());
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View customLayout = inflater.inflate(R.layout.regi2, null);
                    builder.setView(customLayout);
                    TextView editText = customLayout.findViewById(R.id.editText);
                    TextView editText2 = customLayout.findViewById(R.id.editText2);
                    FirebaseFirestore.getInstance().collection("MatchRegister").document(types).collection("MatchID").document(courses.getMatchid()).collection("PlayerId").document(courses.uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot d) {
                            if(d.exists()){
                                editText.setText(d.getString("Player1Id"));
                                editText2.setText(d.getString("Player2Id"));
                                dialogs.dismiss();
                            }

                        }
                    });
                    Button svc = customLayout.findViewById(R.id.svc);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    svc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                else if(types.equals("SQUAD")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(courses.getName());
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View customLayout = inflater.inflate(R.layout.regi4, null);
                    builder.setView(customLayout);
                    TextView editText = customLayout.findViewById(R.id.editText);
                    TextView editText2 = customLayout.findViewById(R.id.editText2);
                    TextView editText3 = customLayout.findViewById(R.id.editText3);
                    TextView editText4 = customLayout.findViewById(R.id.editText4);
                    FirebaseFirestore.getInstance().collection("MatchRegister").document(types).collection("MatchID").document(courses.getMatchid()).collection("PlayerId").document(courses.uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot d) {
                            if(d.exists()){
                                editText.setText(d.getString("Player1Id"));
                                editText2.setText(d.getString("Player2Id"));
                                editText3.setText(d.getString("Player3Id"));
                                editText4.setText(d.getString("Player4Id"));
                                dialogs.dismiss();
                            }

                        }
                    });
                    Button svc = customLayout.findViewById(R.id.svc);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    svc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }

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
        private final TextView sl, tname, kill;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            sl = itemView.findViewById(R.id.sl);
            tname = itemView.findViewById(R.id.tname);
            kill = itemView.findViewById(R.id.kill);

            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
