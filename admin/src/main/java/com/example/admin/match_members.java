package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.helper.mamem_list_adapter;
import com.example.admin.helper.match_list;
import com.example.admin.helper.match_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class match_members extends AppCompatActivity {

    String region, fees, per, pers, time, fst, snd, thd, max, type, uid, map;


    FirebaseFirestore fs;
    private RecyclerView courseVR;
    private ArrayList<match_list> coursesrrayList;
    private mamem_list_adapter courseRVdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_members);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            region = bundle.getString("1");
            fees = bundle.getString("2");
            per = bundle.getString("3");
            pers = bundle.getString("4");
            time = bundle.getString("5");
            fst = bundle.getString("6");
            snd = bundle.getString("7");
            thd = bundle.getString("71");
            max = bundle.getString("8");
            type = bundle.getString("9");
            uid = bundle.getString("10");
            map = bundle.getString("11");
        }

        typexx();

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fs.collection("RoomDetails").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot d) {
                        if(d.exists()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(match_members.this);
                            builder.setTitle("Match ID");
                            final View customLayout = getLayoutInflater().inflate(R.layout.room_alert, null);
                            builder.setView(customLayout);
                            TextView Text = customLayout.findViewById(R.id.mid);
                            EditText editText = customLayout.findViewById(R.id.editText);
                            EditText editText1 = customLayout.findViewById(R.id.editText2);
                            Text.setText(uid);
                            editText.setText(d.getString("RoomID"));
                            editText1.setText(d.getString("RoomPass"));
                            Button svc = customLayout.findViewById(R.id.svc);

                            AlertDialog dialog = builder.create();
                            dialog.show();

                            svc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Map<String, Object> s = new HashMap();
                                    s.put("RoomID",editText.getText().toString());
                                    s.put("RoomPass",editText1.getText().toString());
                                    fs.collection("RoomDetails").document(uid).set(s);
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(match_members.this);
                            builder.setTitle("Match ID");
                            final View customLayout = getLayoutInflater().inflate(R.layout.room_alert, null);
                            builder.setView(customLayout);
                            TextView Text = customLayout.findViewById(R.id.mid);
                            EditText editText = customLayout.findViewById(R.id.editText);
                            EditText editText1 = customLayout.findViewById(R.id.editText2);
                            Text.setText(uid);
                            Button svc = customLayout.findViewById(R.id.svc);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                            svc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!(editText.getText().length() == 0) && !(editText1.getText().length() == 0)){
                                        Map<String, Object> s = new HashMap();
                                        s.put("RoomID",editText.getText().toString());
                                        s.put("RoomPass",editText1.getText().toString());
                                        fs.collection("RoomDetails").document(uid).set(s);
                                        dialog.dismiss();
                                    }else{
                                        Toast.makeText(match_members.this, "All Fields Are Requied", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });
    }

    public void typexx(){

        fs = FirebaseFirestore.getInstance();
        courseVR = findViewById(R.id.mamemview);
        coursesrrayList = new ArrayList<>();
        courseVR.setHasFixedSize(true);
        courseVR.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new mamem_list_adapter(coursesrrayList, this);

        ArrayList<match_list> list = new ArrayList<>();

        if(type.equals("SOLO")){
            fs.collection("MatchRegister").document(type).collection("MatchID").document(uid).collection("PlayerId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            fs.collection("users").document(d.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    list.add(new match_list(documentSnapshot.getString("UserName"), "null", "null", d.getId(), String.valueOf(d.get("PlayerId")), "null", "null", "null",  "null", "null",  "null", "null"));
                                    courseRVdapter= new mamem_list_adapter(list, match_members.this);
                                    courseVR.setAdapter(courseRVdapter);
                                }
                            });
                        }
                        courseRVdapter.notifyDataSetChanged();
                    }
                }
            });
        }else if(type.equals("DUO")){
            fs.collection("MatchRegister").document(type).collection("MatchID").document(uid).collection("PlayerId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            fs.collection("users").document(d.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    list.add(new match_list(documentSnapshot.getString("UserName"), "null", "null", d.getId(), String.valueOf(d.get("Player1Id")),  "null", String.valueOf(d.get("Player2Id")) ,"null", "null",  "null",  "null", "null"));
                                    courseRVdapter= new mamem_list_adapter(list, match_members.this);
                                    courseVR.setAdapter(courseRVdapter);
                                }
                            });
                        }
                        courseRVdapter.notifyDataSetChanged();
                    }
                }
            });
        }else if(type.equals("SQUAD")){

            fs.collection("MatchRegister").document(type).collection("MatchID").document(uid).collection("PlayerId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            fs.collection("users").document(d.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    list.add(new match_list(documentSnapshot.getString("UserName"), "null", type, d.getId(), String.valueOf(d.get("Player1Id")),  "null", String.valueOf(d.get("Player2Id")) ,  "null", String.valueOf(d.get("Player3Id")),  "null", String.valueOf(d.get("Player4Id")), "null"));
                                    courseRVdapter= new mamem_list_adapter(list, match_members.this);
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
}