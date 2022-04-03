package com.example.bph_esports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bph_esports.helper.match_list;
import com.example.bph_esports.helper.match_list_adapter;
import com.example.bph_esports.helper.team_list;
import com.example.bph_esports.helper.team_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class match_room extends AppCompatActivity {

    FirebaseFirestore fs;
    FirebaseAuth fa;
    TextView type, date, ldate, max, fees, perspec, per, map, fst, snd, thd, roomid, roompass;
    Button room, prize, team, register, live;
    FrameLayout rooml, prizel;
    LinearLayout teaml;
    Integer amount, wamount;

    private RecyclerView bgmirv;
    private ArrayList<team_list> coursesrrayList;
    private team_list_adapter courseRVdapter;

    String regions, feess, pers, perss, times, fsts, snds, thds, maxs, types, uids, maps, matchid,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_room);


        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();
        type = findViewById(R.id.type);
        date = findViewById(R.id.date);
        ldate = findViewById(R.id.last);
        max = findViewById(R.id.max);
        fees = findViewById(R.id.fees);
        perspec = findViewById(R.id.perspect);
        per = findViewById(R.id.perkill);
        map = findViewById(R.id.map);
        bgmirv = findViewById(R.id.allteamrv);
        register = findViewById(R.id.register);
        live = findViewById(R.id.live);


        fst = findViewById(R.id.fst);
        snd = findViewById(R.id.snd);
        thd = findViewById(R.id.thd);
        roomid = findViewById(R.id.roomid);
        roompass = findViewById(R.id.roompass);

        room = findViewById(R.id.room);
        rooml = findViewById(R.id.rooml);
        prize = findViewById(R.id.prize);
        prizel = findViewById(R.id.prizel);
        team = findViewById(R.id.team);
        teaml = findViewById(R.id.teaml);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            regions = bundle.getString("1");
            feess = bundle.getString("2");
            pers = bundle.getString("3");
            perss = bundle.getString("4");
            times = bundle.getString("5");
            fsts = bundle.getString("6");
            snds = bundle.getString("7");
            thds = bundle.getString("71");
            maxs = bundle.getString("8");
            types = bundle.getString("9");
            uids = bundle.getString("10");
            maps = bundle.getString("11");

            type.setText(types);
            date.setText(times);
            fees.setText(feess);
            max.setText(maxs);
            perspec.setText(perss);
            per.setText(pers);
            map.setText(maps);
            fst.setText(fsts);
            snd.setText(snds);
            thd.setText(thds);
            roomid.setText("Hidden");
            roompass.setText("Hidden");
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


        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayou);
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


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert();
            }
        });
        fs.collection("users").whereEqualTo("UserName", fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                for(DocumentSnapshot d: q){
                    uid = d.getId();
                }
                rv();
                button();
            }
        });


    }

    public void alert(){
        if(types.equals("SOLO")){
            AlertDialog.Builder builder = new AlertDialog.Builder(match_room.this);
            builder.setTitle("Name");
            final View customLayout = getLayoutInflater().inflate(R.layout.regi_alert, null);
            builder.setView(customLayout);
            EditText editText = customLayout.findViewById(R.id.editText);
            Button svc = customLayout.findViewById(R.id.svc);

            AlertDialog dialog = builder.create();
            dialog.show();

            svc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressDialog dialog = ProgressDialog.show(match_room.this, "Data Is Adding To Database", "Loading. Please wait...", true);
                    if(!(editText.getText().toString().length() == 0)){
                        fs.collection("Balance").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot d) {
                                if(d.exists()){
                                    amount = Integer.parseInt(String.valueOf(d.get("Balance")));
                                    wamount = Integer.parseInt(String.valueOf(d.get("Winings")));
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
                                        his.put("Stats", "Paid Match Fee");
                                        his.put("DateTime", cdt);
                                        Map<String, Object> noll;
                                        noll = new HashMap<>();
                                        noll.put("null", null);
                                        fs.collection("Balance").document(uid).set(bal);
                                        DocumentReference ds = fs.collection("PaymentHistory").document(uid);
                                        ds.set(noll);
                                        ds.collection("DateTime").document(cdt).set(his);
                                        Map<String, Object> ddd;
                                        ddd = new HashMap<>();
                                        ddd.put("TeamName", editText.getText().toString());
                                        DocumentReference dx1 = fs.collection("MatchRegister").document("SOLO");
                                        dx1.set(noll);
                                        DocumentReference dx = dx1.collection("MatchID").document(matchid);
                                        dx.set(noll);
                                        dx.collection("PlayerId").document(uid).set(ddd);


                                        Map<String, Object> sss;
                                        sss = new HashMap<>();
                                        sss.put("Type", "SOLO");
                                        DocumentReference dxs = fs.collection("Match").document("Upcoming");
                                        dxs.set(noll);
                                        DocumentReference dxss = dxs.collection("MatchID").document(matchid);
                                        dxss.set(noll);
                                        dxss.collection("PlayerId").document(uid).set(sss);
                                        Toast.makeText(match_room.this, "Added", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
                                        dialog.dismiss();
                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(match_room.this, "Your Balance Is Low", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(match_room.this, "Doesn't Exists", Toast.LENGTH_SHORT).show();
                                }}
                        });
                    }else{
                        dialog.dismiss();
                        Toast.makeText(match_room.this, "Give Player ID", Toast.LENGTH_SHORT).show();}

                }
            });
        }
        else if(types.equals("DUO")){
            AlertDialog.Builder builder = new AlertDialog.Builder(match_room.this);
            builder.setTitle("Name");
            final View customLayout = getLayoutInflater().inflate(R.layout.regi_alert_duo, null);
            builder.setView(customLayout);
            EditText tname = customLayout.findViewById(R.id.tnamed);
            EditText editText = customLayout.findViewById(R.id.editText);
            EditText editText1 = customLayout.findViewById(R.id.editText2);
            Button svc = customLayout.findViewById(R.id.svc);

            AlertDialog dialog = builder.create();
            dialog.show();
            svc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressDialog dialog = ProgressDialog.show(match_room.this, "Data Is Adding To Database", "Loading. Please wait...", true);
                    if(!(editText.getText().toString().length() == 0 && editText1.getText().toString().length() == 0  && tname.getText().toString().length() == 0 )){
                        fs.collection("Balance").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot d) {
                                if(d.exists()){
                                    amount = Integer.parseInt(String.valueOf(d.get("Balance")));
                                    wamount = Integer.parseInt(String.valueOf(d.get("Winings")));
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
                                        his.put("Stats", "Paid Match Fee");
                                        his.put("DateTime", cdt);
                                        Map<String, Object> noll;
                                        noll = new HashMap<>();
                                        noll.put("null", null);
                                        fs.collection("Balance").document(uid).set(bal);
                                        DocumentReference ds = fs.collection("PaymentHistory").document(uid);
                                        ds.set(noll);
                                        ds.collection("DateTime").document(cdt).set(his);
                                        Map<String, Object> ddd;
                                        ddd = new HashMap<>();
                                        ddd.put("TeamName", tname.getText().toString());
                                        ddd.put("Player1Id", editText.getText().toString());
                                        ddd.put("Player2Id", editText1.getText().toString());
                                        DocumentReference dx1 = fs.collection("MatchRegister").document("DUO");
                                        dx1.set(noll);
                                        DocumentReference dx = dx1.collection("MatchID").document(matchid);
                                        dx.set(noll);
                                        dx.collection("PlayerId").document(uid).set(ddd);


                                        Map<String, Object> sss;
                                        sss = new HashMap<>();
                                        sss.put("Type", "DUO");

                                        DocumentReference dxs = fs.collection("Match").document("Upcoming");
                                        dxs.set(noll);
                                        DocumentReference dxss = dxs.collection("MatchID").document(matchid);
                                        dxss.set(noll);
                                        dxss.collection("PlayerId").document(uid).set(sss);

                                        Toast.makeText(match_room.this, "Added", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
                                        dialog.dismiss();
                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(match_room.this, "Your Balance Is Low", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(match_room.this, "Doesn't Exists", Toast.LENGTH_SHORT).show();
                                }}
                        });
                    }else{
                        dialog.dismiss();
                        Toast.makeText(match_room.this, "Give Each Player ID", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if(types.equals("SQUAD")){
            AlertDialog.Builder builder = new AlertDialog.Builder(match_room.this);
            builder.setTitle("Name");
            final View customLayout = getLayoutInflater().inflate(R.layout.regi_alert_squad, null);
            builder.setView(customLayout);
            EditText tname = customLayout.findViewById(R.id.tnames);
            EditText editText = customLayout.findViewById(R.id.editText);
            EditText editText1 = customLayout.findViewById(R.id.editText2);
            EditText editText2 = customLayout.findViewById(R.id.editText3);
            EditText editText3 = customLayout.findViewById(R.id.editText4);
            Button svc = customLayout.findViewById(R.id.svc);

            AlertDialog dialog = builder.create();
            dialog.show();
            svc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressDialog dialog = ProgressDialog.show(match_room.this, "Data Is Adding To Database", "Loading. Please wait...", true);
                    if(!(editText.getText().toString().length() == 0 && editText1.getText().toString().length() == 0 && tname.getText().toString().length() == 0 && editText2.getText().toString().length() == 0 && editText3.getText().toString().length() == 0 )){
                        fs.collection("Balance").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot d) {
                                if(d.exists()){
                                    amount = Integer.parseInt(String.valueOf(d.get("Balance")));
                                    wamount = Integer.parseInt(String.valueOf(d.get("Winings")));
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
                                        his.put("Stats", "Paid Match Fee");
                                        his.put("DateTime", cdt);
                                        Map<String, Object> noll;
                                        noll = new HashMap<>();
                                        noll.put("null", null);
                                        fs.collection("Balance").document(uid).set(bal);
                                        DocumentReference ds = fs.collection("PaymentHistory").document(uid);
                                        ds.set(noll);
                                        ds.collection("DateTime").document(cdt).set(his);
                                        Map<String, Object> ddd;
                                        ddd = new HashMap<>();
                                        ddd.put("TeamName", tname.getText().toString());
                                        ddd.put("Player1Id", editText.getText().toString());
                                        ddd.put("Player2Id", editText1.getText().toString());
                                        ddd.put("Player3Id", editText2.getText().toString());
                                        ddd.put("Player4Id", editText3.getText().toString());
                                        DocumentReference dx1 = fs.collection("MatchRegister").document("SQUAD");
                                        dx1.set(noll);
                                        DocumentReference dx = dx1.collection("MatchID").document(matchid);
                                        dx.set(noll);
                                        dx.collection("PlayerId").document(uid).set(ddd);
                                        Map<String, Object> sss;
                                        sss = new HashMap<>();
                                        sss.put("Type", "SQUAD");

                                        DocumentReference dxs = fs.collection("Match").document("Upcoming");
                                        dxs.set(noll);
                                        DocumentReference dxss = dxs.collection("MatchID").document(matchid);
                                        dxss.set(noll);
                                        dxss.collection("PlayerId").document(uid).set(sss);

                                        Toast.makeText(match_room.this, "Added", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
                                        dialog.dismiss();
                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(match_room.this, "Your Balance Is Low", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(match_room.this, "Doesn't Exists", Toast.LENGTH_SHORT).show();
                                }}
                        });
                    }else{

                        dialog.dismiss();
                        Toast.makeText(match_room.this, "Give Each Player ID", Toast.LENGTH_SHORT).show();
                }}
            });
        }
    }
    public void rv(){
        coursesrrayList = new ArrayList<>();
        bgmirv.setHasFixedSize(true);
        bgmirv.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new team_list_adapter(coursesrrayList, this);
        ArrayList<team_list> list = new ArrayList<>();

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
                                    courseRVdapter= new team_list_adapter(list, match_room.this);
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
    public void button(){
        fs.collection("Daily Matches").whereEqualTo("Time", times).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for (QueryDocumentSnapshot document : q) {
                        matchid = document.getId();
                        fs.collection("MatchRegister").document(types).collection("MatchID").document(matchid).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    register.setVisibility(View.GONE);
                                    live.setVisibility(View.VISIBLE);

                                    fs.collection("RoomDetails").document(matchid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnaot) {
                                            if(documentSnaot.exists()){
                                                roomid.setText(documentSnaot.getString("RoomID"));
                                                roompass.setText(documentSnaot.getString("RoomPass"));
                                            } else{
                                                roomid.setText("Hidden");
                                                roompass.setText("Hidden");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}