package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.admin.helper.match_list;
import com.example.admin.helper.match_list_adapter;
import com.example.admin.helper.quiz_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class quiz extends AppCompatActivity {


    Button add;
    FirebaseFirestore fs;

    String sub;

    private RecyclerView courseVR;
    private ArrayList<match_list> coursesrrayList;
    private quiz_list_adapter courseRVdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        add = findViewById(R.id.add);
        fs = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        if(!i.getStringExtra("ID").isEmpty()){
            sub = i.getStringExtra("ID");
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ix = new Intent(quiz.this, addquiz.class);
                ix.putExtra("ID", sub);
                startActivity(ix);
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        courseVR = findViewById(R.id.quizview);
        coursesrrayList = new ArrayList<>();
        courseVR.setHasFixedSize(true);
        courseVR.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new quiz_list_adapter(coursesrrayList, this);

        ArrayList<match_list> list = new ArrayList<>();
        fs.collection("Quiz").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        list.add(new match_list(d.getId(), String.valueOf(d.get("Fees")), String.valueOf(d.get("Length")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Date")), String.valueOf(d.get("Time")), String.valueOf(d.get("Question")), null,  sub,null));
                        courseRVdapter= new quiz_list_adapter(list, quiz.this);
                        courseVR.setAdapter(courseRVdapter);
                    }
                    courseRVdapter.notifyDataSetChanged();
                }
            }
        });
    }
}