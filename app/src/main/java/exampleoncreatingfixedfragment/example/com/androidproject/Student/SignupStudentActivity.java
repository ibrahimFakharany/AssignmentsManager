package exampleoncreatingfixedfragment.example.com.androidproject.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.Params;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.VolleyCall;

public class SignupStudentActivity extends AppCompatActivity implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener {
    private EditText mFName, mLName, mUsername, mPhoneNumber, mPassword;
    Button mSignup;
    Toolbar toolbar;
    private boolean storeDoctorData() {
        String fName = mFName.getText().toString();
        String lName = mLName.getText().toString();
        String username = mUsername.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String password = mPassword.getText().toString();
        if(!(fName.equals("")||lName.equals("")||username.equals("")||phoneNumber.equals("")||password.equals(""))){
            ArrayList<Params> list = new ArrayList<>();
            list.add(new Params("fName", fName));
            list.add(new Params("lName",lName));
            list.add(new Params("username", username));
            list.add(new Params("phonenumber", phoneNumber));
            list.add(new Params("password", password));
            VolleyCall volleyCall = new VolleyCall(this, "/insert/insertstudentdata.php", list);
            String response = volleyCall.getVolleyCall();
            if(response!= null || response != ""){
                new CustomToast(this).raiseToast(response);
            }

        }else {
            new CustomToast(this).raiseToast("All Fields is required");
        }


        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
        defineViews();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Student");

    }

    private void defineViews() {
        mSignup = (Button) findViewById(R.id.signup_btn_student_activity);
        mSignup.setOnClickListener(this);
        mFName = (EditText) findViewById(R.id.f_name_student_signup);
        mLName = (EditText) findViewById(R.id.l_name_student_signup);
        mUsername = (EditText) findViewById(R.id.username_student_signup);
        mUsername.setOnTouchListener(this);
        mUsername.setOnFocusChangeListener(this);
        mPhoneNumber = (EditText) findViewById(R.id.phone_numebr_student_signup);
        mPassword = (EditText) findViewById(R.id.password_student_signup);
        toolbar = (Toolbar) findViewById(R.id.app_bar);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_btn_student_activity:
                new CustomToast(this).raiseToast("sign up btn");
                storeDoctorData();
                return;
        }
    }


}
