package exampleoncreatingfixedfragment.example.com.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import exampleoncreatingfixedfragment.example.com.androidproject.Doctor.DoctorMain;
import exampleoncreatingfixedfragment.example.com.androidproject.Doctor.SignupDoctorActivity;
import exampleoncreatingfixedfragment.example.com.androidproject.Student.MainActivity;
import exampleoncreatingfixedfragment.example.com.androidproject.Student.SignupStudentActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //views references

    private Button mStudentBtn, mDoctorBtn, mDoctorBtnLogin, mStudentBtnLogin;
    private EditText mStudentusernameTxt, mStudentPasswordTxt, mDoctorUsernameTxt, mDoctorPasswordTxt;
    private LinearLayout mStudentRelative ,mDoctorRelative;

    private TextView mIndicator, mDoctorTvSignUp, mStudentTvSignUp;
    String username = "", password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        defineViews();


    }

    private void defineViews() {
        mIndicator = (TextView) findViewById(R.id.indicator_tv);
        mStudentBtn = (Button) findViewById(R.id.student_btn);
        mDoctorBtn = (Button) findViewById(R.id.doctor_btn);

        mStudentBtn.setOnClickListener(this);
        mDoctorBtn.setOnClickListener(this);

        mStudentRelative = (LinearLayout) findViewById(R.id.student_relative);
        mDoctorRelative = (LinearLayout) findViewById(R.id.doctor_relative);

        mStudentBtnLogin = (Button) findViewById(R.id.student_btn_login);
        mDoctorBtnLogin = (Button) findViewById(R.id.doctor_btn_login);
        mDoctorTvSignUp = (TextView) findViewById(R.id.doctor_tv_signup);
        mStudentTvSignUp = (TextView) findViewById(R.id.student_tv_signup);
        mStudentBtnLogin.setOnClickListener(this);
        mDoctorBtnLogin.setOnClickListener(this);
        mDoctorTvSignUp.setOnClickListener(this);
        mStudentTvSignUp.setOnClickListener(this);
        mStudentusernameTxt = (EditText) findViewById(R.id.student_username_txt);
        mStudentPasswordTxt = (EditText) findViewById(R.id.student_password_txt);

        mDoctorUsernameTxt = (EditText) findViewById(R.id.doctor_username_txt);
        mDoctorPasswordTxt = (EditText) findViewById(R.id.doctor_password_txt);

    }

    @Override
    public void onClick(View v) {

        StringRequest stringRequest;
        switch (v.getId()) {
            case R.id.student_btn:
                mIndicator.setText(getString(R.string.student));
                mDoctorRelative.setVisibility(View.GONE);
                mStudentRelative.setVisibility(View.VISIBLE);
                return;
            case R.id.doctor_btn:
                mIndicator.setText(getString(R.string.doctor));
                mStudentRelative.setVisibility(View.GONE);
                mDoctorRelative.setVisibility(View.VISIBLE);
                return;
            // web service
            case R.id.student_btn_login:
                username = mStudentusernameTxt.getText().toString();
                password = mStudentPasswordTxt.getText().toString();

                stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/retrieve/checkLogin.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result = null;
                        try {
                            result = new JSONObject(response);
                            if(result.getInt("success") == 1){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("student_id", result.getInt("student_id"));
                                intent.putExtra("firstname", result.getString("firstname"));
                                intent.putExtra("lastname", result.getString("lastname"));
                                intent.putExtra("phonenumber", result.getString("phonenumber"));
                                intent.putExtra("password", result.getString("password"));
                                intent.putExtra("username", result.getString("username"));

                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", username);
                    map.put("password", password);
                    return map;
                }
            };
                SingleTon.getInstance(this).addRequest(stringRequest);

                return;
            case R.id.doctor_btn_login:
                username = mDoctorUsernameTxt.getText().toString();
                password = mDoctorPasswordTxt.getText().toString();

                stringRequest = new StringRequest(Request.Method.POST,   getString(R.string.ip) + "/retrieve/checkLoginDoctor.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject result = new JSONObject(response);
                            int success = result.getInt("success");
                            if (success == 1) {
                                int doctor_id = result.getInt("doctor_id");
                                String firstname = result.getString("firstname");
                                String lastname= result.getString("lastname");
                                String username = result.getString("username");
                                String phonenumber = result.getString("phonenumber");
                                String address = result.getString("address");
                                String password = result.getString("password");

                                Intent intent = new Intent(getApplicationContext(), DoctorMain.class);
                                intent.putExtra("doctor_id",doctor_id);
                                intent.putExtra("firstname",firstname);
                                intent.putExtra("lastname",lastname);
                                intent.putExtra("username",username);
                                intent.putExtra("phonenumber",phonenumber);
                                intent.putExtra("address",address);
                                intent.putExtra("password",password);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("username", username);
                        map.put("password", password);
                        return map;
                    }
                };
                SingleTon.getInstance(this).addRequest(stringRequest);
                return;
            case R.id.doctor_tv_signup:
                startActivity(new Intent(this, SignupDoctorActivity.class));
                return;
            case R.id.student_tv_signup:
                startActivity(new Intent(this, SignupStudentActivity.class));
                return;


        }
    }
}
