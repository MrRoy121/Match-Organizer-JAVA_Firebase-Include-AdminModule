package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.helper.match_list;
import com.example.admin.helper.match_list_adapter;
import com.example.admin.helper.withdraw_list_adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class withdraw extends AppCompatActivity {

    FirebaseFirestore fs;


    private RecyclerView courseVR;
    private ArrayList<match_list> coursesrrayList;
    private withdraw_list_adapter courseRVdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        fs = FirebaseFirestore.getInstance();

        courseVR = findViewById(R.id.withdrawview);
        coursesrrayList = new ArrayList<>();
        courseVR.setHasFixedSize(true);
        courseVR.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new withdraw_list_adapter(coursesrrayList, this);
        ArrayList<match_list> list = new ArrayList<>();
        fs.collection("WithdrawRequest").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d : q.getDocuments()){
                        fs.collection("users").document(d.getString("UserId")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                list.add(new match_list(String.valueOf(d.get("Amount")), "d.getId()",  "null", d.getString("UserId"), documentSnapshot.getString("UPI"), d.getId(), "null",  "null", "null", "null",  "null", "null"));
                                courseRVdapter= new withdraw_list_adapter(list, withdraw.this);
                                courseVR.setAdapter(courseRVdapter);
                            }
                        });
                    }
                    courseRVdapter.notifyDataSetChanged();
                }
            }
        });
    }
}