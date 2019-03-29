package ru.icerebro.telega.attendanceapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.icerebro.telega.attendanceapp.R;
import ru.icerebro.telega.attendanceapp.entities.Employee;

public class EmplsAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private List<Employee> objects;

    public EmplsAdapter(Context context, List<Employee> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.employee_item, parent, false);
        }

        Employee employee = (Employee) getItem(position);

        ((TextView) view.findViewById(R.id.emp_surname)).setText(employee.getSurname());
        ((TextView) view.findViewById(R.id.emp_name)).setText(employee.getName());
        ((TextView) view.findViewById(R.id.emp_patronymic)).setText(employee.getPatronymic());

        ((TextView) view.findViewById(R.id.emp_id)).setText("id: "+employee.getId());
        ((TextView) view.findViewById(R.id.emp_key)).setText("key: "+employee.getKey());

        return view;
    }
}
