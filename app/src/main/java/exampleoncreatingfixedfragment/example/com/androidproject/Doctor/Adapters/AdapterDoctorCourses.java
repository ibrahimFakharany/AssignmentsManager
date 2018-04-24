package exampleoncreatingfixedfragment.example.com.androidproject.Doctor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import java.util.List;
import java.util.Map;

import exampleoncreatingfixedfragment.example.com.androidproject.Doctor.DetailCourseActivity;
import exampleoncreatingfixedfragment.example.com.androidproject.Models.Course;
import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;

/**
 * Created by 450 G1 on 12/05/2017.
 */

public class AdapterDoctorCourses extends BaseAdapter {
    private Context mContext;
    private ArrayList<Course> list=new ArrayList<>();
    private int doctor_id= 0;
    public AdapterDoctorCourses(ArrayList<Course> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        doctor_id = list.get(0).getDoctor_id();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.adapter_doctor_main_item,parent, false);

        TextView coursename = (TextView) convertView.findViewById(R.id.adapter_item_doctor_course_name);

        final RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mContext, DetailCourseActivity.class);
                intent1.putExtra("doctor_id", list.get(position).getDoctor_id());
                intent1.putExtra("coursenumber", list.get(position).getCourse_number());
                intent1.putExtra("coursename", list.get(position).getCourse_name());
                intent1.putExtra("coursepoint", list.get(position).getCourse_point());
                intent1.putExtra("semster", list.get(position).getCourse_semster());
                intent1.putExtra("doctorOrStudent",1);

                mContext.startActivity(intent1);
            }
        });

        coursename.setText(list.get(position).getCourse_name());

        ImageButton deletCourse = (ImageButton) convertView.findViewById(R.id.delete_btn);
        deletCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete ccourse
                StringRequest stringRequest = new StringRequest(Request.Method.POST, mContext.getString(R.string.ip) + "/delete/deleteAssignedCourseToDoctor.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST
                                , mContext.getString(R.string.ip)+"/retrieve/getDoctorCourses.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ArrayList<Course> list1 = new ArrayList<Course>();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getInt("success") == 1){
                                        new CustomToast(mContext).raiseToast("success = 1 after deleting");
                                        JSONArray results = jsonObject.getJSONArray("message");
                                        JSONObject objectI = null;
                                        for(int i = 0 ; i < results.length(); i++){
                                            objectI = results.getJSONObject(i);
                                            list1.add(new Course(doctor_id,
                                                    objectI.getInt("coursenumber"),
                                                    objectI.getString("course_name"),
                                                    objectI.getString("point"),
                                                    objectI.getString("semester")));
                                        }
                                        System.out.println("new courses*****");
                                        for(int i = 0 ; i < list1.size(); i++){
                                            System.out.println(i+"  "+list1.get(i).getCourse_name());
                                        }

                                    }else{
                                        new CustomToast(mContext).raiseToast("No courses found  "+Integer.toString(jsonObject.getInt("success")));
                                    }
                                    refreshEvents(list1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("doctor_id", Integer.toString(list.get(position).getDoctor_id()));
                                return map;
                            }
                        };
                        SingleTon.getInstance(mContext).addRequest(stringRequest1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("doctor_id", list.get(position).getDoctor_id()+"");
                        map.put("course_id", list.get(position).getCourse_number()+"");
                        return map;
                    }
                };
                SingleTon.getInstance(mContext).addRequest(stringRequest);

            }

        });
        return convertView;
    }

    public void refreshEvents(List<Course> events) {
        this.list.clear();
        this.list.addAll(events);
        notifyDataSetChanged();
    }
}
