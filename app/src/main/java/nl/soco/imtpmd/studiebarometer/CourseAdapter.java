package nl.soco.imtpmd.studiebarometer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.soco.imtpmd.studiebarometer.Models.CourseModel;

/**
 * Created by Soed on 26-05-16.
 */
public class CourseAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public CourseAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(CourseModel object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        CourseHolder courseHolder;
        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.courses_layout,parent,false);
            courseHolder = new CourseHolder();
            courseHolder.tx_name = (TextView) row.findViewById(R.id.tx_name);
            courseHolder.tx_ects = (TextView) row.findViewById(R.id.tx_ects);
            courseHolder.tx_grade = (TextView) row.findViewById(R.id.tx_grade);
            courseHolder.tx_period = (TextView) row.findViewById(R.id.tx_period);
            row.setTag(courseHolder);
        }
        else {
            courseHolder = (CourseHolder)row.getTag();
        }

        CourseModel courseModel = (CourseModel)this.getItem(position);
        courseHolder.tx_name.setText(courseModel.getName());
        courseHolder.tx_ects.setText(courseModel.getEcts());
        courseHolder.tx_grade.setText(courseModel.getGrade());
        courseHolder.tx_period.setText(courseModel.getPeriod());
        return row;
    }
    static class CourseHolder {
        TextView tx_name, tx_ects, tx_grade, tx_period;
    }
}
