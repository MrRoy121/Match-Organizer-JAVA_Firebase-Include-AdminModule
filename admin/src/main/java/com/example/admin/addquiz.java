package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class addquiz extends AppCompatActivity {

    String sub;
    EditText fee, len, fst, snd, thd, num;
    Button add;
    TextView times, dates, sss;

    private int mHour, mMinute, year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquiz);


        times = findViewById(R.id.time);
        dates = findViewById(R.id.date);

        sss = findViewById(R.id.sss);
        fee = findViewById(R.id.fees);
        len = findViewById(R.id.etime);
        fst = findViewById(R.id.fst);
        snd = findViewById(R.id.scn);
        thd = findViewById(R.id.thd);
        num = findViewById(R.id.no);
        add = findViewById(R.id.add);

        Intent i = getIntent();
        if(!i.getStringExtra("ID").isEmpty()){
            sub = i.getStringExtra("ID");
            sss.setText(sub);
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(addquiz.this, new TimePickerDialog.OnTimeSetListener() {
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
            public void onClick(View view) {
                if(!(fee.getText().length() == 0)&&!(len.getText().length() == 0)&&!(fst.getText().length() == 0)&&!(snd.getText().length() == 0)&&!(num.getText().length() == 0)&&!(times.getText().length() == 0)&&!(dates.getText().length() == 0)&&!(thd.getText().length() == 0)){
                    Intent ix = new Intent(addquiz.this, addquestion.class);
                    ix.putExtra("ID", sub);
                    ix.putExtra("1", fee.getText().toString());
                    ix.putExtra("2", len.getText().toString());
                    ix.putExtra("3", fst.getText().toString());
                    ix.putExtra("4", snd.getText().toString());
                    ix.putExtra("5", thd.getText().toString());
                    ix.putExtra("6", num.getText().toString());
                    ix.putExtra("7", times.getText().toString());
                    ix.putExtra("8", dates.getText().toString());
                    startActivity(ix);
                    finish();
                }else
                    Toast.makeText(addquiz.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
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