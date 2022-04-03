package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class editmatch extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private RadioButton tppd, fppd, solo, duo, squad, era, mira;
    String region, fees, per, pers, time, fsts, thrds, scnds, max, type, uid, map;
    private TextView regions, feess, perc, times, fst, scnd, thrd, maxs, types;
    Button update;
    FirebaseFirestore fs;
    private int mHour, mMinute, year, month, day;
    TextView dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmatch);


        radioGroup1=findViewById(R.id.radioGroup1);
        radioGroup2=findViewById(R.id.radioGroup2);
        radioGroup3=findViewById(R.id.radioGroup3);
        tppd = findViewById(R.id.tpp);
        dates = findViewById(R.id.date);
        fppd = findViewById(R.id.fpp);
        solo = findViewById(R.id.solo);
        squad = findViewById(R.id.squad);
        duo = findViewById(R.id.duo);
        era = findViewById(R.id.era);
        mira = findViewById(R.id.mira);

        fs = FirebaseFirestore.getInstance();
        regions = findViewById(R.id.region);
        feess = findViewById(R.id.fees);
        perc = findViewById(R.id.perkill);
        times = findViewById(R.id.time);
        types = findViewById(R.id.type);
        maxs = findViewById(R.id.max);
        fst = findViewById(R.id.fst);
        scnd = findViewById(R.id.scn);
        thrd = findViewById(R.id.thd);
        update = findViewById(R.id.update);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            region = bundle.getString("1");
            fees = bundle.getString("2");
            per = bundle.getString("3");
            pers = bundle.getString("4");
            time = bundle.getString("5");
            fsts = bundle.getString("6");
            scnds = bundle.getString("7");
            thrds = bundle.getString("71");
            max = bundle.getString("8");
            type = bundle.getString("9");
            uid = bundle.getString("10");
            map = bundle.getString("11");

            regions.setText(region);
            feess.setText(fees);
            perc.setText(per);
            times.setText(time);
            fst.setText(fsts);
            scnd.setText(scnds);
            thrd.setText(thrds);
            maxs.setText(max);

            if(pers.equals("TPP")){
                tppd.setChecked(true);
                fppd.setChecked(false);
            }else if(pers.equals("FPP")){
                fppd.setChecked(true);
                tppd.setChecked(false);
            }if(map.equals("Erangle")){
                era.setChecked(true);
                mira.setChecked(false);
            }else if(map.equals("Mirammar")){
                mira.setChecked(true);
                era.setChecked(false);
            }
            if(type.equals("SOLO")){
                solo.setChecked(true);
                duo.setChecked(false);
                squad.setChecked(false);
            }else if(type.equals("SQUAD")){
                squad.setChecked(true);
                solo.setChecked(false);
                duo.setChecked(false);
            }else if(type.equals("DUO")){
                duo.setChecked(true);
                squad.setChecked(false);
                solo.setChecked(false);
            }

        }


        dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
                showDate(year, month+1, day);
            }
        });
        times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(editmatch.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        times.setText(String.valueOf(hourOfDay) +":"+ String.valueOf(minute));
                    }

                }, mHour, mMinute, false);
                timePickerDialog.show();


            }

        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId1=radioGroup1.getCheckedRadioButtonId();
                radioButton1= findViewById(selectedId1);
                int selectedId2=radioGroup2.getCheckedRadioButtonId();
                radioButton2= findViewById(selectedId2);
                int selectedId3=radioGroup3.getCheckedRadioButtonId();
                radioButton3= findViewById(selectedId3);


                pers = String.valueOf(radioButton1.getText());
                type = String.valueOf(radioButton2.getText());
                map = String.valueOf(radioButton3.getText());
                time = dates.getText().toString()+"T"+times.getText().toString();
                region = String.valueOf(regions.getText());
                fees = String.valueOf(feess.getText());
                per = String.valueOf(perc.getText());
                fsts = String.valueOf(fst.getText());
                scnds = String.valueOf(scnd.getText());
                thrds = String.valueOf(thrd.getText());
                max = String.valueOf(maxs.getText());

                if(map.length() == 0 && pers.length() == 0 && type.length() == 0 && time.length() == 0 && region.length() == 0 && fees.length() == 0 && per.length() == 0 && fsts.length() == 0 && scnds.length() == 0 && thrds.length() == 0 &&  max.length() == 0){
                    Toast.makeText(editmatch.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String,Object> match = new HashMap<>();
                    match.put("Region", region);
                    match.put("Time", time);
                    match.put("First", fsts);
                    match.put("Second", scnds);
                    match.put("Third", thrds);
                    match.put("Perspective", pers);
                    match.put("Type", type);
                    match.put("Map", map);
                    match.put("Per Kill", per);
                    match.put("Fees", fees);
                    match.put("Max Member", max);

                    fs.collection("Daily Matches").document(uid).set(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(editmatch.this, "Database Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate() {
        showDialog(999);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dates.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
}