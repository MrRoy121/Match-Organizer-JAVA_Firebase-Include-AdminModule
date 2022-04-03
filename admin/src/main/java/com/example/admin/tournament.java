package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class tournament extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private RadioButton tppd, fppd, solo, duo, squad, era, mira;
    FirebaseFirestore fs;
    Calendar date;
    TextView datex, lastx, namex, feesx, perkx, fstx, scndx, thrdx, maxx;
    String dates, lasts, names, feess, perks, fsts, scnds, thrds, maxs, types, pers, map;
    Button adup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        radioGroup2=findViewById(R.id.radioGroup2);
        radioGroup1=findViewById(R.id.radioGroup1);
        radioGroup2=findViewById(R.id.radioGroup2);
        datex = findViewById(R.id.date);
        lastx = findViewById(R.id.last);
        adup =findViewById(R.id.addx);
        era = findViewById(R.id.era);
        mira = findViewById(R.id.mira);


        fs = FirebaseFirestore.getInstance();
        feesx = findViewById(R.id.fees);
        perkx = findViewById(R.id.perkill);
        fstx = findViewById(R.id.fst);
        scndx = findViewById(R.id.scnd);
        thrdx = findViewById(R.id.thrd);
        maxx = findViewById(R.id.max);
        namex = findViewById(R.id.name);
        tppd = findViewById(R.id.tpp);
        fppd = findViewById(R.id.fpp);
        solo = findViewById(R.id.solo);
        squad = findViewById(R.id.squad);
        duo = findViewById(R.id.duo);



        fs.collection("Tournament").document("XYZ").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                if(d.exists()){
                        namex.setText(d.getString("Name"));
                        fstx.setText(d.getString("First"));
                        scndx.setText(d.getString("Second"));
                        thrdx.setText(d.getString("Third"));
                        lastx.setText(d.getString("Last Date"));
                        datex.setText(d.getString("Date"));
                        maxx.setText(d.getString("Max Member"));
                        perkx.setText(d.getString("Per Kill"));
                        feesx.setText(d.getString("Fees"));

                        types = d.getString("Type");
                        pers = d.getString("Perspective");
                        map = d.getString("Map");

                        if(pers.equals("TPP")){
                            tppd.setChecked(true);
                            fppd.setChecked(false);
                        }else if(pers.equals("FPP")){
                            fppd.setChecked(true);
                        }
                        if(types.equals("SOLO")){
                            solo.setChecked(true);
                            duo.setChecked(false);
                            squad.setChecked(false);
                        }else if(types.equals("SQUAD")){
                            squad.setChecked(true);
                            solo.setChecked(false);
                            duo.setChecked(false);
                        }else if(types.equals("DUO")){
                            duo.setChecked(true);
                            squad.setChecked(false);
                            solo.setChecked(false);
                        }
                        if(map.equals("Erangle")){
                        era.setChecked(true);
                        mira.setChecked(false);
                    }else if(map.equals("Mirammar")){
                        mira.setChecked(true);
                        era.setChecked(false);
                    }
                }
            }
        });

        datex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datex.setText(showDateTimePicker());
            }
        });

        lastx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastx.setText(showDateTimePicker());
            }
        });

        adup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId1=radioGroup1.getCheckedRadioButtonId();
                radioButton1= findViewById(selectedId1);
                int selectedId2=radioGroup2.getCheckedRadioButtonId();
                radioButton2= findViewById(selectedId2);
                int selectedId3=radioGroup3.getCheckedRadioButtonId();
                radioButton3= findViewById(selectedId3);

                map  = String.valueOf(radioButton3.getText());
                dates = String.valueOf(datex.getText());
                lasts = String.valueOf(lastx.getText());
                pers  = String.valueOf(radioButton1.getText());
                types  = String.valueOf(radioButton2.getText());
                names = String.valueOf(namex.getText());
                fsts = String.valueOf(fstx.getText());
                scnds = String.valueOf(scndx.getText());
                thrds = String.valueOf(thrdx.getText());
                feess = String.valueOf(feesx.getText());
                perks = String.valueOf(perkx.getText());
                maxs = String.valueOf(maxx.getText());

                if(map.length() == 0 && names.length() == 0 && types.length() == 0 && lasts.length() == 0 && dates.length() == 0 && pers.length() == 0 && fsts.length() == 0 && scnds.length() == 0 && thrds.length() == 0 && feess.length() == 0 && perks.length() == 0 && maxs.length() == 0){
                    Toast.makeText(tournament.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }else{
                    Map<String,Object> match = new HashMap<>();
                    match.put("Name", names);
                    match.put("Type", types);
                    match.put("Last Date", lasts);
                    match.put("Date", dates);
                    match.put("Perspective", pers);
                    match.put("First", fsts);
                    match.put("Second", scnds);
                    match.put("Third", thrds);
                    match.put("Max Member", maxs);
                    match.put("Fees", feess);
                    match.put("Map", map);
                    match.put("Per Kill", perks);

                    Log.e("AARAsda", String.valueOf(match));
                    fs.collection("Tournament").document("XYZ").set(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(tournament.this, "Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

            }
        });

    }

    public String showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(tournament.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.e("TAG", "The choosen one " + date.getTime());
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        return  String.valueOf(date.getTime());
    }
}