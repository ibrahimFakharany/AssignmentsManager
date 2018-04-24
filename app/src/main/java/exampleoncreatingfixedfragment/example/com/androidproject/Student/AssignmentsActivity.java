package exampleoncreatingfixedfragment.example.com.androidproject.Student;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import exampleoncreatingfixedfragment.example.com.androidproject.Models.Assignment;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;
import exampleoncreatingfixedfragment.example.com.androidproject.Student.Adapters.AssignmentAdapter;

public class AssignmentsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    Intent intent1;
    int course_number, seen, studentId;
    String course_name, course_point, course_semster, assignment_id, instruction, description, startTime, endTime, typeOfWork;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Assignments");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent1 = getIntent();
        defineViews();
        getIntentValues();
        getAssignments();
    }

    private void getAssignments() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/retrieve/getAssignments.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final ArrayList<Assignment> list = new ArrayList<>();
                try {
                    JSONObject root = new JSONObject(response);

                    int state = root.getInt("success");
                    if (state == 1) {

                        System.out.println("success = 1");
                        JSONArray array = root.getJSONArray("message");
                        JSONObject jsonObjectI = null;
                        for (int i = 0; i < array.length(); i++) {
                            jsonObjectI = array.getJSONObject(i);
                            list.add(new Assignment(
                                    jsonObjectI.getInt("assignment_id"),
                                    course_number,
                                    jsonObjectI.getString("instruction"),
                                    jsonObjectI.getString("description"),
                                    jsonObjectI.getString("starttime"),
                                    jsonObjectI.getString("endtime"),
                                    jsonObjectI.getString("type_of_work")
                                    ));
                        }
                    } else {
                        System.out.println("success = zero");
                    }
                } catch (JSONException ex) {
                }
                listView = (ListView) findViewById(R.id.assignments_list_studdent);
                final AssignmentAdapter assignmentAdapter = new AssignmentAdapter(getBaseContext(), list);
                listView.setAdapter(assignmentAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(getBaseContext(), QuestionsMultiActivity.class);
                            intent1.putExtra("assignment_id", list.get(position).getAssignment_id());

                            intent.putExtras(intent1);
                            startActivity(intent);
                            finish();

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new CustomToast(getBaseContext()).raiseToast(error.toString()
                );
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("course_number", Integer.toString(course_number));
                return map;
            }
        };
        SingleTon.getInstance(this).addRequest(stringRequest);
    }

    private void getIntentValues() {
        course_number = intent1.getIntExtra("course_number", 0);
        studentId = intent1.getIntExtra("student_id", 0);
    }

    private void defineViews() {

    }
}
