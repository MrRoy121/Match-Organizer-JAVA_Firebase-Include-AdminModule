package com.example.bph_esports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bph_esports.helper.match_list;
import com.example.bph_esports.helper.match_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class tournament extends AppCompatActivity {

    FirebaseFirestore fs;
    TextView datex, lastx, namex, feesx, perkx, fstx, scndx, thrdx, maxx,mapx, typex, persp;
    String dates, lasts, names, feess, perks, fsts, scnds, thrds, maxs, types, pers, map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        fs = FirebaseFirestore.getInstance();
        feesx = findViewById(R.id.fees);
        perkx = findViewById(R.id.perkill);
        fstx = findViewById(R.id.fst);
        scndx = findViewById(R.id.scnd);
        thrdx = findViewById(R.id.thrd);
        maxx = findViewById(R.id.max);
        typex = findViewById(R.id.type);
        persp = findViewById(R.id.perspect);
        namex = findViewById(R.id.name);
        datex = findViewById(R.id.date);
        lastx = findViewById(R.id.last);
        mapx = findViewById(R.id.map);



        fs.collection("Tournament").document("XYZ").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                if(d.exists()){
                    namex.setText(d.getString("Name"));
                    fstx.setText(d.getString("First"));
                    scndx.setText(d.getString("Second"));
                    thrdx.setText(d.getString("Third"));
                    lastx.setText(d.getString("Last Date"));
                    datex.setText(d.getString("Date"));
                    maxx.setText(d.getString("Max Member"));
                    perkx.setText(d.getString("Per Kill"));
                    feesx.setText(d.getString("Fees"));

                    typex.setText(d.getString("Type"));
                    persp.setText(d.getString("Perspective"));
                    mapx.setText(d.getString("Map"));

                }
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnavigation);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.match) {
                    Intent i = new Intent(tournament.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }else if (item.getItemId() == R.id.lead) {
                    Intent i = new Intent(tournament.this, leaderboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }else if (item.getItemId() == R.id.mymatch) {
                    Intent i = new Intent(tournament.this, my_matches.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }
                return false;
            }
        });

    }
}