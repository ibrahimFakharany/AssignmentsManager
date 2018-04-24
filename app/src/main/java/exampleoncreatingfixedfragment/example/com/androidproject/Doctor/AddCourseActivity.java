package exampleoncreatingfixedfragment.example.com.androidproject.Doctor;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mCrsNum, mCrsName, mCrsPoint, mCrsSemster;
    private Button mAddBtn;
    int doctor_id;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        intent  = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setTitle("Add course");

        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        doctor_id = getIntent().getIntExtra("doctor_id",0);

        defineView();
    }


    private void defineView() {
        mCrsNum = (EditText) findViewById(R.id.course_num_edt);
        mCrsName   = (EditText) findViewById(R.id.course_name_edt);
        mCrsPoint = (EditText) findViewById(R.id.course_point_edt);
        mCrsSemster = (EditText) findViewById(R.id.course_semster_edt);
        mAddBtn = (Button) findViewById(R.id.course_add_btn);
        mAddBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        StringRequest stringRequest;
        switch (v.getId()){
            case R.id.course_add_btn:
                stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/insert/insertcoursedata.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new CustomToast(getBaseContext()).raiseToast(response);
                        Intent intent1 = new Intent(getBaseContext(), DoctorMain.class);
                        intent1.putExtras(intent);
                        startActivity(intent1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getStackTrace());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap map = new HashMap();
                        map.put("course_num",mCrsNum.getText().toString());
                        map.put("course_name",mCrsName.getText().toString());
                        map.put("course_point",mCrsPoint.getText().toString());
                        map.put("course_semster",mCrsSemster.getText().toString());
                        map.put("doctor_id",doctor_id+"");
                        return map;
                    }
                };
                SingleTon.getInstance(this).addRequest(stringRequest);

                return;
        }
    }
}
