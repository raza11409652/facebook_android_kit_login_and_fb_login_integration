package com.flatondemand.user.fod;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class VisitBooking extends AppCompatActivity {
Button btnDate , btnTime;
String time =null, date=null ;
EditText timeEdit , dateEdit;
Calendar calendar,mcurrentTime ;
    HorizontalCalendarView horizontalCalendarView;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_booking);
       dateEdit = (EditText)findViewById(R.id.dateEdit);
       timeEdit = (EditText)findViewById(R.id.timeEdit);
        calendar = Calendar.getInstance();
        mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY) + 1;
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
               // updateLabel();
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                //Toast.makeText(getApplicationContext() , ""+sdf.format(calendar.getTime()) , Toast.LENGTH_SHORT).show();
                dateEdit.setText(sdf.format(calendar.getTime()).toString());
            }
        };

       dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(VisitBooking.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
            timeEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(VisitBooking.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    timeEdit.setText(hourOfDay+":"+minute);
                                }
                            }, hour, minute, false);
                    timePickerDialog.show();

                }
            });
    }

}

