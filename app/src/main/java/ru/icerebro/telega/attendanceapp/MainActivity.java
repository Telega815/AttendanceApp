package ru.icerebro.telega.attendanceapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.icerebro.telega.attendanceapp.Adapters.EmplsAdapter;
import ru.icerebro.telega.attendanceapp.client.AttendanceClient;
import ru.icerebro.telega.attendanceapp.client.ClientImitator;
import ru.icerebro.telega.attendanceapp.entities.Department;
import ru.icerebro.telega.attendanceapp.entities.Employee;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AttendanceClient attendanceClient;

    private MenuItem chosenItem;

    private AlertDialog addEmployeeDialog;
    private AlertDialog renameDepartmentDialog;


    //listeners----------------------------------------------------------
    private DialogInterface.OnClickListener addDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    Department department = new Department();
                    department.setId(chosenItem.getItemId());
                    department.setDepName(chosenItem.getTitle().toString());

                    Employee employee = new Employee();

                    employee.setDepartment(department);
                    employee.setSurname(((TextView)addEmployeeDialog.findViewById(R.id.dialog_emp_surname)).getText().toString());
                    employee.setName(((TextView)addEmployeeDialog.findViewById(R.id.dialog_emp_name)).getText().toString());
                    employee.setPatronymic(((TextView)addEmployeeDialog.findViewById(R.id.dialog_emp_patronymic)).getText().toString());
                    employee.setKey(Integer.valueOf(((TextView)addEmployeeDialog.findViewById(R.id.dialog_emp_key)).getText().toString()));

                    showNotification(attendanceClient.createEmployee(employee));

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    private DialogInterface.OnClickListener renameDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    Department department = new Department();
                    department.setId(chosenItem.getItemId());
                    department.setDepName(((TextView)renameDepartmentDialog.findViewById(R.id.dialog_dep_name)).getText().toString());


                    showNotification(attendanceClient.updateDepartment(department));

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

                    Menu menu = navigationView.getMenu();

                    menu.removeGroup(R.id.departmentsGroup);
                    List<Department> list = attendanceClient.getDepartments();

                    for (Department d:list) {
                        menu.add(R.id.departmentsGroup, d.getId(),  0, d.getDepName()).setCheckable(true);
                    }

                    MenuItem menuItem = menu.getItem(0);
                    MainActivity.this.onNavigationItemSelected(menuItem);

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
    //==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                addEmployeeDialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();

        //-------------------------------------------------------------------------------------------
        attendanceClient = ClientImitator.getINSTANCE();

        List<Department> list = attendanceClient.getDepartments();

        for (Department d:list) {
            menu.add(R.id.departmentsGroup, d.getId(),  0, d.getDepName()).setCheckable(true);
        }

        MenuItem menuItem = menu.getItem(0);
        this.onNavigationItemSelected(menuItem);
        //----------------------


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        addEmployeeDialog = dialogBuilder.setPositiveButton("Save", addDialogClickListener)
                .setNegativeButton("Cancel", addDialogClickListener)
                .setView(R.layout.dialog_empl_edit).create();

        renameDepartmentDialog = dialogBuilder.setPositiveButton("Save", renameDialogClickListener)
                .setNegativeButton("Cancel", renameDialogClickListener)
                .setView(R.layout.dialog_dep_rename).create();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar employeeItem clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dep_edit) {

            renameDepartmentDialog.show();

            return true;
        }

        if (id == R.id.action_dep_delete){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view employeeItem clicks here.
        Department department = new Department();
        department.setId(item.getItemId());
        department.setDepName(item.getTitle().toString());

        chosenItem = item;
        //int id = employeeItem.getItemId();


        if (this.getSupportActionBar() != null){
            this.getSupportActionBar().setTitle(item.getTitle());
        }

        final ListView listView = findViewById(R.id._employeeList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Delete dialog-----------------------------------------------------------------

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Employee employee = (Employee) listView.getItemAtPosition(position);

                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);

                DialogInterface.OnClickListener deleteDialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                showNotification(attendanceClient.deleteEmployee(employee));
                                MainActivity.this.onNavigationItemSelected(item);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };


                b.setMessage("Delete this employee?\n"+employee.getSurname()).setPositiveButton("Yes", deleteDialogClickListener)
                        .setNegativeButton("No", deleteDialogClickListener).show();

                return true;
            }
        });
        //----------------------------------------------------------------------------------


        final List<Employee> employees = attendanceClient.getEmployees(department);

        final EmplsAdapter adapter = new EmplsAdapter(this, employees);

        // присваиваем адаптер списку
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            private View focusedView;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmployeeActivity.setChosenEmployee(employees.get(position));
                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNotification(boolean success){
        if (success){
            Snackbar.make(findViewById(R.id.fab), "Success!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            Snackbar.make(findViewById(R.id.fab), "Failure!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
