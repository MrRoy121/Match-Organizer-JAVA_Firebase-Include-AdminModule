package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class matchresults extends AppCompatActivity {


    FirebaseFirestore fs;
    ListView listView;
    ArrayList<String> listItem;
    String matchid, type, p1, p2, p3, p4;
    Integer kills, amount, perkil, balance, wining;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchresults);

        fs = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.resultrv);
        btn = findViewById(R.id.btn);


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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(type == null)){
                    fs.collection("MatchRegister").document(type).collection("MatchID").document(matchid).collection("PlayerId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(matchresults.this, "Please Finish All Users First", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else{
                    fs.collection("Daily Matches").document(matchid).delete();
                    DocumentReference d = fs.collection("Match").document("Pending");
                    DocumentReference d2 = fs.collection("Match").document("Live");
                    DocumentReference d3 = fs.collection("Match").document("Upcomming");
                    d.collection("MatchID").document(matchid).delete();
                    d2.collection("MatchID").document(matchid).delete();
                    d3.collection("MatchID").document(matchid).delete();

                    fs.collection("MatchRegister").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(!queryDocumentSnapshots.isEmpty()){
                                for(DocumentSnapshot dsds : queryDocumentSnapshots){
                                    DocumentReference ss = fs.collection("MatchRegister").document(dsds.getId());
                                    ss.collection("MatchID").document(matchid).delete();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            }
                        }
                    });
                }
            }
        });

        listItem = new ArrayList<>();
        Intent i = getIntent();
        if(!i.getStringExtra("ID").isEmpty()){
            matchid = i.getStringExtra("ID");
            fs.collection("Match").document("Pending").collection("MatchID").document(matchid).collection("PlayerId").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot q) {
                    if(!q.isEmpty()){
                        for (DocumentSnapshot d : q){
                            type = d.getString("Type");
                            listItem.add(d.getId());
                        }

                        ssdd();
                    }
                }
            });
        }
    }

    public void ssdd(){
        fs.collection("MatchRegister").document(type).collection("MatchID").document(matchid).collection("PlayerId").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                String tname = " ";
                if(!q.isEmpty()){
                    for (DocumentSnapshot ds : q){
                        tname = ds.getString("TeamName");
                        p1 = ds.getString("Player1Id");
                        p2 = ds.getString("Player2Id");
                        p3 = ds.getString("Player3Id");
                        p4 = ds.getString("Player4Id");
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(matchresults.this, android.R.layout.simple_list_item_1, listItem);
                    listView.setAdapter(adapter);
                    String finalTname = tname;
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            String value=adapter.getItem(position);
                            fs.collection("users").document(value).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot da) {
                                    if(type.equals("SOLO")){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(matchresults.this);
                                        builder.setTitle("Name");
                                        final View customLayout = getLayoutInflater().inflate(R.layout.regi_alert, null);
                                        builder.setView(customLayout);
                                        TextView editText = customLayout.findViewById(R.id.editText);
                                        editText.setText(da.getString("FullName"));
                                        EditText kill = customLayout.findViewById(R.id.kill);
                                        Button svc = customLayout.findViewById(R.id.svc);

                                        svc.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                ProgressDialog dialog = ProgressDialog.show(matchresults.this, "Data Is Adding To Database", "Loading. Please wait...", true);
                                                if (!(kill.getText().toString().length() == 0)) {
                                                    Map<String, Object> match;
                                                    match = new HashMap<>();
                                                    fs.collection("Daily Matches").document(matchid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot dc) {
                                                            match.put("Region", dc.getString("Region"));
                                                            match.put("RoomID", dc.getString("RoomID"));
                                                            match.put("RoomPass", dc.getString("RoomPass"));
                                                            match.put("Time", dc.getString("Time"));
                                                            match.put("Perspective", dc.getString("Perspective"));
                                                            match.put("Type", dc.getString("Type"));
                                                            match.put("Map", dc.getString("Map"));
                                                            match.put("Per Kill", dc.getString("Per Kill"));
                                                            match.put("Fees", dc.getString("Fees"));
                                                            match.put("Max Member", dc.getString("Max Member"));

                                                            perkil = Integer.parseInt(dc.getString("Per Kill"));

                                                            Map<String, Object> noll;
                                                            noll = new HashMap<>();
                                                            noll.put("null", null);

                                                            Map<String, Object> ddd;
                                                            ddd = new HashMap<>();
                                                            ddd.put("TeamName", finalTname);
                                                            ddd.put("UserName", editText.getText().toString());
                                                            ddd.put("Kills", kill.getText().toString());
                                                            DocumentReference dx1 = fs.collection("MatchHistory").document(type);
                                                            dx1.set(noll);
                                                            DocumentReference dx = dx1.collection("MatchID").document(matchid);
                                                            dx.set(match);
                                                            dx.collection("PlayerId").document(value).set(ddd);

                                                            Map<String, Object> baa;
                                                            baa = new HashMap<>();
                                                            fs.collection("Balance").document(value).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot dos) {
                                                                    Log.e("bal", String.valueOf(dos.getLong("Balance").intValue()));
                                                                    balance = dos.getLong("Balance").intValue();
                                                                    wining = dos.getLong("Winings").intValue();
                                                                    kills = Integer.parseInt(kill.getText().toString());
                                                                    amount = kills * perkil;
                                                                    wining = wining + amount;

                                                                    Log.e(String.valueOf(wining),"balance");
                                                                    baa.put("Balance", balance);
                                                                    baa.put("Winings", wining);
                                                                    fs.collection("Balance").document(value).set(baa);
                                                                    DocumentReference dg = fs.collection("MatchRegister").document(type).collection("MatchID").document(matchid);
                                                                    dg.collection("PlayerId").document(value).delete();
                                                                    DocumentReference dxs = fs.collection("Match").document("Pending").collection("MatchID").document(matchid);
                                                                    dxs.collection("PlayerId").document(value).delete();
                                                                    startActivity(getIntent());
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            Map<String, Object> fe;
                                                            fe = new HashMap<>();
                                                            fs.collection("Leaderboard").document(value).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot dot) {
                                                                    if(dot.exists()){
                                                                        int a = dot.getLong("TotalWinings").intValue();
                                                                        int b = dot.getLong("Matches").intValue();
                                                                        kills = Integer.parseInt(kill.getText().toString());
                                                                        amount = kills * perkil;
                                                                        a = a + amount;
                                                                        b = b + 1;

                                                                        fe.put("TotalWinings", a);
                                                                        fe.put("Matches", b);
                                                                        fs.collection("Leaderboard").document(value).set(fe);

                                                                    }else{
                                                                        int a = 0;
                                                                        int b = 0;
                                                                        kills = Integer.parseInt(kill.getText().toString());
                                                                        amount = kills * perkil;
                                                                        a = a + amount;
                                                                        b = b + 1;
                                                                        fe.put("TotalWinings", a);
                                                                        fe.put("Matches", b);
                                                                        fs.collection("Leaderboard").document(value).set(fe);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });

                                                } else {

                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }

                                    if(type.equals("DUO")){

                                        AlertDialog.Builder builder = new AlertDialog.Builder(matchresults.this);
                                        builder.setTitle("Name");
                                        final View customLayout = getLayoutInflater().inflate(R.layout.regi_alert, null);
                                        builder.setView(customLayout);
                                        TextView editText = customLayout.findViewById(R.id.editText);
                                        editText.setText(da.getString("FullName"));
                                        EditText kill = customLayout.findViewById(R.id.kill);
                                        Button svc = customLayout.findViewById(R.id.svc);

                                        svc.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                ProgressDialog dialog = ProgressDialog.show(matchresults.this, "Data Is Adding To Database", "Loading. Please wait...", true);
                                                if (!(kill.getText().toString().length() == 0)) {
                                                    Map<String, Object> match;
                                                    match = new HashMap<>();
                                                    fs.collection("Daily Matches").document(matchid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot dc) {
                                                            match.put("Region", dc.getString("Region"));
                                                            match.put("RoomID", dc.getString("RoomID"));
                                                            match.put("RoomPass", dc.getString("RoomPass"));
                                                            match.put("Time", dc.getString("Time"));
                                                            match.put("Perspective", dc.getString("Perspective"));
                                                            match.put("Type", dc.getString("Type"));
                                                            match.put("Map", dc.getString("Map"));
                                                            match.put("Per Kill", dc.getString("Per Kill"));
                                                            match.put("Fees", dc.getString("Fees"));
                                                            match.put("Max Member", dc.getString("Max Member"));

                                                            perkil = Integer.parseInt(dc.getString("Per Kill"));

                                                            Map<String, Object> noll;
                                                            noll = new HashMap<>();
                                                            noll.put("null", null);

                                                            Map<String, Object> ddd;
                                                            ddd = new HashMap<>();
                                                            ddd.put("TeamName", finalTname);
                                                            ddd.put("UserName", editText.getText().toString());
                                                            ddd.put("Kills", kill.getText().toString());
                                                            ddd.put("Player1Id", p1);
                                                            ddd.put("Player2Id", p2);
                                                            DocumentReference dx1 = fs.collection("MatchHistory").document(type);
                                                            dx1.set(noll);
                                                            DocumentReference dx = dx1.collection("MatchID").document(matchid);
                                                            dx.set(match);
                                                            dx.collection("PlayerId").document(value).set(ddd);

                                                            Map<String, Object> baa;
                                                            baa = new HashMap<>();
                                                            fs.collection("Balance").document(value).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot dos) {

                                                                    balance = dos.getLong("Balance").intValue();
                                                                    wining = dos.getLong("Winings").intValue();
                                                                    kills = Integer.parseInt(kill.getText().toString());
                                                                    amount = kills * perkil;
                                                                    wining = wining + amount;


                                                                    Log.e(String.valueOf(wining),"balance");
                                                                    baa.put("Balance", balance);
                                                                    baa.put("Winings", wining);
                                                                    fs.collection("Balance").document(value).set(baa);
                                                                    DocumentReference dg = fs.collection("MatchRegister").document(type).collection("MatchID").document(matchid);
                                                                    dg.collection("PlayerId").document(value).delete();
                                                                    DocumentReference dxs = fs.collection("Match").document("Pending").collection("MatchID").document(matchid);
                                                                    dxs.collection("PlayerId").document(value).delete();
                                                                    startActivity(getIntent());
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            Map<String, Object> fe;
                                                            fe = new HashMap<>();
                                                            fs.collection("Leaderboard").document(value).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot dot) {
                                                                    if(dot.exists()){
                                                                        int a = dot.getLong("TotalWinings").intValue();
                                                                        int b = dot.getLong("Matches").intValue();
                                                                        kills = Integer.parseInt(kill.getText().toString());
                                                                        amount = kills * perkil;
                                                                        a = a + amount;
                                                                        b = b + 1;

                                                                        fe.put("TotalWinings", a);
                                                                        fe.put("Matches", b);
                                                                        fs.collection("Leaderboard").document(value).set(fe);

                                                                    }else{
                                                                        int a = 0;
                                                                        int b = 0;
                                                                        kills = Integer.parseInt(kill.getText().toString());
                                                                        amount = kills * perkil;
                                                                        a = a + amount;
                                                                        b = b + 1;
                                                                        fe.put("TotalWinings", a);
                                                                        fe.put("Matches", b);
                                                                        fs.collection("Leaderboard").document(value).set(fe);
                                                                    }
                                                                }
                                                            });


                                                        }
                                                    });

                                                } else {

                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }

                                    if(type.equals("SQUAD")){

                                        AlertDialog.Builder builder = new AlertDialog.Builder(matchresults.this);
                                        builder.setTitle("Name");
                                        final View customLayout = getLayoutInflater().inflate(R.layout.regi_alert, null);
                                        builder.setView(customLayout);
                                        TextView editText = customLayout.findViewById(R.id.editText);
                                        editText.setText(da.getString("FullName"));
                                        EditText kill = customLayout.findViewById(R.id.kill);
                                        Button svc = customLayout.findViewById(R.id.svc);

                                        svc.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                ProgressDialog dialog = ProgressDialog.show(matchresults.this, "Data Is Adding To Database", "Loading. Please wait...", true);
                                                if (!(kill.getText().toString().length() == 0)) {
                                                    Map<String, Object> match;
                                                    match = new HashMap<>();
                                                    fs.collection("Daily Matches").document(matchid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot dc) {
                                                            match.put("Region", dc.getString("Region"));
                                                            match.put("RoomID", dc.getString("RoomID"));
                                                            match.put("RoomPass", dc.getString("RoomPass"));
                                                            match.put("Time", dc.getString("Time"));
                                                            match.put("Perspective", dc.getString("Perspective"));
                                                            match.put("Type", dc.getString("Type"));
                                                            match.put("Map", dc.getString("Map"));
                                                            match.put("Per Kill", dc.getString("Per Kill"));
                                                            match.put("Fees", dc.getString("Fees"));
                                                            match.put("Max Member", dc.getString("Max Member"));

                                                            perkil = Integer.parseInt(dc.getString("Per Kill"));

                                                            Map<String, Object> noll;
                                                            noll = new HashMap<>();
                                                            noll.put("null", null);

                                                            Map<String, Object> ddd;
                                                            ddd = new HashMap<>();
                                                            ddd.put("TeamName", finalTname);
                                                            ddd.put("UserName", editText.getText().toString());
                                                            ddd.put("Kills", kill.getText().toString());
                                                            ddd.put("Player1Id", p1);
                                                            ddd.put("Player2Id", p2);
                                                            ddd.put("Player3Id", p3);
                                                            ddd.put("Player4Id", p4);
                                                            DocumentReference dx1 = fs.collection("MatchHistory").document(type);
                                                            dx1.set(noll);
                                                            DocumentReference dx = dx1.collection("MatchID").document(matchid);
                                                            dx.set(match);
                                                            dx.collection("PlayerId").document(value).set(ddd);

                                                            Map<String, Object> baa;
                                                            baa = new HashMap<>();
                                                            fs.collection("Balance").document(value).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot dos) {

                                                                    balance = dos.getLong("Balance").intValue();
                                                                    wining = dos.getLong("Winings").intValue();
                                                                    kills = Integer.parseInt(kill.getText().toString());
                                                                    amount = kills * perkil;
                                                                    wining = wining + amount;


                                                                    Log.e(String.valueOf(wining),"balance");
                                                                    baa.put("Balance", balance);
                                                                    baa.put("Winings", wining);
                                                                    fs.collection("Balance").document(value).set(baa);
                                                                    DocumentReference dg = fs.collection("MatchRegister").document(type).collection("MatchID").document(matchid);
                                                                    dg.collection("PlayerId").document(value).delete();
                                                                    DocumentReference dxs = fs.collection("Match").document("Pending").collection("MatchID").document(matchid);
                                                                    dxs.collection("PlayerId").document(value).delete();
                                                                    startActivity(getIntent());
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            Map<String, Object> fe;
                                                            fe = new HashMap<>();
                                                            fs.collection("Leaderboard").document(value).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot dot) {
                                                                    if(dot.exists()){
                                                                        int a = dot.getLong("TotalWinings").intValue();
                                                                        int b = dot.getLong("Matches").intValue();
                                                                        kills = Integer.parseInt(kill.getText().toString());
                                                                        amount = kills * perkil;
                                                                        a = a + amount;
                                                                        b = b + 1;

                                                                        fe.put("TotalWinings", a);
                                                                        fe.put("Matches", b);
                                                                        fs.collection("Leaderboard").document(value).set(fe);

                                                                    }else{
                                                                        int a = 0;
                                                                        int b = 0;
                                                                        kills = Integer.parseInt(kill.getText().toString());
                                                                        amount = kills * perkil;
                                                                        a = a + amount;
                                                                        b = b + 1;
                                                                        fe.put("TotalWinings", a);
                                                                        fe.put("Matches", b);
                                                                        fs.collection("Leaderboard").document(value).set(fe);
                                                                    }
                                                                }
                                                            });


                                                        }
                                                    });

                                                } else {

                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}