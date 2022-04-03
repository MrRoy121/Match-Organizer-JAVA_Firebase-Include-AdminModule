package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class addquestion extends AppCompatActivity {


    FirebaseFirestore fs;
    TextView sss;
    String sub, fee, len, fst, snd, thd, num, time, date;
    Integer v;
    EditText e, o1, o2, o3, o4;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);


        fs = FirebaseFirestore.getInstance();
        sss = findViewById(R.id.sss);
        add = findViewById(R.id.add);

        Intent ic = getIntent();
        if(!ic.getStringExtra("ID").isEmpty()){
            sub = ic.getStringExtra("ID");
            fee = ic.getStringExtra("1");
            len = ic.getStringExtra("2");
            fst = ic.getStringExtra("3");
            snd = ic.getStringExtra("4");
            thd = ic.getStringExtra("5");
            num = ic.getStringExtra("6");
            time = ic.getStringExtra("7");
            date = ic.getStringExtra("8");

            v = Integer.parseInt(num);
            sss.setText(sub);


            for(int i=0;i<v;++i)
            {    LinearLayout layout = findViewById(R.id.lt);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);

                LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View c = inflater.inflate(R.layout.layout, null);

                LinearLayout l = c.findViewById(R.id.llt);
                e = c.findViewById(R.id.q);
                o1 = c.findViewById(R.id.o1);
                o2 = c.findViewById(R.id.o2);
                o3 = c.findViewById(R.id.o3);
                o4 = c.findViewById(R.id.o4);
                e.setTag("q" + i);
                o1.setTag("o1" + i);
                o2.setTag("o2" + i);
                o3.setTag("o3" + i);
                o4.setTag("o4" + i);
                int n = i+1;
                e.setHint("Question No " +n);
                c.setLayoutParams(params);
                layout.addView(c);

            }

            Map<String, Object> docData = new HashMap<>();
            Map<String, Object> noll = new HashMap<>();
            noll.put("null", null);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    docData.put("Fees", fee);
                    docData.put("Length", len);
                    docData.put("First", fst);
                    docData.put("Second", snd);
                    docData.put("Third", thd);
                    docData.put("Time", time);
                    docData.put("Date", date);
                    docData.put("Question", num);
                    DocumentReference ds = fs.collection("Quiz").document(sub);
                    ds.set(noll);
                    ds.collection("ExamId").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference de) {
                            int x = 0;
                            for(int i=0;i<v;++i) {
                                x = i + 1;
                                View root = getWindow().getDecorView().getRootView();
                                EditText xCurrentBox = root.findViewWithTag("q" + i);
                                EditText a1 = root.findViewWithTag("o1" + i);
                                EditText a2 = root.findViewWithTag("o2" + i);
                                EditText a3 = root.findViewWithTag("o3" + i);
                                EditText a4 = root.findViewWithTag("o4" + i);
                                Map<String, Object> op = new HashMap<>();
                                op.put("Option", Arrays.asList(a1.getText().toString(), a2.getText().toString(), a3.getText().toString(), a4.getText().toString()));
                                op.put("Question", xCurrentBox.getText().toString());
                                fs.collection("Quiz").document(sub).collection("ExamId").document(de.getId()).collection("Question").document(String.valueOf(x)).set(op);
                            }
                         }
                    });
                    Toast.makeText(addquestion.this, "Added", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }

    }
}