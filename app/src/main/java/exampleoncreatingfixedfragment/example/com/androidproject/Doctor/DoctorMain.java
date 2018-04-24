package exampleoncreatingfixedfragment.example.com.androidproject.Doctor;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

import exampleoncreatingfixedfragment.example.com.androidproject.Doctor.Adapters.AdapterDoctorCourses;
import exampleoncreatingfixedfragment.example.com.androidproject.Models.Course;
import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;

public class DoctorMain extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    ImageButton addCourseBtn;
    int doctor_id;
    ListView listView;
    ArrayList<Course>  list ;
    AdapterDoctorCourses adapterDoctorCourses;
    Toolbar toolbar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(this, DoctorProfileActivity.class).putExtras(intent));
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Doctor");

        defineView();
        intent = getIntent();
        doctor_id = intent.getIntExtra("doctor_id",1);
        getIntentValues();
        getDoctorCourses();
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void getIntentValues() {
        String firstname = intent.getStringExtra("firstname");
        String lastname= intent.getStringExtra("lastname");
        String username = intent.getStringExtra("username");
        String phonenumber = intent.getStringExtra("phonenumber");
        String address = intent.getStringExtra("lastname");
        String password = intent.getStringExtra("password");

        toolbar.setTitle("Dr "+firstname+" "+lastname);

    }

    private void getDoctorCourses() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip)+"/retrieve/getDoctorCourses.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject root = new JSONObject(response);
                    int state = root.getInt("success");
                    if (state == 1) {
                        System.out.println("success = 1");
                        JSONArray array = root.getJSONArray("message");
                        list = new ArrayList<>();
                        JSONObject jsonObjectI = null;
                        for (int i = 0; i < array.length();i++){
                            jsonObjectI = array.getJSONObject(i);
                            list.add(new Course(doctor_id
                                    , jsonObjectI.getInt("coursenumber")
                                    , jsonObjectI.getString("course_name")
                                    , jsonObjectI.getString("point")
                                    , jsonObjectI.getString("semester")));
                        }
                        for(int i = 0 ; i < list.size(); i++){
                            new CustomToast(getBaseContext()).raiseToast(list.get(i).getDoctor_id()+"  "+list.get(i).getCourse_number()+"  "+list.get(i).getCourse_name()+"  "+list.get(i).getCourse_point()+"  "+list.get(i).getCourse_semster()+"  ");
                        }

                        listView = (ListView) findViewById(R.id.doctor_main_listview);

                        adapterDoctorCourses = new AdapterDoctorCourses(list, getBaseContext());
                        listView.setAdapter(adapterDoctorCourses);
                    } else {
                        System.out.println("success = zero");
                    }
                } catch (JSONException ex){
                    System.out.println("json exception ****** here");
                    ex.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new CustomToast(getBaseContext()).raiseToast("error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("doctor_id", doctor_id+"");
                return map;
            }
        };
        SingleTon.getInstance(this).addRequest(stringRequest);
    }

    private void defineView() {
        addCourseBtn = (ImageButton) findViewById(R.id.add_course_btn);
        addCourseBtn.setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_course_btn:
                Intent i = new Intent(this, AddCourseActivity.class);
                i.putExtras(intent);
                startActivity(i);
                finish();
                return;


        }
    }
}
