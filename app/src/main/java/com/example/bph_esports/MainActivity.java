package com.example.bph_esports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bph_esports.helper.history_list;
import com.example.bph_esports.helper.match_list;
import com.example.bph_esports.helper.match_list_adapter;
import com.example.bph_esports.helper.quiz_list;
import com.example.bph_esports.helper.quiz_list_adapter;
import com.example.bph_esports.helper.quiz_result_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    DrawerLayout dLayout;
    TextView bale;
    CardView balen;
    ImageButton smenux;
    Button bgmi, quiz, squad, duo, solo, phy, che, mat, bio, phyr, cher, matr, bior;
    LinearLayout bgmibtn, quizbtn, quizbtn1;
    String type = "SQUAD", uid, sub = "Physics";
    FirebaseFirestore fs;
    FirebaseAuth fa;
    FrameLayout quizf;
    BottomNavigationView navigation;

    private RecyclerView bgmirv, phyrv, cherv, matrv, biorv, phyrvr, chervr, matrvr, biorvr;
    private ArrayList<match_list> coursesrrayList;
    private match_list_adapter courseRVdapter;
    private ArrayList<quiz_list> coursesrrayList1;
    private ArrayList<history_list> coursesrrayList11;
    private quiz_list_adapter courseRVdapter1;
    private quiz_result_adapter courseRVdapter11;
    ArrayList<String> listz = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fa = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();
        navigation = (BottomNavigationView) findViewById(R.id.bnavigation);
        menux = findViewById(R.id.menux);
        bgmi = findViewById(R.id.bgmi);
        bale = findViewById(R.id.bale);
        balen = findViewById(R.id.balen);
        quiz = findViewById(R.id.free);
        bgmirv = findViewById(R.id.bgmirview);

        quizf = findViewById(R.id.quiz);
        bgmibtn = findViewById(R.id.bgmibtn);
        quizbtn = findViewById(R.id.quizbtn);
        quizbtn1 = findViewById(R.id.quizbtn1);

        squad = findViewById(R.id.squad);
        duo = findViewById(R.id.duo);
        solo = findViewById(R.id.solo);

        phy = findViewById(R.id.phy);
        che = findViewById(R.id.che);
        mat = findViewById(R.id.mat);
        bio = findViewById(R.id.bio);

        phyr = findViewById(R.id.phyr);
        cher = findViewById(R.id.cher);
        matr = findViewById(R.id.matr);
        bior = findViewById(R.id.bior);

        phyrv = findViewById(R.id.phyrv);
        cherv = findViewById(R.id.cherv);
        matrv = findViewById(R.id.matrv);
        biorv = findViewById(R.id.biorv);

        phyrvr = findViewById(R.id.phyrvr);
        chervr = findViewById(R.id.chervr);
        matrvr = findViewById(R.id.matrvr);
        biorvr = findViewById(R.id.biorvr);


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

        FirebaseUser mFirebaseUser = fa.getCurrentUser();
        if(mFirebaseUser != null) {
            String id = fa.getCurrentUser().getUid();
            fs.collection("users").whereEqualTo("UserName", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot q) {
                            if(!q.isEmpty()){
                                for(DocumentSnapshot d: q){
                                    uid = d.getId();
                                }
                                rest();
                                quizs();
                            }
                        }
                    });
        }else {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                phyrv.setVisibility(View.VISIBLE);
                cherv.setVisibility(View.INVISIBLE);
                matrv.setVisibility(View.INVISIBLE);
                biorv.setVisibility(View.INVISIBLE);
                sub = "Physics";
                phy.setBackgroundResource(R.color.black_700);
                mat.setBackgroundResource(R.color.teal_700);
                che.setBackgroundResource(R.color.teal_700);
                bio.setBackgroundResource(R.color.teal_700);


                quizf.setVisibility(View.VISIBLE);
                bgmirv.setVisibility(View.INVISIBLE);
                bgmibtn.setVisibility(View.INVISIBLE);
                quizbtn.setVisibility(View.VISIBLE);
                quizbtn1.setVisibility(View.VISIBLE);
                quiz.setBackgroundResource(R.color.black_700);
                bgmi.setBackgroundResource(R.color.teal_700);
            }
        });
        bgmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizf.setVisibility(View.INVISIBLE);
                bgmirv.setVisibility(View.VISIBLE);
                bgmibtn.setVisibility(View.VISIBLE);
                quizbtn.setVisibility(View.INVISIBLE);
                quizbtn1.setVisibility(View.INVISIBLE);
                quiz.setBackgroundResource(R.color.teal_700);
                bgmi.setBackgroundResource(R.color.black_700);
            }
        });


        balen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), wallet.class));
            }
        });

        menux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout navDrawer = findViewById(R.id.my_drawer_layout);
                if(!navDrawer.isDrawerOpen(Gravity.LEFT)) navDrawer.openDrawer(Gravity.LEFT);
                else navDrawer.closeDrawer(Gravity.LEFT);
            }
        });

    }
    private void setNavigationDrawer() {
        dLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navView = findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_account) {
                    startActivity(new Intent(getApplicationContext(),profile.class));
                }else if (itemId == R.id.nav_wallet) {
                    startActivity(new Intent(getApplicationContext(),wallet.class));
                } else if (itemId == R.id.nav_settings) {
                    //startActivity(new Intent(getApplicationContext(),settings.class));
                } else if (itemId == R.id.nav_logout) {
                    logout();
                }
                return false;
            }
        });
    }

    public void  rest(){

        coursesrrayList = new ArrayList<>();
        bgmirv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        bgmirv.setLayoutManager(linearLayoutManager);
        courseRVdapter = new match_list_adapter(coursesrrayList, this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listz);

        ArrayList<match_list> list = new ArrayList<>();

        fs.collection("Balance").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                if(d.exists()){
                    Object x = d.get("Balance");
                    bale.setText(String.valueOf(x));
                }
            }
        });



        fs.collection("Daily Matches").whereEqualTo("Type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        list.add(new match_list(String.valueOf(d.get("Region")), String.valueOf(d.get("Fees")), String.valueOf(d.get("Per Kill")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Max Member")), String.valueOf(d.get("Time")), String.valueOf(d.get("Type")), String.valueOf(d.get("Perspective")), d.getId(),  String.valueOf(d.get("Map"))));
                        courseRVdapter= new match_list_adapter(list, MainActivity.this);
                        bgmirv.setAdapter(courseRVdapter);
                    }
                    // courseRVdapter.notifyDataSetChanged();
                }
            }
        });

        setNavigationDrawer();
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tour) {
                    Intent i = new Intent(MainActivity.this, tournament.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return true;
                }else if (item.getItemId() == R.id.lead) {
                    Intent i = new Intent(MainActivity.this, leaderboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }else if (item.getItemId() == R.id.mymatch) {
                    Intent i = new Intent(MainActivity.this, my_matches.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }
                return false;
            }
        });

        duo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duo.setClickable(false);
                squad.setClickable(true);
                solo.setClickable(true);
                duo.setBackgroundResource(R.color.black_700);
                squad.setBackgroundResource(R.color.teal_700);
                solo.setBackgroundResource(R.color.teal_700);
                type = "DUO";

                courseRVdapter.clear();
                //listView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                fs.collection("Daily Matches").whereEqualTo("Type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list.add(new match_list(String.valueOf(d.get("Region")), String.valueOf(d.get("Fees")), String.valueOf(d.get("Per Kill")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Max Member")), String.valueOf(d.get("Time")), String.valueOf(d.get("Type")), String.valueOf(d.get("Perspective")), d.getId(),  String.valueOf(d.get("Map"))));
                                courseRVdapter= new match_list_adapter(list, MainActivity.this);
                                bgmirv.setAdapter(courseRVdapter);
                            }
                        }
                    }
                });

            }
        });
        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solo.setClickable(false);
                squad.setClickable(true);
                duo.setClickable(true);
                solo.setBackgroundResource(R.color.black_700);
                squad.setBackgroundResource(R.color.teal_700);
                duo.setBackgroundResource(R.color.teal_700);
                type = "SOLO";


                courseRVdapter.clear();
                fs.collection("Daily Matches").whereEqualTo("Type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list.add(new match_list(String.valueOf(d.get("Region")), String.valueOf(d.get("Fees")), String.valueOf(d.get("Per Kill")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Max Member")), String.valueOf(d.get("Time")), String.valueOf(d.get("Type")), String.valueOf(d.get("Perspective")), d.getId(),  String.valueOf(d.get("Map"))));
                                courseRVdapter= new match_list_adapter(list, MainActivity.this);
                                bgmirv.setAdapter(courseRVdapter);
                            }
                        }
                    }
                });
            }
        });
        squad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                squad.setClickable(false);
                duo.setClickable(true);
                solo.setClickable(true);
                squad.setBackgroundResource(R.color.black_700);
                duo.setBackgroundResource(R.color.teal_700);
                solo.setBackgroundResource(R.color.teal_700);
                type = "SQUAD";

                courseRVdapter.clear();

                fs.collection("Daily Matches").whereEqualTo("Type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list.add(new match_list(String.valueOf(d.get("Region")), String.valueOf(d.get("Fees")), String.valueOf(d.get("Per Kill")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Max Member")), String.valueOf(d.get("Time")), String.valueOf(d.get("Type")), String.valueOf(d.get("Perspective")), d.getId(),  String.valueOf(d.get("Map"))));
                                courseRVdapter= new match_list_adapter(list, MainActivity.this);
                                bgmirv.setAdapter(courseRVdapter);
                            }
                        }
                    }
                });
            }
        });

    }


    public void quizs(){

        coursesrrayList1 = new ArrayList<>();
        phyrv.setHasFixedSize(true);
        cherv.setHasFixedSize(true);
        matrv.setHasFixedSize(true);
        biorv.setHasFixedSize(true);
        phyrvr.setHasFixedSize(true);
        chervr.setHasFixedSize(true);
        matrvr.setHasFixedSize(true);
        biorvr.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager7 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager8 = new LinearLayoutManager(getApplicationContext());
        phyrv.setLayoutManager(linearLayoutManager1);
        cherv.setLayoutManager(linearLayoutManager2);
        matrv.setLayoutManager(linearLayoutManager3);
        biorv.setLayoutManager(linearLayoutManager4);
        phyrvr.setLayoutManager(linearLayoutManager5);
        chervr.setLayoutManager(linearLayoutManager6);
        matrvr.setLayoutManager(linearLayoutManager7);
        biorvr.setLayoutManager(linearLayoutManager8);
        courseRVdapter1 = new quiz_list_adapter(coursesrrayList1, this);
        courseRVdapter11 = new quiz_result_adapter(coursesrrayList11, this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listz);

        ArrayList<quiz_list> list1 = new ArrayList<>();
        ArrayList<history_list> list11 = new ArrayList<>();

        fs.collection("Quiz").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        list1.add(new quiz_list(d.getId(), String.valueOf(d.get("Fees")), String.valueOf(d.get("Length")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Date")), String.valueOf(d.get("Time")), String.valueOf(d.get("Question")), null, sub, null));
                        courseRVdapter1= new quiz_list_adapter(list1, MainActivity.this);
                        phyrv.setAdapter(courseRVdapter1);
                    }
                    // courseRVdapter.notifyDataSetChanged();
                }
            }
        });
        phy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phyrv.setVisibility(View.VISIBLE);
                cherv.setVisibility(View.INVISIBLE);
                matrv.setVisibility(View.INVISIBLE);
                biorv.setVisibility(View.INVISIBLE);
                sub = "Physics";
                phy.setBackgroundResource(R.color.black_700);
                mat.setBackgroundResource(R.color.teal_700);
                che.setBackgroundResource(R.color.teal_700);
                bio.setBackgroundResource(R.color.teal_700);
                phyrvr.setVisibility(View.INVISIBLE);
                matrvr.setVisibility(View.INVISIBLE);
                chervr.setVisibility(View.INVISIBLE);
                biorvr.setVisibility(View.INVISIBLE);

                phyr.setEnabled(true);
                cher.setEnabled(false);
                matr.setEnabled(false);
                bior.setEnabled(false);

                courseRVdapter1.clear();
                fs.collection("Quiz").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list1.add(new quiz_list(d.getId(), String.valueOf(d.get("Fees")), String.valueOf(d.get("Length")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Date")), String.valueOf(d.get("Time")), String.valueOf(d.get("Question")), null, sub, null));
                                courseRVdapter1= new quiz_list_adapter(list1, MainActivity.this);
                                phyrv.setAdapter(courseRVdapter1);
                            }
                            // courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
       che.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phyrv.setVisibility(View.INVISIBLE);
                cherv.setVisibility(View.VISIBLE);
                matrv.setVisibility(View.INVISIBLE);
                biorv.setVisibility(View.INVISIBLE);
                sub = "Chemistry";

                phyrvr.setVisibility(View.INVISIBLE);
                matrvr.setVisibility(View.INVISIBLE);
                chervr.setVisibility(View.INVISIBLE);
                biorvr.setVisibility(View.INVISIBLE);


                phy.setBackgroundResource(R.color.teal_700);
                mat.setBackgroundResource(R.color.teal_700);
                che.setBackgroundResource(R.color.black_700);
                bio.setBackgroundResource(R.color.teal_700);

                phyr.setEnabled(false);
                cher.setEnabled(true);
                matr.setEnabled(false);
                bior.setEnabled(false);


                courseRVdapter1.clear();
                fs.collection("Quiz").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list1.add(new quiz_list(d.getId(), String.valueOf(d.get("Fees")), String.valueOf(d.get("Length")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Date")), String.valueOf(d.get("Time")), String.valueOf(d.get("Question")), null, sub, null));
                                courseRVdapter1= new quiz_list_adapter(list1, MainActivity.this);
                                cherv.setAdapter(courseRVdapter1);
                            }
                            // courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phyrv.setVisibility(View.INVISIBLE);
                cherv.setVisibility(View.INVISIBLE);
                matrv.setVisibility(View.VISIBLE);
                biorv.setVisibility(View.INVISIBLE);
                sub = "Math";

                phyrvr.setVisibility(View.INVISIBLE);
                matrvr.setVisibility(View.INVISIBLE);
                chervr.setVisibility(View.INVISIBLE);
                biorvr.setVisibility(View.INVISIBLE);
                phy.setBackgroundResource(R.color.teal_700);
                mat.setBackgroundResource(R.color.black_700);
                che.setBackgroundResource(R.color.teal_700);
                bio.setBackgroundResource(R.color.teal_700);

                phyr.setEnabled(false);
                cher.setEnabled(false);
                matr.setEnabled(true);
                bior.setEnabled(false);


                courseRVdapter1.clear();
                fs.collection("Quiz").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list1.add(new quiz_list(d.getId(), String.valueOf(d.get("Fees")), String.valueOf(d.get("Length")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Date")), String.valueOf(d.get("Time")), String.valueOf(d.get("Question")), null, sub, null));
                                courseRVdapter1= new quiz_list_adapter(list1, MainActivity.this);
                                matrv.setAdapter(courseRVdapter1);
                            }
                            // courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phyrv.setVisibility(View.INVISIBLE);
                cherv.setVisibility(View.INVISIBLE);
                matrv.setVisibility(View.INVISIBLE);
                biorv.setVisibility(View.VISIBLE);

                phyrvr.setVisibility(View.INVISIBLE);
                matrvr.setVisibility(View.INVISIBLE);
                chervr.setVisibility(View.INVISIBLE);
                biorvr.setVisibility(View.INVISIBLE);
                phyr.setEnabled(false);
                cher.setEnabled(false);
                matr.setEnabled(false);
                bior.setEnabled(true);

                sub = "Biology";
                phy.setBackgroundResource(R.color.teal_700);
                mat.setBackgroundResource(R.color.teal_700);
                che.setBackgroundResource(R.color.teal_700);
                bio.setBackgroundResource(R.color.black_700);



                courseRVdapter1.clear();
                fs.collection("Quiz").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list1.add(new quiz_list(d.getId(), String.valueOf(d.get("Fees")), String.valueOf(d.get("Length")), String.valueOf(d.get("First")), String.valueOf(d.get("Second")), String.valueOf(d.get("Third")), String.valueOf(d.get("Date")), String.valueOf(d.get("Time")), String.valueOf(d.get("Question")), null, sub, null));
                                courseRVdapter1= new quiz_list_adapter(list1, MainActivity.this);
                                biorv.setAdapter(courseRVdapter1);
                            }
                            // courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        fs.collection("QuizResults").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        fs.collection("QuizResults").document(sub).collection("ExamId").document(d.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot q) {
                                if(q.exists()) {
                                    list11.add(new history_list(d.getId(), uid, String.valueOf(d.get("Correct")), null));
                                    courseRVdapter11 = new quiz_result_adapter(list11, MainActivity.this);
                                    phyrvr.setAdapter(courseRVdapter11);
                                }
                            }
                        });
                    }
                    //courseRVdapter.notifyDataSetChanged();
                }
            }
        });

        phyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phyrvr.setVisibility(View.VISIBLE);
                phyrv.setVisibility(View.INVISIBLE);


                courseRVdapter11.clear();
                fs.collection("QuizResults").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                fs.collection("QuizResults").document(sub).collection("ExamId").document(d.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot q) {
                                        if(q.exists()) {
                                            list11.add(new history_list(d.getId(), uid, String.valueOf(d.get("Correct")), null));
                                            courseRVdapter11 = new quiz_result_adapter(list11, MainActivity.this);
                                            Log.e("TAG", String.valueOf(courseRVdapter11.getItemCount()));
                                            phyrvr.setAdapter(courseRVdapter11);
                                            Log.e("TAG", String.valueOf(courseRVdapter11.getItemCount()));
                                        }
                                    }
                                });
                            }
                            //courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        cher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chervr.setVisibility(View.VISIBLE);
                cherv.setVisibility(View.INVISIBLE);

                courseRVdapter11.clear();
                fs.collection("QuizResults").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                Log.e("TAG", d.getId());
                                fs.collection("QuizResults").document(sub).collection("ExamId").document(d.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot q) {
                                        if(q.exists()){
                                            list11.add(new history_list(d.getId(), uid,  String.valueOf(q.get("Correct")), null));
                                            courseRVdapter11= new quiz_result_adapter(list11, MainActivity.this);
                                            chervr.setAdapter(courseRVdapter11);
                                        }
                                    }
                                });
                            }
                            // courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        matr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrvr.setVisibility(View.VISIBLE);
                matrv.setVisibility(View.INVISIBLE);


                courseRVdapter11.clear();
                fs.collection("QuizResults").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                Log.e("TAG", d.getId());
                                fs.collection("QuizResults").document(sub).collection("ExamId").document(d.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot q) {
                                        if(q.exists()){
                                            list11.add(new history_list(d.getId(), uid,  String.valueOf(q.get("Correct")), null));
                                            courseRVdapter11= new quiz_result_adapter(list11, MainActivity.this);
                                            matrvr.setAdapter(courseRVdapter11);
                                        }
                                    }
                                });
                            }
                            // courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        bior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biorvr.setVisibility(View.VISIBLE);
                biorv.setVisibility(View.INVISIBLE);


                courseRVdapter11.clear();
                fs.collection("QuizResults").document(sub).collection("ExamId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                Log.e("TAG", d.getId());
                                fs.collection("QuizResults").document(sub).collection("ExamId").document(d.getId()).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot q) {
                                        if(q.exists()){
                                            list11.add(new history_list(d.getId(), uid,  String.valueOf(q.get("Correct")), null));
                                            courseRVdapter11= new quiz_result_adapter(list11, MainActivity.this);
                                            biorvr.setAdapter(courseRVdapter11);
                                        }
                                    }
                                });
                            }
                            // courseRVdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
        finish();
    }
}