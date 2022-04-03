package com.example.bph_esports;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class exam_room extends AppCompatActivity {

    String sub, fee, len, fst, snd, thd, num, time, date, uid, uname;
    LinearLayout lt1, lt2;
    TextView cd, e;
    FirebaseFirestore fs;
    Button add;
    RadioButton o1, o2, o3, o4;
    int v;
    RadioGroup group;
    FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_room);

        fa = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();
        Intent ic = getIntent();
        sub = ic.getStringExtra("ID");
        uid = ic.getStringExtra("1");
        fee = ic.getStringExtra("2");
        len = ic.getStringExtra("3");
        fst = ic.getStringExtra("4");
        snd = ic.getStringExtra("5");
        thd = ic.getStringExtra("6");
        num = ic.getStringExtra("8");
        time = ic.getStringExtra("7");
        date = ic.getStringExtra("9");
        TextView lenth = findViewById(R.id.time);
        lenth.setText(len);

        v = Integer.parseInt(num);
        cd = findViewById(R.id.ct);
        lt1 = findViewById(R.id.lt1);
        lt2 = findViewById(R.id.lt2);
        add = findViewById(R.id.submit);

        Button start = findViewById(R.id.start);

        fs.collection("users").whereEqualTo("UserName", fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d: q){
                        uname = d.getId();
                    }
                }
            }
        });

        fs.collection("Quiz").document(sub).collection("ExamId").document(uid).collection("Question").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    int i = 0;
                    for(DocumentSnapshot d: q){
                        StringTokenizer t = new StringTokenizer(String.valueOf(d.get("Option")), "[");
                        String s = t.nextToken();
                        StringTokenizer t2 = new StringTokenizer(s, "]");
                        String c = t2.nextToken();
                        StringTokenizer tokens = new StringTokenizer(c, ", ");
                        String o1 = tokens.nextToken();
                        String o2 = tokens.nextToken();
                        String o3 = tokens.nextToken();
                        String o4 = tokens.nextToken();
                        setq(d.getString("Question"), o1, o2, o3,o4, i);
                        i++;
                    }
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lt1.setVisibility(View.INVISIBLE);
                lt2.setVisibility(View.VISIBLE);
                int i = Integer.parseInt(len);
                new CountDownTimer(i * 60000L, 1000) {
                    public void onTick(long millisUntilFinished) {
                        // Used for formatting digit to be in 2 digits only
                        NumberFormat f = new DecimalFormat("00");
                        long hour = (millisUntilFinished / 3600000) % 24;
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        cd.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    }
                    public void onFinish() {
                        cd.setText("00:00:00");
                        AlertDialog alertDialog = new AlertDialog.Builder(exam_room.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("The Time Is Up!!")
                                .setMessage("You Want To Submit What You've Answered Till Now?")
                                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int ic) {

                                        Map<String,Object>ans = new HashMap<>();
                                        RadioButton o1, o2, o3, o4;
                                        for(int i=0;i<v;i++) {
                                            View root = getWindow().getDecorView().getRootView();
                                            TextView x = root.findViewWithTag("q" + i);
                                            o1 = root.findViewWithTag("o1" + i);
                                            o2 = root.findViewWithTag("o2" + i);
                                            o3 = root.findViewWithTag("o3" + i);
                                            o4 = root.findViewWithTag("o4" + i);
                                            if(o1.isChecked()){
                                                ans.put(x.getText().toString(), o1.getText().toString());
                                            }else if(o2.isChecked()){
                                                ans.put(x.getText().toString(), o2.getText().toString());
                                            }else if(o3.isChecked()){
                                                ans.put(x.getText().toString(), o3.getText().toString());
                                            }else if(o4.isChecked()){
                                                ans.put(x.getText().toString(), o4.getText().toString());
                                            }
                                        }

                                        Map<String,Object>fff = new HashMap<>();
                                        fff.put("Null",null);

                                        DocumentReference ds = fs.collection("QuizSubmissions").document(sub);
                                        ds.set(fff);
                                        DocumentReference dss = ds.collection("ExamId").document(uid);
                                        dss.set(fff);
                                        dss.collection("PlayerId").document(fa.getCurrentUser().getUid()).set(ans).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialogInterface.dismiss();
                                                Toast.makeText(exam_room.this, "Submission Completed", Toast.LENGTH_SHORT).show();
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                })
                                .show();

                    }
                }.start();
            }
        });
    }

    public void setq(String q, String o1s, String o2s, String o3s, String o4s, int i){
        LinearLayout layout = findViewById(R.id.question);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View c = inflater.inflate(R.layout.layout, null);

        LinearLayout l = c.findViewById(R.id.llt);
        e = c.findViewById(R.id.q);
        o1 = c.findViewById(R.id.b1);
        o2 = c.findViewById(R.id.b2);
        o3 = c.findViewById(R.id.b3);
        o4 = c.findViewById(R.id.b4);
        group = c.findViewById(R.id.group);
        e.setTag("q" + i);
        group.setTag("G" + i);
        o1.setTag("o1" + i);
        o2.setTag("o2" + i);
        o3.setTag("o3" + i);
        o4.setTag("o4" + i);
        int n = i+1;
        e.setText(n + ". " + q);
        o1.setText(o1s);
        o2.setText(o2s);
        o3.setText(o3s);
        o4.setText(o4s);
        c.setLayoutParams(params);
        layout.addView(c);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object>ans = new HashMap<>();
                RadioButton o1, o2, o3, o4;
                for(int i=0;i<v;i++) {
                    View root = getWindow().getDecorView().getRootView();
                    TextView x = root.findViewWithTag("q" + i);
                    o1 = root.findViewWithTag("o1" + i);
                    o2 = root.findViewWithTag("o2" + i);
                    o3 = root.findViewWithTag("o3" + i);
                    o4 = root.findViewWithTag("o4" + i);
                    if(o1.isChecked()){
                        ans.put(x.getText().toString(), o1.getText().toString());
                    }else if(o2.isChecked()){
                        ans.put(x.getText().toString(), o2.getText().toString());
                    }else if(o3.isChecked()){
                        ans.put(x.getText().toString(), o3.getText().toString());
                    }else if(o4.isChecked()){
                        ans.put(x.getText().toString(), o4.getText().toString());
                    }
                }

                Map<String,Object>fff = new HashMap<>();
                fff.put("Null",null);
                DocumentReference ds = fs.collection("QuizSubmissions").document(sub);
                ds.set(fff);
                DocumentReference dss = ds.collection("ExamId").document(uid);
                dss.set(fff);
                dss.collection("PlayerId").document(uname).set(ans).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(exam_room.this, "Submission Completed", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            }
        });
    }
}