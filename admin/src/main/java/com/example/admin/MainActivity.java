package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button matches, withdraw, tournaments, matchregi, matchresult, done, quiz, quizr;
    ImageButton logout;
    FirebaseFirestore fs;
    ListView listView,listView1;
    ArrayList<String> listItem;
    String[] listItem1;
    ArrayAdapter<String> adapter, adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        matches = findViewById(R.id.matches);
        withdraw = findViewById(R.id.withdraw);
        tournaments = findViewById(R.id.tournaments);
        logout = findViewById(R.id.logout);
        matchregi = findViewById(R.id.matchregi);
        matchresult = findViewById(R.id.matchresult);

        quiz = findViewById(R.id.quiz);
        quizr = findViewById(R.id.quizresult);

        fs = FirebaseFirestore.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), daily_matches.class));
            }
        });
        tournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), tournament.class));
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), withdraw.class));
            }
        });
        matchregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), matchs_lists.class));
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listItem1 = new String[]{"Physics", "Chemistry", "Math", "Biology"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                View rowList = getLayoutInflater().inflate(R.layout.row, null);
                listView1 = rowList.findViewById(R.id.listView);
                done = rowList.findViewById(R.id.btton);
                adapter1 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listItem1);
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
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {dialog.dismiss();
                        Intent ix = new Intent(MainActivity.this, quiz.class);
                        ix.putExtra("ID", listItem1[i]);
                        startActivity(ix);
                        finish();
                    }
                });
            }
        });

        quizr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listItem1 = new String[]{"Physics", "Chemistry", "Math", "Biology"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                View rowList = getLayoutInflater().inflate(R.layout.row, null);
                listView1 = rowList.findViewById(R.id.listView);
                done = rowList.findViewById(R.id.btton);
                adapter1 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listItem1);
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
                        Intent ix = new Intent(MainActivity.this, quiz_list.class);
                        ix.putExtra("ID", listItem1[i]);
                        startActivity(ix);
                        finish();
                    }
                });
            }
        });
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getApplicationContext(), LoginRegister.class));
                    finish();
                }
            }
        });

        matchresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listItem = new ArrayList<>();
                fs.collection("Match").document("Pending").collection("MatchID").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot q) {
                        if(!q.isEmpty()){
                            for (DocumentSnapshot d : q){
                                listItem.add(d.getId());
                            }
                        }
                    }
                });

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                View rowList = getLayoutInflater().inflate(R.layout.row, null);
                listView = rowList.findViewById(R.id.listView);
                done = rowList.findViewById(R.id.btton);
                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listItem);
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
                        Intent ix = new Intent(MainActivity.this, matchresults.class);
                        ix.putExtra("ID", listItem.get(i));
                        startActivity(ix);
                        finish();
                    }
                });
            }
        });

    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(MainActivity.this, LoginRegister.class);
        startActivity(i);
        finish();
    }
}