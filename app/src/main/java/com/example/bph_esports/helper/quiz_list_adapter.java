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
import com.example.bph_esports.match_room;
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

public class quiz_list_adapter extends RecyclerView.Adapter<quiz_list_adapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<quiz_list> coursesArrayList;
    private Context context;
    String uid;

    // creating constructor for our adapter class
    public quiz_list_adapter(ArrayList<quiz_list> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.quiz_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        quiz_list courses = coursesArrayList.get(position);
        holder.eid.setText(courses.getUid());
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
                FirebaseFirestore fs = FirebaseFirestore.getInstance();
                FirebaseAuth fa = FirebaseAuth.getInstance();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("You Pay And Start The Exam?")
                        .setCancelable(false)
                        .setPositiveButton("Join Exam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ProgressDialog dialogs = ProgressDialog.show(context, "Joining The Exam", "Loading. Please wait...", true);
                                fs.collection("users").whereEqualTo("UserName", fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot q) {
                                        if(!q.isEmpty()){
                                            for(DocumentSnapshot d: q){
                                                uid = d.getId();
                                            }
                                            Map<String,Object> noll = new HashMap();
                                            noll.put("null", null);
                                            DocumentReference d = fs.collection("QuizRegister").document(courses.getUid());
                                            d.set(noll);
                                            d.collection("UserId").document(uid).set(noll);
                                            Log.e("TAG", uid);
                                            fs.collection("Balance").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot d) {
                                                    if(d.exists()){
                                                        int amount = Integer.parseInt(String.valueOf(d.get("Balance")));
                                                        int wamount = Integer.parseInt(String.valueOf(d.get("Winings")));
                                                        String feess = courses.getFee();
                                                        SimpleDateFormat sdf = new SimpleDateFormat("KK:mma,dd-MM-yy");
                                                        String cdt = sdf.format(new Date());
                                                        if(amount>= Integer.parseInt(feess)){
                                                            amount = amount - Integer.parseInt(feess);
                                                            Map<String, Object> bal;
                                                            bal = new HashMap<>();
                                                            bal.put("Balance", amount);
                                                            bal.put("Winings", wamount);
                                                            Map<String, Object> his;
                                                            his = new HashMap<>();
                                                            his.put("Amount", feess);
                                                            his.put("Stats", "Paid Quiz Fee");
                                                            his.put("DateTime", cdt);
                                                            Map<String, Object> noll;
                                                            noll = new HashMap<>();
                                                            noll.put("null", null);
                                                            fs.collection("Balance").document(uid).set(bal);
                                                            DocumentReference ds = fs.collection("PaymentHistory").document(uid);
                                                            ds.set(noll);
                                                            ds.collection("DateTime").document(cdt).set(his);
                                                            dialogs.dismiss();
                                                            Intent i = new Intent(context, exam_room.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("ID", courses.getUid());
                                                            bundle.putString("1", courses.getRegion());
                                                            bundle.putString("2", courses.getFee());
                                                            bundle.putString("3", courses.getPer());
                                                            bundle.putString("4", courses.getFst());
                                                            bundle.putString("5", courses.getScnd());
                                                            bundle.putString("6", courses.getThrd());
                                                            bundle.putString("7", courses.getTime());
                                                            bundle.putString("8", courses.getType());
                                                            bundle.putString("9", courses.getMax());
                                                            i.putExtras(bundle);
                                                            context.startActivity(i);

                                                        }else{
                                                            dialogs.dismiss();
                                                            dialog.dismiss();
                                                            Toast.makeText(context, "Your Balance Is Low", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else{
                                                        dialogs.dismiss();
                                                        dialog.dismiss();
                                                        Toast.makeText(context, "Doesn't Exists", Toast.LENGTH_SHORT).show();
                                                    }}
                                            });

                                        }
                                    }
                                });
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

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
        private final TextView eid, num, date, time, fee, len, fst, snd, thd;
        private final CardView relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            eid = itemView.findViewById(R.id.sub);
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
