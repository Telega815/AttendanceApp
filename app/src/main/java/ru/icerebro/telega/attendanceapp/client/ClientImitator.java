package ru.icerebro.telega.attendanceapp.client;

import java.util.ArrayList;
import java.util.List;

import ru.icerebro.telega.attendanceapp.entities.Attendance;
import ru.icerebro.telega.attendanceapp.entities.Department;
import ru.icerebro.telega.attendanceapp.entities.Employee;

public class ClientImitator implements AttendanceClient {

    List<List<Employee>> listsOfEmpls = new ArrayList<>();

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

        return null;
    }

    @Override
    public void writeAttendance(Attendance attendance) {

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
