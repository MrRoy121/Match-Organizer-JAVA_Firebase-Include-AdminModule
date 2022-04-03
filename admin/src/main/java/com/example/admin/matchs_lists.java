package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.admin.helper.ma_list_adapter;
import com.example.admin.helper.match_list;
import com.example.admin.helper.match_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class matchs_lists extends AppCompatActivity {

    FirebaseFirestore fs;

    private RecyclerView courseVR;
    private ArrayList<match_list> coursesrrayList;
    private ma_list_adapter courseRVdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchs_lists);

        fs = FirebaseFirestore.getInstance();

        courseVR = findViewById(R.id.maview);
        coursesrrayList = new ArrayList<>();
        courseVR.setHasFixedSize(true);
        courseVR.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new ma_list_adapter(coursesrrayList, this);

        ArrayList<match_list> list = new ArrayList<>();
        fs.collection("Daily Matches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        list.add(new match_list(String.valueOf(d.get("Region")), String.valueOf(d.get("Fees")), String.valueOf(d.get("Per Kill")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")),  String.valueOf(d.get("Third")),String.valueOf(d.get("Max Member")), String.valueOf(d.get("Time")), String.valueOf(d.get("Type")), String.valueOf(d.get("Perspective")),  String.valueOf(d.get("Map")), d.getId()));
                        courseRVdapter= new ma_list_adapter(list, matchs_lists.this);
                        courseVR.setAdapter(courseRVdapter);
                    }
                    courseRVdapter.notifyDataSetChanged();
                }
            }
        });
    }
}