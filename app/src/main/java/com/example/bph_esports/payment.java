package com.example.bph_esports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;

import com.example.bph_esports.R;

public class payment extends AppCompatActivity {

    String region, fees, per, pers, time, roomid, roompass, max, type, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            region = bundle.getString("1");
            fees = bundle.getString("2");
            per = bundle.getString("3");
            pers = bundle.getString("4");
            time = bundle.getString("5");
            roomid = bundle.getString("6");
            roompass = bundle.getString("7");
            max = bundle.getString("8");
            type = bundle.getString("9");
            uid = bundle.getString("10");


        }
    }
}