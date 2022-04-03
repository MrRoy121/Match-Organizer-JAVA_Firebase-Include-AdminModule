package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginRegister extends AppCompatActivity {

    FirebaseAuth fauth;
    FirebaseFirestore fs;
    EditText emails, passs, names;
    String email, pass, name;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        fauth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        emails = findViewById(R.id.email);
        passs = findViewById(R.id.pass);
        names = findViewById(R.id.name);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = String.valueOf(emails.getText());
                pass = String.valueOf(passs.getText());
                if( email.length()==0 && pass.length()==0){
                    Toast.makeText(LoginRegister.this, "Email Password is Required", Toast.LENGTH_SHORT).show();
                }else{
                    fauth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(
                                                @NonNull Task<AuthResult> task)
                                        {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(LoginRegister.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = String.valueOf(emails.getText());
                pass = String.valueOf(passs.getText());
                name = String.valueOf(names.getText());

                Map<String,Object> user = new HashMap<>();
                user.put("Name", name);
                user.put("Email", email);
                user.put("Password", pass);


                if( email.length()==0 && pass.length()==0 && name.length()== 0){
                    Toast.makeText(LoginRegister.this, "Email Password And Name is Required", Toast.LENGTH_SHORT).show();
                }else{
                    fauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = task.getResult().getUser().getUid();
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                fs.collection("Admin").document(userId).set(user);

                                Intent intent = new Intent(LoginRegister.this, MainActivity.class);startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}