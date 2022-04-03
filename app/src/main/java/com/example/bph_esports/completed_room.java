package com.example.bph_esports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bph_esports.helper.Completed_adapter;
import com.example.bph_esports.helper.completed_list;
import com.example.bph_esports.helper.team_list;
import com.example.bph_esports.helper.team_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class completed_room extends AppCompatActivity {

    FirebaseFirestore fs;

    TextView type, date, ldate, max, fees, perspec, per, map;
    Button room, prize, team;
    FrameLayout rooml, prizel;
    LinearLayout teaml;
    String regions, feess, pers, perss, times, roomids, roompasss, maxs, types, uids, maps, matchid,uid;


    private RecyclerView bgmirv;
    private ArrayList<team_list> coursesrrayList;
    private team_list_adapter courseRVdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_room);


        fs = FirebaseFirestore.getInstance();
        type = findViewById(R.id.type);
        date = findViewById(R.id.date);
        ldate = findViewById(R.id.last);
        max = findViewById(R.id.max);
        fees = findViewById(R.id.fees);
        perspec = findViewById(R.id.perspect);
        per = findViewById(R.id.perkill);
        map = findViewById(R.id.map);
        bgmirv = findViewById(R.id.allteamrv);



        room = findViewById(R.id.room);
        rooml = findViewById(R.id.rooml);
        prize = findViewById(R.id.prize);
        prizel = findViewById(R.id.prizel);
        team = findViewById(R.id.team);
        teaml = findViewById(R.id.teaml);



        coursesrrayList = new ArrayList<>();
        bgmirv.setHasFixedSize(true);
        bgmirv.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new team_list_adapter(coursesrrayList, this);
        ArrayList<team_list> list = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            types = bundle.getString("1");
            matchid = bundle.getString("2");
            uid = bundle.getString("3");

            fs.collection("MatchHistory").document(types).collection("MatchID").document(matchid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot ds) {
                    if(ds.exists()){
                        regions = ds.getString("Region");
                        feess = ds.getString("Fees");
                        perss = ds.getString("Per Kill");
                        roomids = ds.getString("RoomID");
                        roompasss = ds.getString("RoomPass");
                        maxs = ds.getString("Max Member");
                        times = ds.getString("Time");
                        pers = ds.getString("Perspective");
                        maps = ds.getString("Map");


                        type.setText(types);
                        date.setText(times);
                        fees.setText(feess);
                        max.setText(maxs);
                        perspec.setText(perss);
                        per.setText(pers);
                        map.setText(maps);

                        fs.collection("MatchHistory").document(types).collection("MatchID").document(matchid).collection("PlayerId").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot dv) {
                                if(!dv.isEmpty()){
                                    for (QueryDocumentSnapshot d : dv) {
                                        list.add(new team_list(String.valueOf(d.get("TeamName")), types,d.getId(),ds.getId(), Integer.parseInt(d.getString("Kills"))));
                                        courseRVdapter= new team_list_adapter(list, completed_room.this);
                                        bgmirv.setAdapter(courseRVdapter);
                                    }
                                }
                            }
                        });
                    courseRVdapter.notifyDataSetChanged();

                    }
                }

            });
        }



        room.setOnClickListener(view -> {

            room.setClickable(false);
            prize.setClickable(true);
            team.setClickable(true);
            room.setBackgroundResource(R.color.black_700);
            prize.setBackgroundResource(R.color.teal_700);
            team.setBackgroundResource(R.color.teal_700);
            prizel.setVisibility(View.GONE);
            teaml.setVisibility(View.GONE);
            rooml.setVisibility(View.VISIBLE);

        });


        prize.setOnClickListener(view -> {

            prize.setClickable(false);
            room.setClickable(true);
            team.setClickable(true);
            prize.setBackgroundResource(R.color.black_700);
            room.setBackgroundResource(R.color.teal_700);
            team.setBackgroundResource(R.color.teal_700);
            prizel.setVisibility(View.VISIBLE);
            teaml.setVisibility(View.GONE);
            rooml.setVisibility(View.GONE);

        });

        team.setOnClickListener(view -> {

            room.setClickable(true);
            prize.setClickable(true);
            team.setClickable(false);
            team.setBackgroundResource(R.color.black_700);
            prize.setBackgroundResource(R.color.teal_700);
            room.setBackgroundResource(R.color.teal_700);
            prizel.setVisibility(View.GONE);
            teaml.setVisibility(View.VISIBLE);
            rooml.setVisibility(View.GONE);

        });

        fs.collection("Daily Matches").whereEqualTo("Time", times).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                        fs.collection("MatchRegister").document(types).collection("MatchID").document(ds.getId()).collection("PlayerId").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot dv) {
                                if(!dv.isEmpty()){
                                    for (QueryDocumentSnapshot d : dv) {
                                        list.add(new team_list(String.valueOf(d.get("TeamName")), types,d.getId(),ds.getId(),  0));
                                        courseRVdapter= new team_list_adapter(list, completed_room.this);
                                        bgmirv.setAdapter(courseRVdapter);
                                    }
                                }
                            }
                        });
                    }
                    courseRVdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
