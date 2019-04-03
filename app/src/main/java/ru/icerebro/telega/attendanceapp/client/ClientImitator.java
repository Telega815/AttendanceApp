package ru.icerebro.telega.attendanceapp.client;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ru.icerebro.telega.attendanceapp.entities.Attendance;
import ru.icerebro.telega.attendanceapp.entities.Department;
import ru.icerebro.telega.attendanceapp.entities.Employee;

public class ClientImitator implements AttendanceClient {

    private List<List<Employee>> listsOfEmpls = new ArrayList<>();

    private static ClientImitator INSTANCE = new ClientImitator();

    private List<Long> deleted = new ArrayList<>();

    private ClientImitator() {

    }

    public static ClientImitator getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public List<Department> getDepartments() {
        List<Department> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            List<Employee> employees = new ArrayList<>();
            Department department = new Department();
            department.setId(i);
            department.setDepName("Department "+ i);
            list.add(department);
            for (int j = 0; j < 16; j++) {
                Employee employee = new Employee();
                employee.setDepartment(department);
                employee.setId(j + 6*i);
                employee.setKey(1488);
                employee.setName("Anal");
                employee.setSurname("Emp "+ j);
                employee.setPatronymic("Dep " + i);
                employees.add(employee);
            }
            listsOfEmpls.add(employees);
        }
        return list;
    }

    @Override
    public List<Employee> getEmployees(Department department) {
        return listsOfEmpls.get(department.getId());
    }

    @Override
    public List<Attendance> getAttendance(Employee employee, int day, int month, int year) {

        List<Attendance> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if(deleted.contains((long) i)){
                continue;
            }
            Attendance attendance = new Attendance();
            attendance.setDay(day);
            attendance.setMonth(month);
            attendance.setaYear(year);
            attendance.setTime(new Time(System.currentTimeMillis()));
            attendance.setId(i);
            list.add(attendance);
        }

        return list;
    }

    @Override
    public boolean writeAttendance(Attendance attendance) {
        deleted.remove(deleted.size()-1);
        return true;
    }

    @Override
    public boolean deleteAttendance(Attendance attendance) {
        deleted.add(attendance.getId());
        return true;
    }

    @Override
    public void createDepartment(Department department) {

    }

    @Override
    public void createEmployee(Employee employee) {

    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
