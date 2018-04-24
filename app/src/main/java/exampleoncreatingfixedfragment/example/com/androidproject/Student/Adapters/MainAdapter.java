package exampleoncreatingfixedfragment.example.com.androidproject.Student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.Doctor.DetailCourseActivity;
import exampleoncreatingfixedfragment.example.com.androidproject.Models.Course;
import exampleoncreatingfixedfragment.example.com.androidproject.Models.StudentCourseAssignment;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;
import exampleoncreatingfixedfragment.example.com.androidproject.Student.AssignmentsActivity;
import exampleoncreatingfixedfragment.example.com.androidproject.Student.CourseDetailWithAssignments;
import exampleoncreatingfixedfragment.example.com.androidproject.Student.MainActivity;

/**
 * Created by 450 G1 on 18/05/2017.
 */

public class MainAdapter extends BaseAdapter {
    Context context;
    ArrayList<Course> list = new ArrayList<>();
    int courseNumber;
    MainActivity mainActivity;
    public MainAdapter(Context c, ArrayList<Course> list ,MainActivity mainActivity) {
        this.context = c;
        this.list = list;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return this.list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.adapter_student_main_item, parent, false);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.student_adapter_linear);
        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.icon);

        TextView coursename = (TextView) convertView.findViewById(R.id.course_name_tv_student_list_main_item);
        coursename.setText(list.get(position).getCourse_name());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomToast(context).raiseToast("frame layout");
                Intent intent = new Intent(context, AssignmentsActivity.class);
                intent.putExtra("student_id", list.get(position).getStudent_id());
                intent.putExtra("course_number", list.get(position).getCourse_number());
                intent.putExtra("course_name", list.get(position).getCourse_name());
                intent.putExtra("coure_point", list.get(position).getCourse_point());
                intent.putExtra("course_semster", list.get(position).getCourse_semster());
                context.startActivity(intent);

            }
        });


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailCourseActivity.class);
                intent.putExtra("doctorOrStudent", 0);
                intent.putExtra("doctor_id",list.get(position).getDoctor_id());
                intent.putExtra("coursenumber", list.get(position).getCourse_number());
                intent.putExtra("coursename", list.get(position).getCourse_name());
                intent.putExtra("coursepoint", list.get(position).getCourse_point());
                intent.putExtra("semster", list.get(position).getCourse_semster());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
