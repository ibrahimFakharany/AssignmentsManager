package exampleoncreatingfixedfragment.example.com.androidproject.Student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import exampleoncreatingfixedfragment.example.com.androidproject.Models.Course;
import exampleoncreatingfixedfragment.example.com.androidproject.Models.StudentCourseAssignment;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;
import exampleoncreatingfixedfragment.example.com.androidproject.Student.Adapters.MainAdapter;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private int student_id;
    private String firstname, lastname, phonenumber, username, password;
    Toolbar toolbar;
    ArrayList<Course> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        getIntentVlaues();
        getStudentCourseAssignment();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(firstname+ " " +lastname);
        setSupportActionBar(toolbar);

    }

    private void getStudentCourseAssignment() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/retrieve/getStudentCourseAssignment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new CustomToast(getBaseContext()).raiseToast(response);
                try {
                    JSONObject root = new JSONObject(response);

                    if(root.getInt("success") == 1){
                        JSONArray message = root.getJSONArray("message");
                        JSONObject jsonObject = null;
                        for(int i = 0 ;  i < message.length(); i++){
                            jsonObject = message.getJSONObject(i);
                            list.add(new Course(
                                    jsonObject.getInt("doctor_id"),
                                    student_id,
                                    Integer.parseInt(jsonObject.getString("coursenumber")),
                                    jsonObject.getString("course_name"),
                                    jsonObject.getString("point"),
                                    jsonObject.getString("semester")
                                    ));
                        }
                        ListView listView = (ListView) findViewById(R.id.list_view_student);
                        MainAdapter adapter= new MainAdapter(MainActivity.this, list, MainActivity.this);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });
                    }else{
                        new CustomToast(getBaseContext()).raiseToast("no data found");
                    }
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
                HashMap<String, String> map = new HashMap<>();
                map.put("student_id", Integer.toString(student_id));
                return map;
            }
        };
        SingleTon.getInstance(this).addRequest(stringRequest);
    }

    private void getIntentVlaues() {
        student_id = intent.getIntExtra("student_id",0);
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        username = intent.getStringExtra("username");
        phonenumber = intent.getStringExtra("phonenumber");
        password = intent.getStringExtra("password");
    }


}
