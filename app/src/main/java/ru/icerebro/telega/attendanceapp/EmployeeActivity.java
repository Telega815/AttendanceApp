package ru.icerebro.telega.attendanceapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EmployeeActivity extends AppCompatActivity {

    private Calendar calendar;

    int myYear = 2019;
    int myMonth = 2;
    int myDay = 29;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        calendar = new GregorianCalendar();

        myYear = calendar.get(Calendar.YEAR);
        myMonth = calendar.get(Calendar.MONTH);
        myDay = calendar.get(Calendar.DAY_OF_MONTH);

        textView = findViewById(R.id.dateView);

        textView.setText(myDay + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + myYear);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog tpd = new DatePickerDialog(EmployeeActivity.this, myCallBack, myYear, myMonth, myDay);
                tpd.show();
            }
        });
    }


    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            calendar.set(myYear, myMonth, myDay);
            textView.setText(myDay + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + myYear);
        }
    };
}
