package exampleoncreatingfixedfragment.example.com.androidproject.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import exampleoncreatingfixedfragment.example.com.androidproject.Models.Assignment;
import exampleoncreatingfixedfragment.example.com.androidproject.R;

/**
 * Created by 450 G1 on 18/05/2017.
 */

public class AssignmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Assignment> list = new ArrayList<>();

    public AssignmentAdapter(Context c, ArrayList<Assignment> list) {
        this.context = c;
        this.list = list;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.assignment_student_adapter_item, parent,false);

        TextView topic = (TextView) convertView.findViewById(R.id.topic);
        topic.setText(list.get(position).getInstruction());

        TextView count = (TextView) convertView.findViewById(R.id.count_assignment);
        count.setText(Integer.toString(position+1)+".");
        return convertView;
    }
}
