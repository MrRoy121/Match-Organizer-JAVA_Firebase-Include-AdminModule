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
import android.widget.EditText;
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

public class addmatch extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private Button add;
    TextView times, dates;
    private int mHour, mMinute, year, month, day;
    EditText regionx, feex, perx, maxx, fst, snd, thd;
    String region, fee, per, max, time, type, pers, map, fsts, snds, thds;
    FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmatch);

        fs = FirebaseFirestore.getInstance();
        times = findViewById(R.id.time);
        dates = findViewById(R.id.date);
        regionx = findViewById(R.id.region);
        feex = findViewById(R.id.fees);
        perx = findViewById(R.id.perkill);
        maxx = findViewById(R.id.max);

        fst =  findViewById(R.id.fst);
        snd =  findViewById(R.id.scn);
        thd =  findViewById(R.id.thd);

        radioGroup1=findViewById(R.id.radioGroup1);
        radioGroup2=findViewById(R.id.radioGroup2);
        radioGroup3=findViewById(R.id.radioGroup3);

        add = findViewById(R.id.add);

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

                TimePickerDialog timePickerDialog = new TimePickerDialog(addmatch.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                times.setText(String.valueOf(hourOfDay) +":"+ String.valueOf(minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();


            }

        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                region = String.valueOf(regionx.getText());
                fee = String.valueOf(feex.getText());
                per = String.valueOf(perx.getText());
                fsts = String.valueOf(fst.getText());
                snds = String.valueOf(snd.getText());
                thds = String.valueOf(thd.getText());
                max = String.valueOf(maxx.getText());


                if(map.length() == 0 && pers.length() == 0 && type.length() == 0 && time.length() == 0 && region.length() == 0 && fee.length() == 0 && per.length() == 0 && fsts.length() == 0 && snds.length() == 0 && thds.length() == 0 && max.length() == 0){
                    Toast.makeText(addmatch.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(addmatch.this, time, Toast.LENGTH_SHORT).show();
                    Map<String,Object> match = new HashMap<>();
                    match.put("Region", region);
                    match.put("First", fsts);
                    match.put("Second", snds);
                    match.put("Third", thds);
                    match.put("Time", time);
                    match.put("Perspective", pers);
                    match.put("Type", type);
                    match.put("Map", map);
                    match.put("Per Kill", per);
                    match.put("Fees", fee);
                    match.put("Max Member", max);

                    fs.collection("Daily Matches").add(match).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(addmatch.this, "Data Added To Database", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addmatch.this, "Failed///", Toast.LENGTH_SHORT).show();
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
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dates.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
}