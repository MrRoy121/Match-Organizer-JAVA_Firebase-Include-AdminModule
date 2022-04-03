package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.helper.ma_list_adapter;
import com.example.admin.helper.match_list;
import com.example.admin.helper.q_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class quiz_list extends AppCompatActivity {

    String sub, selectedItem;
    List<String> ts, st;
    FirebaseFirestore fs;
    private ListView l, listView1;
    Button done;
    ArrayAdapter<String> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        Intent i = getIntent();
        if(!i.getStringExtra("ID").isEmpty()){
            sub = i.getStringExtra("ID");
        }


        fs = FirebaseFirestore.getInstance();

        l = findViewById(R.id.qview);


        ts = new ArrayList<String>();

        fs.collection("QuizSubmissions").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        ts.add(d.getId());
                    }
                    listt();
                }
            }
        });
    }
    public void listt(){


        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ts);
        l.setAdapter(arr);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);

                st = new ArrayList<String>();
                fs.collection("QuizSubmissions").document(sub).collection("ExamId").document(selectedItem).collection("PlayerId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                st.add(d.getId());
                            }
                            sss();
                        }
                    }
                });
            }
        });
    }
    public void sss(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(quiz_list.this);
        View rowList = getLayoutInflater().inflate(R.layout.row, null);
        listView1 = rowList.findViewById(R.id.listView);
        done = rowList.findViewById(R.id.btton);
        adapter1 = new ArrayAdapter<>(quiz_list.this, android.R.layout.simple_list_item_1, st);
        listView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        alertDialog.setView(rowList);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                Intent ix = new Intent(quiz_list.this, quiz_result.class);
                ix.putExtra("ID", sub);
                ix.putExtra("ID1", selectedItem);
                ix.putExtra("ID2", st.get(i));
                startActivity(ix);
                finish();
            }
        });
    }
}
