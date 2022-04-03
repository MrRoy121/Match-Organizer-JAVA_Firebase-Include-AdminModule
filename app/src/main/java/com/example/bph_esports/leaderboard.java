package com.example.bph_esports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bph_esports.helper.leaderboard_adapter;
import com.example.bph_esports.helper.team_list;
import com.example.bph_esports.helper.team_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class leaderboard extends AppCompatActivity {

    FirebaseFirestore fs;
    private RecyclerView bgmirv;
    private ArrayList<team_list> coursesrrayList;
    private leaderboard_adapter courseRVdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        fs = FirebaseFirestore.getInstance();
        bgmirv = findViewById(R.id.allrv);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayu);
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


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnavigation);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tour) {
                    Intent i = new Intent(leaderboard.this, tournament.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return true;
                }else if (item.getItemId() == R.id.match) {
                    Intent i = new Intent(leaderboard.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return true;
                }else if (item.getItemId() == R.id.mymatch) {
                    Intent i = new Intent(leaderboard.this, my_matches.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return true;
                }
                return false;
            }
        });



        coursesrrayList = new ArrayList<>();
        bgmirv.setHasFixedSize(true);
        bgmirv.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new leaderboard_adapter(coursesrrayList, this);
        ArrayList<team_list> list = new ArrayList<>();
        fs.collection("Leaderboard").orderBy("TotalWinings", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot d : task.getResult().getDocuments()) {
                            list.add(new team_list(d.getId(), String.valueOf(d.get("TotalWinings")),String.valueOf(d.get("Matches")),null,  0));
                            courseRVdapter= new leaderboard_adapter(list, leaderboard.this);
                            bgmirv.setAdapter(courseRVdapter);
                    }
                    courseRVdapter.notifyDataSetChanged();
                }
            }
        });
    }
}