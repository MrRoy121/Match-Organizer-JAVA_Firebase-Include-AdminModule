package com.example.bph_esports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class profile extends AppCompatActivity {

    TextView uname, fname, email, phn;
    FirebaseFirestore fs;
    FirebaseAuth fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();

        uname = findViewById(R.id.uname);
        fname = findViewById(R.id.fname);
        email = findViewById(R.id.emaiil);
        phn = findViewById(R.id.phn);



        fs.collection("users").whereEqualTo("UserName", fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d: q){
                        uname.setText(d.getId());
                        fname.setText(d.getString("FullName"));
                        email.setText(d.getString("Email"));
                        phn.setText(d.getString("Phone"));
                    }
                }
            }
        });

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(profile.this, "Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });


    }
}