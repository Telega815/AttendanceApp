package ru.icerebro.telega.attendanceapp.client;

import java.util.List;

import ru.icerebro.telega.attendanceapp.entities.Attendance;
import ru.icerebro.telega.attendanceapp.entities.Department;
import ru.icerebro.telega.attendanceapp.entities.Employee;

public interface AttendanceClient {

    List<Department> getDepartments();
    List<Employee> getEmployees(Department department);
    List<Attendance> getAttendance(Attendance attendance);

    void writeAttendance(Attendance attendance);
    void createDepartment(Department department);
    void createEmployee(Employee employee);

    boolean isConnected();
}