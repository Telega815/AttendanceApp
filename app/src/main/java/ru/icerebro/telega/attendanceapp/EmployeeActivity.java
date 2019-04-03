package ru.icerebro.telega.attendanceapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ru.icerebro.telega.attendanceapp.Adapters.AttendanceAdapter;
import ru.icerebro.telega.attendanceapp.client.AttendanceClient;
import ru.icerebro.telega.attendanceapp.client.ClientImitator;
import ru.icerebro.telega.attendanceapp.entities.Attendance;
import ru.icerebro.telega.attendanceapp.entities.Employee;

public class EmployeeActivity extends AppCompatActivity {

    private Calendar calendar;

    int myYear = 2019;
    int myMonth = 2;
    int myDay = 29;

    int myHour = 0;
    int myMinute = 0;

    TextView textView;

    private Attendance selectedAttendance;

    private AttendanceClient attendanceClient;

    private static Employee choosenEmployee;


    TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



            Attendance attendance = new Attendance();
            attendance.setaYear(myYear);
            attendance.setMonth(myMonth);
            attendance.setDay(myDay);
            calendar.set(myYear, myMonth, myDay, hourOfDay, minute);
            attendance.setTime(new Time(calendar.getTime().getTime()));
            attendance.setId(0);
            attendanceClient.writeAttendance(attendance);

            refreshAttlist();
        }
    };


    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            calendar.set(myYear, myMonth, myDay);
            textView.setText(myDay + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + myYear);

            refreshAttlist();
        }
    };

    private AlertDialog.Builder builder;
    private DialogInterface.OnClickListener deleteDialogClickListener;
    private DialogInterface.OnClickListener addDialogClickListener;

    public static void setChoosenEmployee(Employee choosenEmployee) {
        EmployeeActivity.choosenEmployee = choosenEmployee;
    }

    private void refreshAttlist(){
        final ListView listView = findViewById(R.id._AttendanceList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        List<Attendance> attendanceList = attendanceClient.getAttendance(choosenEmployee, myDay, myMonth, myYear);

        final AttendanceAdapter adapter = new AttendanceAdapter(EmployeeActivity.this, attendanceList);

        // присваиваем адаптер списку
        listView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        Toolbar toolbar = findViewById(R.id.toolbar_emplActiv);
        setSupportActionBar(toolbar);

        calendar = new GregorianCalendar();

        //DatePick---------------------------------------------------------------------------------------------------------------
        textView = findViewById(R.id.dateView);

        textView.setText(myDay + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + myYear);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = new GregorianCalendar();

                myYear = calendar.get(Calendar.YEAR);
                myMonth = calendar.get(Calendar.MONTH);
                myDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog tpd = new DatePickerDialog(EmployeeActivity.this, myCallBack, myYear, myMonth, myDay);
                tpd.show();
            }
        });
        //-----------------------------------------------------------------------------------------------------------------------



        attendanceClient = ClientImitator.getINSTANCE();

        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().setTitle(choosenEmployee.getSurname() + " " + choosenEmployee.getName());

        myCallBack.onDateSet(null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));




        //Add dialog--------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddAttendance);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = new GregorianCalendar();
                myHour = calendar.get(Calendar.HOUR_OF_DAY);
                myMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(EmployeeActivity.this, myTimeSetListener, myHour, myMinute, true);
                tpd.show();
            }
        });
        //---------------------------------------------------------------------------------------------





        //Delete dialog-----------------------------------------------------------------
        deleteDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        attendanceClient.deleteAttendance(selectedAttendance);
                        myCallBack.onDateSet(null, myYear, myMonth, myDay);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        builder = new AlertDialog.Builder(this);

        final ListView listView = findViewById(R.id._AttendanceList);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Attendance attendance = (Attendance) listView.getItemAtPosition(position);
                selectedAttendance = attendance;
                builder.setMessage("Delete this attendance?\n"+attendance.getTime().toString()).setPositiveButton("Yes", deleteDialogClickListener)
                        .setNegativeButton("No", deleteDialogClickListener).show();
                return false;
            }
        });
        //----------------------------------------------------------------------------------
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar employeeItem clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            AlertDialog dialog = builder.setPositiveButton("Yes", deleteDialogClickListener)
                    .setNegativeButton("No", deleteDialogClickListener)
                    .setView(R.layout.dialog_empl_edit).create();

//            TextView tv = dialog.findViewById(R.id.dialog_header_text);
//            tv.setText(choosenEmployee.getSurname() + " " + choosenEmployee.getName());

            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
