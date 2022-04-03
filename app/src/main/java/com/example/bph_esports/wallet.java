package com.example.bph_esports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bph_esports.helper.history_list;
import com.example.bph_esports.helper.history_list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class wallet extends AppCompatActivity {

    Button add, withdraw;
    Integer amount = 0;
    TextView balance, usrname, win;
    FirebaseFirestore fs;
    FirebaseAuth fa;
    Map<String, Object> bal;
    String uid;
    public boolean tic1 = false;

    private RecyclerView bgmirv;

    ArrayList<history_list> list;
    private history_list_adapter courseRVdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        fa = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();
        balance = findViewById(R.id.balance);
        usrname = findViewById(R.id.usrname);
        add = findViewById(R.id.addm);
        win = findViewById(R.id.win);
        bgmirv = findViewById(R.id.hisrv);
        withdraw = findViewById(R.id.withdraw);


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

        fs.collection("users").whereEqualTo("UserName", fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d: q){
                        uid = d.getId();
                    }
                    usrname.setText(uid);
                    dds();
                }
            }
        });
        bal = new HashMap<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(wallet.this);
                final EditText edittext = new EditText(wallet.this);
                alert.setMessage("Enter The amount");
                alert.setTitle("Add Balance");
                edittext.setHint("0000");
                edittext.setPadding(50,10,50,10);
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(edittext);
                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        amount = Integer.parseInt(edittext.getText().toString()) + amount;
                        bal.put("Balance", amount);
                        bal.put("Winings", 0);
                        SimpleDateFormat sdf = new SimpleDateFormat("KK:mma,dd-MM-yy");
                        String cdt = sdf.format(new Date());
                        fs.collection("Balance").document(uid).set(bal).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Map<String, Object> his;
                                his = new HashMap<>();
                                his.put("Amount", Integer.parseInt(edittext.getText().toString()));
                                his.put("Stats", "Added In Account");
                                his.put("DateTime", cdt);
                                Map<String, Object> noll;
                                noll = new HashMap<>();
                                noll.put("null", null);
                                DocumentReference ds = fs.collection("PaymentHistory").document(uid);
                                ds.set(noll);
                                ds.collection("DateTime").document(cdt).set(his);
                                balance.setText(amount.toString());
                                Toast.makeText(wallet.this, "Adding Successful", Toast.LENGTH_SHORT).show();
                                tic1();
                            }
                        });
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });

       withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount >= 2000){
                AlertDialog.Builder alert = new AlertDialog.Builder(wallet.this);
                final EditText edittext = new EditText(wallet.this);
                final EditText edittext1 = new EditText(wallet.this);
                alert.setMessage("Enter The amount");
                alert.setTitle("Withdraw Balance");
                edittext.setHint("0000");
                edittext1.setHint("Upi Number");
                edittext.setPadding(50,10,50,10);
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                edittext1.setPadding(50,10,50,10);
                edittext1.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(edittext);
                alert.setPositiveButton("Withdraw", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(edittext1.getText().toString().length() == 0 && edittext.getText().toString().length() == 0){
                            int v = Integer.parseInt(edittext.getText().toString());
                            if(v>amount){

                                if(v >= 2000){
                                    amount = v - amount;
                                    bal.put("Balance", amount);
                                    bal.put("Winings", 0);
                                    Map<String, Object> wit;
                                    wit = new HashMap<>();
                                    wit.put("Amount", v);
                                    wit.put("UserId", uid);
                                    wit.put("UPI", edittext1.getText().toString().length());
                                    SimpleDateFormat sdf = new SimpleDateFormat("KK:mma,dd-MM-yy");
                                    String cdt = sdf.format(new Date());
                                    fs.collection("WithdrawRequest").add(wit).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            balance.setText(amount.toString());
                                            Map<String, Object> his;
                                            his = new HashMap<>();
                                            his.put("Amount", Integer.parseInt(edittext.getText().toString()));
                                            his.put("Stats", "Withdrawn From Account");
                                            his.put("DateTime", cdt);
                                            Map<String, Object> noll;
                                            noll = new HashMap<>();
                                            noll.put("null", null);
                                            fs.collection("Balance").document(uid).set(bal);
                                            DocumentReference ds = fs.collection("PaymentHistory").document(uid);
                                            ds.set(noll);
                                            ds.collection("DateTime").document(cdt).set(his);
                                            Toast.makeText(wallet.this, "Withdraw Successful", Toast.LENGTH_SHORT).show();
                                            tic1();
                                        }
                                    });
                                }else
                                    Toast.makeText(wallet.this, "Minimum Withdraw is 2000", Toast.LENGTH_SHORT).show();

                            }else
                                Toast.makeText(wallet.this, "Earn This Amount First", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(wallet.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
                }else
                    Toast.makeText(wallet.this, "Your Balance is Low!!", Toast.LENGTH_SHORT).show();

            }
        });

        list = new ArrayList<>();
        bgmirv.setHasFixedSize(true);
        bgmirv.setLayoutManager(new LinearLayoutManager(this));
        courseRVdapter = new history_list_adapter(list, this);

    }
    public void tic1(){
        if(!tic1){
            new CountDownTimer(120000, 1000) {
                public void onTick(long millisUntilFinished) {
                    add.setEnabled(false);
                    withdraw.setEnabled(false);
                    tic1 = true;
                }

                public void onFinish() {
                    add.setEnabled(true);
                    withdraw.setEnabled(true);
                    tic1= false;
                }
            }.start();
        }
    }
    private void dds(){

        fs.collection("Balance").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                if(d.exists()){
                    Object x = d.get("Balance");
                    Object s = d.get("Winings");
                    amount = Integer.parseInt(String.valueOf(x));
                    win.setText(String.valueOf(s));
                    balance.setText(String.valueOf(x));
                    bal.put("Balance", amount);
                }
            }
        });
        fs.collection("PaymentHistory").document(uid).collection("DateTime").orderBy("DateTime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        Log.e(d.getId(),d.get("Amount") + d.getString("Stats") );
                        list.add(new history_list(d.getString("Stats"), d.getId(), uid, Integer.parseInt(String.valueOf(d.get("Amount")))));
                        courseRVdapter= new history_list_adapter(list, wallet.this);
                        bgmirv.setAdapter(courseRVdapter);
                    }
                    courseRVdapter.notifyDataSetChanged();
                }
            }
        });


    }
}