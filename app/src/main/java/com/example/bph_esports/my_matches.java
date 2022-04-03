package com.example.bph_esports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.bph_esports.helper.Completed_adapter;
import com.example.bph_esports.helper.Pending_adapter;
import com.example.bph_esports.helper.Upmatch_adapter;
import com.example.bph_esports.helper.completed_list;
import com.example.bph_esports.helper.history_list;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class my_matches extends AppCompatActivity {

    Button live, upc, com;
    RecyclerView liver, upcr, comr;
    String time, uid;
    Button pend, done;

    FirebaseFirestore fs;
    FirebaseAuth fa;
    private Upmatch_adapter a;
    private Pending_adapter b;
    private Completed_adapter c;
    ArrayList<history_list> list1, list2;
    ArrayList<completed_list> list3;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_matches);

        liver = findViewById(R.id.liveview);
        upcr = findViewById(R.id.upcommingview);
        comr = findViewById(R.id.completedview);
        live = findViewById(R.id.live);
        upc = findViewById(R.id.upc);
        com = findViewById(R.id.com);
        pend = findViewById(R.id.pending);
        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            time = getDate(getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }


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

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                live.setClickable(false);
                upc.setClickable(true);
                com.setClickable(true);
                live.setBackgroundResource(R.color.black_700);
                upc.setBackgroundResource(R.color.teal_700);
                com.setBackgroundResource(R.color.teal_700);
                liver.setVisibility(View.VISIBLE);
                comr.setVisibility(View.INVISIBLE);
                upcr.setVisibility(View.INVISIBLE);
                pend.setClickable(false);
                pend.setVisibility(View.INVISIBLE);

            }
        });


        upc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                live.setClickable(true);
                upc.setClickable(false);
                com.setClickable(true);
                upc.setBackgroundResource(R.color.black_700);
                live.setBackgroundResource(R.color.teal_700);
                com.setBackgroundResource(R.color.teal_700);
                liver.setVisibility(View.INVISIBLE);
                comr.setVisibility(View.INVISIBLE);
                upcr.setVisibility(View.VISIBLE);
                pend.setClickable(false);
                pend.setVisibility(View.INVISIBLE);
            }
        });
        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                live.setClickable(true);
                upc.setClickable(true);
                com.setClickable(false);
                com.setBackgroundResource(R.color.black_700);
                upc.setBackgroundResource(R.color.teal_700);
                live.setBackgroundResource(R.color.teal_700);
                liver.setVisibility(View.INVISIBLE);
                comr.setVisibility(View.VISIBLE);
                upcr.setVisibility(View.INVISIBLE);
                pend.setClickable(true);
                pend.setVisibility(View.VISIBLE);

            }
        });
        fs.collection("users").whereEqualTo("UserName", fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                for(DocumentSnapshot d: q){
                    uid = d.getId();
                }
            }
        });

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        upcr.setHasFixedSize(true);
        upcr.setLayoutManager(new LinearLayoutManager(this));
        comr.setHasFixedSize(true);
        comr.setLayoutManager(new LinearLayoutManager(this));
        liver.setHasFixedSize(true);
        liver.setLayoutManager(new LinearLayoutManager(this));
        a = new Upmatch_adapter(list1, this);
        b = new Pending_adapter(list2, this);
        c = new Completed_adapter(list3, this);
        ArrayList<HashMap<String,String>> names=new ArrayList<>();
        fs.collection("MatchRegister").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for (DocumentSnapshot d : q){
                        fs.collection("MatchRegister").document(d.getId()).collection("MatchID").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot q1) {
                                if(!q1.isEmpty()){
                                    for (DocumentSnapshot d1 : q1){
                                        fs.collection("MatchRegister").document(d.getId()).collection("MatchID").document(d1.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot d2) {
                                                if(d2.exists()){
                                                    String type =  d.getId();
                                                    fs.collection("Match").document("Upcoming").collection("MatchID").document(d1.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if(documentSnapshot.exists()){
                                                                fs.collection("Daily Matches").document(d1.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot doshot) {
                                                                        if(doshot.exists()){
                                                                            try {
                                                                                lives(doshot.getString("Time"), type, d1.getId());
                                                                            } catch (ParseException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        list1.add(new history_list(doshot.getString("Time"), documentSnapshot.getString("Type"), d1.getId(), null));
                                                                        a= new Upmatch_adapter(list1, my_matches.this);
                                                                        upcr.setAdapter(a);
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });
                                                    fs.collection("Match").document("Live").collection("MatchID").document(d1.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if(documentSnapshot.exists()){
                                                                fs.collection("Daily Matches").document(d1.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot doshot) {
                                                                        if(doshot.exists()){
                                                                            try {
                                                                                pending(doshot.getString("Time"), d.getId(), d1.getId());
                                                                            } catch (ParseException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                            list2.add(new history_list(doshot.getString("Time"), documentSnapshot.getString("Type"), d1.getId(), null));
                                                                            b= new Pending_adapter(list2, my_matches.this);
                                                                            liver.setAdapter(b);
                                                                        }
                                                                    }

                                                                });
                                                            }
                                                        }
                                                    });

                                                    fs.collection("Match").document("Pending").collection("MatchID").document(d1.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if(documentSnapshot.exists()){
                                                                fs.collection("Daily Matches").document(d1.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot doshot) {
                                                                        HashMap<String,String> hashMap=new HashMap<>();
                                                                        hashMap.put("Time",doshot.getString("Time"));
                                                                        hashMap.put("MatchId",d1.getId());
                                                                        names.add(hashMap);
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });

                                                    pend.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(my_matches.this);
                                                            View rowList = getLayoutInflater().inflate(R.layout.row, null);
                                                            listView = rowList.findViewById(R.id.listView);
                                                            done = rowList.findViewById(R.id.btton);
                                                            SimpleAdapter adapter = new SimpleAdapter(my_matches.this, names, android.R.layout.simple_list_item_2, new String[] {"Time", "MatchId"}, new int[] {android.R.id.text1, android.R.id.text2,});
                                                            listView.setAdapter(adapter);
                                                            adapter.notifyDataSetChanged();
                                                            alertDialog.setView(rowList);
                                                            AlertDialog dialog = alertDialog.create();
                                                            dialog.show();
                                                            done.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                @Override
                                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                    Toast.makeText(getApplicationContext(),i,Toast.LENGTH_LONG).show();//show the selected image in toast according to position
                                                                }
                                                            });
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
            }
        });



        fs.collection("MatchHistory").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot document) {
                if(!document.isEmpty()){
                    for (DocumentSnapshot documentSnapshot : document){
                        fs.collection("MatchHistory").document(documentSnapshot.getId()).collection("MatchID").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot dsoshot) {
                                if(!dsoshot.isEmpty()){
                                    for (DocumentSnapshot doshot: dsoshot){
                                        Log.e(documentSnapshot.getId(),doshot.getId());
                                        fs.collection("MatchHistory").document(documentSnapshot.getId()).collection("MatchID").document(doshot.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot d) {
                                                if(d.exists()){
                                                    Log.e(d.getString("TeamName"),d.getString("Kills"));
                                                    list3.add(new completed_list(documentSnapshot.getId(), doshot.getId(), d.getString("TeamName"), d.getString("Kills"), uid));
                                                    c= new Completed_adapter(list3, my_matches.this);
                                                    comr.setAdapter(c);
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                        });
                    }

                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnavigation);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tour) {
                    Intent i = new Intent(my_matches.this, tournament.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return true;
                }else if (item.getItemId() == R.id.match) {
                    Intent i = new Intent(my_matches.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return true;
                }else if (item.getItemId() == R.id.lead) {
                    Intent i = new Intent(my_matches.this, leaderboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }
                return false;
            }
        });

    }


    private long getTime() throws Exception {
        String url = "https://time.is/Unix_time_now";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[] {
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements= doc.select(tags[0]);
        for (int i = 0; i <tags.length; i++) {
            elements = elements.select(tags[i]);
        }
        return Long.parseLong(elements.text());
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);

        return dateFormat.format(cal.getTime());
    }

    private void lives(String times, String tp, String matchid) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = dateFormat.parse(time);
        Date date2 = dateFormat.parse(times);
        if(date.after(date2) || date.compareTo(date2) == 0) {
            Map<String, Object> noll;
            noll = new HashMap<>();
            noll.put("null", null);
            Map<String, Object> sss;
            sss = new HashMap<>();
            sss.put("Type", tp);
            Log.e("c", String.valueOf(date));
            Log.e("c", String.valueOf(date2));
            DocumentReference dxs = fs.collection("Match").document("Live");
            dxs.set(noll);
            DocumentReference dxss = dxs.collection("MatchID").document(matchid);
            dxss.set(noll);
            dxss.collection("PlayerId").document(uid).set(sss);

            DocumentReference a = fs.collection("Match").document("Upcoming").collection("MatchID").document(matchid);
            a.collection("PlayerId").document(uid).delete();
        }
    }

    public void pending(String times, String tp, String matchid) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date d = dateFormat.parse(times);
        Date date = dateFormat.parse(time);
        Date date2 = addHour(d, 35);;
        if(date.after(date2) || date.compareTo(date2) == 0) {
            Map<String, Object> noll;
            noll = new HashMap<>();
            noll.put("null", null);
            Map<String, Object> sss;
            sss = new HashMap<>();
            sss.put("Type", tp);
            Log.e("c",matchid);
            Log.e("c",tp);
            DocumentReference dxs = fs.collection("Match").document("Pending");
            dxs.set(noll);
            DocumentReference dxss = dxs.collection("MatchID").document(matchid);
            dxss.set(noll);
            dxss.collection("PlayerId").document(uid).set(sss);

            DocumentReference a = fs.collection("Match").document("Live").collection("MatchID").document(matchid);
            a.delete();
        }
    }

    public static Date addHour(Date d, int number)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, number);
        Date newTime = cal.getTime();
        return newTime;
    }
}
