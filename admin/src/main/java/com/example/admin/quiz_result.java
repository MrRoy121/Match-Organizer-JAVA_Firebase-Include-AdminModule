package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class quiz_result extends AppCompatActivity {

    TextView subt, uidt, q, o;
    String sub, qid, uid;
    FirebaseFirestore fs;
    Button submit;
    int v, right = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        fs = FirebaseFirestore.getInstance();
        subt = findViewById(R.id.sub);
        uidt = findViewById(R.id.uid);
        submit = findViewById(R.id.submit);


        Intent i = getIntent();
        if(!i.getStringExtra("ID").isEmpty()){
            sub = i.getStringExtra("ID");
            qid = i.getStringExtra("ID1");
            uid = i.getStringExtra("ID2");
            subt.setText(sub);
            uidt.setText(uid);

            fs.collection("QuizSubmissions").document(sub).collection("ExamId").document(qid).collection("PlayerId").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot d) {
                    if(d.exists()){
                        Log.e("Ta2", String.valueOf(d.getData()));
                        Map<String, Object> a = d.getData();
                        Log.e("Ta2", String.valueOf(a));
                        StringTokenizer t = new StringTokenizer(String.valueOf(d.getData()), "{");
                        String s = t.nextToken();
                        StringTokenizer t2 = new StringTokenizer(s, "}");
                        String str = t2.nextToken();
                        Map<String, String> map = new HashMap<String, String>();

                        String[] strArray = str.split(", ");
                        v = strArray.length;
                        for (int i = 0; i < strArray.length; i++) {
                            String data = strArray[i];
                            String[] keyValue = data.split("=");
                            map.put(keyValue[0], keyValue[1]);

                            ms(keyValue[0], keyValue[1], i);
                        }
                    }
                }
            });
        }
    }

    public void ms(String q, String o, int i){

        LinearLayout layout = findViewById(R.id.question);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View c = inflater.inflate(R.layout.layout1, null);
        LinearLayout l = c.findViewById(R.id.la);
        TextView ea = c.findViewById(R.id.q);
        TextView oa = c.findViewById(R.id.o);
        RadioGroup g = c.findViewById(R.id.radioGroup);
        RadioButton t = c.findViewById(R.id.radioButton);
        g.setTag("g"+i);
        ea.setText(q);
        oa.setText(o);
        t.setTag("t" + i);
        c.setLayoutParams(params);
        layout.addView(c);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<v;i++) {
                    View root = getWindow().getDecorView().getRootView();
                    RadioButton t;
                    t = root.findViewWithTag("t" + i);
                    if(t.isChecked()){
                        right=right+1;
                    }
                }

                Map<String, Object> noll = new HashMap<>();
                noll.put("null",null);
                Map<String, Object> res = new HashMap<>();
                res.put("Correct", right);

                DocumentReference p = fs.collection("QuizSubmissions").document(sub).collection("ExamId").document(qid);
                p.collection("PlayerId").document(uid).delete();
                DocumentReference r = fs.collection("QuizResults").document(sub);
                r.set(noll);
                DocumentReference r1 = r.collection("ExamId").document(qid);
                r1.set(noll);
                r1.collection("PlayerId").document(uid).set(res);
                Toast.makeText(quiz_result.this, "Added", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}