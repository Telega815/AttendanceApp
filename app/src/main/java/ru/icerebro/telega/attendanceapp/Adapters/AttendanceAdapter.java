package ru.icerebro.telega.attendanceapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.icerebro.telega.attendanceapp.R;
import ru.icerebro.telega.attendanceapp.entities.Attendance;

public class AttendanceAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private List<Attendance> objects;

    public AttendanceAdapter(Context context, List<Attendance> products) {
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
            view = lInflater.inflate(R.layout.attendance_item, parent, false);
        }

        Attendance attendance = (Attendance) getItem(position);

        ((TextView) view.findViewById(R.id.textViewAttendance)).setText(attendance.getTime().toString() +" "+ attendance.getDay()+"."+attendance.getMonth()+"."+ attendance.getaYear());

        return view;
    }
}
