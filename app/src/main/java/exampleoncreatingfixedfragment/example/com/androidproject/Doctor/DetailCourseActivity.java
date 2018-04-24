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

public class DetailCourseActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mCourseNumber, mCourseName, mCoursePoint, mCourseSemster;
    private Button mUpdateBtn;
    Intent intent;
    int courseNumberTemp;
    private String mCourseNumberTxt;
    int mDoctorId;
    private String mCourseNameTxt, mCoursePointTxt, mCourseSemsterTxt;
    private Toolbar toolbar;
    int flagStudentDoctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        intent = getIntent();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        getIntentValues();
        setSupportActionBar(toolbar);
        defineViews();
        setViewsValues();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(flagStudentDoctor == 1){
            getMenuInflater().inflate(R.menu.doctor_courses_menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_assignment:
                Intent intentToAssignment = new Intent(this, AssignmentMainActivity.class);
                intentToAssignment.putExtra("doctor_id", mDoctorId);
                intentToAssignment.putExtra("course_number", mCourseNumberTxt);
                intentToAssignment.putExtras(intent);
                startActivity(intentToAssignment);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewsValues() {
        mCourseName.setText(mCourseNameTxt);
        mCourseNumber.setText(mCourseNumberTxt+"");
        mCoursePoint.setText(mCoursePointTxt);
        mCourseSemster.setText(mCourseSemsterTxt);
    }

    private void getIntentValues() {
        courseNumberTemp = intent.getIntExtra("coursenumber", 0);
        mCourseNumberTxt = courseNumberTemp+"";
        mDoctorId = intent.getIntExtra("doctor_id", 0);
        mCourseNameTxt = intent.getStringExtra("coursename");
        mCoursePointTxt = intent.getStringExtra("coursepoint");
        mCourseSemsterTxt = intent.getStringExtra("semster");
        flagStudentDoctor = intent.getIntExtra("doctorOrStudent", 0);
        toolbar.setTitle(mCourseNameTxt);
    }

    private void defineViews() {
        mCourseName = (EditText) findViewById(R.id.course_name_edt_detail);
        mCourseNumber = (EditText) findViewById(R.id.course_num_edt_detail);
        mCoursePoint = (EditText) findViewById(R.id.course_point_edt_detail);
        mCourseSemster = (EditText) findViewById(R.id.course_semster_edt_detail);

        mUpdateBtn = (Button) findViewById(R.id.course_update_btn_detail);
        if(flagStudentDoctor == 0){
            // student
            mUpdateBtn.setVisibility(View.GONE);
            mCourseName.setEnabled(false);
            mCourseNumber.setEnabled(false);
            mCoursePoint.setEnabled(false);
            mCourseSemster.setEnabled(false);
        }else{
            //doctor
            mUpdateBtn.setVisibility(View.VISIBLE);
            mCourseName.setEnabled(true);
            mCourseNumber.setEnabled(true);
            mCoursePoint.setEnabled(true);
            mCourseSemster.setEnabled(true);
        }
        mUpdateBtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.course_update_btn_detail:

               mCourseNumberTxt =  mCourseNumber.getText().toString();
                mCourseNameTxt = mCourseName.getText().toString();
                mCoursePointTxt = mCoursePoint.getText().toString();
                mCourseSemsterTxt = mCourseSemster.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/update/updateCourseData.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                new CustomToast(getBaseContext()).raiseToast(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map  = new HashMap<>();
                        map.put("course_number", mCourseNumberTxt);
                        map.put("doctor_id", Integer.toString(mDoctorId));
                        map.put("course_name", mCourseNameTxt);
                        map.put("semester", mCourseSemsterTxt);
                        map.put("point", mCoursePointTxt);
                        return map;
                    }
                };
                SingleTon.getInstance(getBaseContext()).addRequest(stringRequest);
                return;
        }
    }
}
